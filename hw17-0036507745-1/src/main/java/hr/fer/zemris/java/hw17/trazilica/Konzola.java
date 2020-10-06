package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * This class represents a console program which implements a text search
 * engine.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class Konzola {

	/**
	 * idf vector
	 */
	private static Map<String, Double> idf;

	/**
	 * list of stop words
	 */
	private static List<String> stopWords;

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			System.exit(0);
		}

		String articles = args[0];

		Path articlePath = Paths.get(articles);

		CustomFileVisitor visitor = new CustomFileVisitor();

		try {
			Files.walkFileTree(articlePath, visitor);
		} catch (IOException e1) {
			System.out.println("Error while reading articles.");
			System.exit(0);
		}

		List<Path> paths = visitor.getPaths();

		List<String> vocabulary = null;
		List<Document> documents = null;

		try {
			vocabulary = getVocabularyFromArticles(paths);
			documents = calculateVectorsAndCreateDocuments(paths, vocabulary);
		} catch (IOException e) {
			System.out.println("Error while reading files.");
			System.exit(0);
		}

		System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");

		Scanner sc = new Scanner(System.in);

		List<SearchResult> results = null;

		while (true) {

			System.out.print("Enter command > ");

			String input = sc.nextLine();

			String[] inputSplit = input.trim().split("\\s+");

			String command = inputSplit[0];

			if (command.equals("query")) {

				List<String> queryWords = new ArrayList<>();

				for (int i = 1; i < inputSplit.length; i++) {

					String word = inputSplit[i].toLowerCase();
					if (vocabulary.contains(word)) {

						queryWords.add(word);

					}

				}

				System.out.println("Query is: " + queryWords);

				results = getResults(queryWords, documents, vocabulary);

				Comparator<SearchResult> comparator = new Comparator<SearchResult>() {

					@Override
					public int compare(SearchResult sr1, SearchResult sr2) {
						double sim1 = sr1.getSimilarityFactor();
						double sim2 = sr2.getSimilarityFactor();
						if (sim1 > sim2) {
							return -1;
						} else if (sim1 < sim2) {
							return 1;
						} else {
							return 0;
						}
					}
				};

				results.sort(comparator);

				System.out.println("Najboljih 10 rezultata:");

				results = results.subList(0, Math.min(results.size(), 10));

				for (int i = 0; i < results.size(); i++) {

					String similarity = String.format("%1.4f", results.get(i).getSimilarityFactor());

					StringBuilder sb = new StringBuilder();
					sb.append("[").append(i).append("] (").append(similarity).append(") ")
							.append(results.get(i).getDocument().getDocumentPath());

					System.out.println(sb.toString());
				}

			} else if (command.equals("type")) {

				if (results == null) {

					System.out.println("No results were found yet!");

				} else {

					int index = 0;
					try {
						index = Integer.parseInt(inputSplit[1]);

						SearchResult result = results.get(index);

						StringBuilder sb = new StringBuilder();

						Path docPath = result.getDocument().getDocumentPath();

						sb.append("Dokument: " + docPath).append("\n");

						sb.append("-----------------------------------------------------------------\n");

						BufferedReader br = new BufferedReader(new FileReader(docPath.toFile()));

						String line;

						while ((line = br.readLine()) != null) {

							sb.append(line).append("\n");

						}

						sb.append("-----------------------------------------------------------------\n");

						System.out.println(sb.toString());
						br.close();
					} catch (NumberFormatException ex) {
						System.out.println("Invalid second argument.");
					} catch (IOException e) {
						System.out.println("Error while reading file.");
					}
				}

			} else if (command.equals("results")) {

				if (results == null) {
					System.out.println("No results to show.");
				} else {
					for (int i = 0; i < results.size(); i++) {

						String similarity = String.format("%1.4f", results.get(i).getSimilarityFactor());

						StringBuilder sb = new StringBuilder();
						sb.append("[").append(i).append("] (").append(similarity).append(") ")
								.append(results.get(i).getDocument().getDocumentPath());

						System.out.println(sb.toString());
					}
				}

			} else if (command.equals("exit")) {

				break;

			} else {

				System.out.println("Nepoznata naredba.");

			}

			System.out.println();
		}

		sc.close();
	}

	/**
	 * Helper method used to do the search and return the list of
	 * {@link SearchResult}s.
	 * 
	 * @param queryWords - query words
	 * @param documents  - documents
	 * @param vocabulary - vocabulary words
	 * @return
	 */
	private static List<SearchResult> getResults(List<String> queryWords, List<Document> documents,
			List<String> vocabulary) {

		List<SearchResult> results = new ArrayList<>();

		Map<String, Double> queryVector = new HashMap<>();

		for (var word : vocabulary) {

			if (queryWords.contains(word)) {
				double queryIdf = Konzola.idf.get(word);
				queryVector.put(word, queryIdf);
			} else {
				queryVector.put(word, 0d);
			}
		}

		for (var doc : documents) {

			double similarity = calculateSimilarity(queryVector, doc.getTfidf());
			if (similarity > 0) {

				SearchResult result = new SearchResult(similarity, doc);
				results.add(result);

			}

		}

		return results;
	}

	/**
	 * Helper method used to calculate similarity factor between two vectors.
	 * 
	 * @param queryVector - query vector
	 * @param docVector   - document vector
	 * @return similarity - similarity of the two provided vectors
	 */
	private static double calculateSimilarity(Map<String, Double> queryVector, Map<String, Double> docVector) {

		double similarity = 0;

		double dotProduct = 0;
		double norm1 = 0;
		double norm2 = 0;

		for (var word : queryVector.keySet()) {

			double val1 = queryVector.get(word);
			double val2 = docVector.get(word);

			dotProduct += val1 * val2;
			norm1 += Math.pow(val1, 2);
			norm2 += Math.pow(val2, 2);

		}

		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);

		if (norm1 != 0 && norm2 != 0) {
			similarity = dotProduct / (norm1 * norm2);
		} else {
			similarity = 0;
		}

		return similarity;
	}

	/**
	 * Helper method used to calculate vectors and create a list of documents.
	 * 
	 * @param paths      - paths to all documents
	 * @param vocabulary - vocabulary words
	 * @return documents - list of documents
	 * @throws IOException - if there is an error while reading
	 */
	private static List<Document> calculateVectorsAndCreateDocuments(List<Path> paths, List<String> vocabulary)
			throws IOException {

		BufferedReader br;

		String line;

		List<Document> documents = new ArrayList<>();

		for (var path : paths) {

			Document doc = new Document();

			File file = path.toFile();

			br = new BufferedReader(new FileReader(file));

			StringBuilder sb = new StringBuilder();

			Set<String> fileWords = new HashSet<>();

			Map<String, Integer> fileWordFrequencies = new HashMap<>();

			while ((line = br.readLine()) != null) {

				for (int i = 0; i < line.length(); i++) {

					char chr = line.charAt(i);

					if (Character.isAlphabetic(chr)) {

						sb.append(chr);

					} else {

						if (sb.length() != 0) {

							String word = sb.toString().toLowerCase();

							if (!stopWords.contains(word)) {

								if (fileWords.contains(word)) {

									int freq = fileWordFrequencies.get(word);
									fileWordFrequencies.put(word, freq + 1);

								} else {

									fileWordFrequencies.put(word, 1);
									fileWords.add(word);

								}

							}

							sb = new StringBuilder();

						}

					}

				}

			}

			doc.setDocumentPath(file.toPath());

			doc.setDocumentVocabulary(fileWords);

			Map<String, Double> tfidf = new HashMap<>();

			for (var word : vocabulary) {

				int tf = fileWordFrequencies.getOrDefault(word, 0);
				double idf = Konzola.idf.get(word);

				tfidf.put(word, tf * idf);

			}

			doc.setTfidf(tfidf);

			documents.add(doc);

		}

		return documents;
	}

	/**
	 * Finds and returns the vocabulary from provided list of articles/documents.
	 * 
	 * @param paths - list of documents
	 * @return vocabulary - list of all words in vocabulary
	 * @throws IOException
	 */
	private static List<String> getVocabularyFromArticles(List<Path> paths) throws IOException {

		Path stopWordsPath = Paths.get("src/main/resources/hrvatski_stoprijeci.txt");

		Set<String> vocabulary = new HashSet<>();

		List<String> stopWords = new ArrayList<>();

		Map<String, Integer> wordFrequencies = new HashMap<String, Integer>();

		BufferedReader br = new BufferedReader(new FileReader(stopWordsPath.toFile()));

		String line;

		while ((line = br.readLine()) != null) {
			stopWords.add(line.trim());
		}

		Konzola.stopWords = stopWords;

		br.close();

		for (var path : paths) {

			File file = path.toFile();

			Set<String> fileWords = new HashSet<>();

			br = new BufferedReader(new FileReader(file));

			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {

				for (int i = 0; i < line.length(); i++) {

					char chr = line.charAt(i);

					if (Character.isAlphabetic(chr)) {

						sb.append(chr);

					} else {

						if (sb.length() != 0) {

							String word = sb.toString().toLowerCase();

							if (!stopWords.contains(word)) {

								fileWords.add(word);

							}

							sb = new StringBuilder();

						}

					}

				}

			}

			for (var word : fileWords) {

				if (vocabulary.contains(word)) {

					int freq = wordFrequencies.get(word);
					wordFrequencies.put(word, freq + 1);

				} else {

					vocabulary.add(word);
					wordFrequencies.put(word, 1);

				}

			}

		}

		Map<String, Double> idf = new HashMap<>();

		double numberOfDocuments = paths.size();

		for (var word : wordFrequencies.keySet()) {

			int frequency = wordFrequencies.get(word);
			double idfValue = Math.log(numberOfDocuments / frequency);

			idf.put(word, idfValue);
		}

		Konzola.idf = idf;

		List<String> vocabularyList = vocabulary.stream().collect(Collectors.toList());

		return vocabularyList;
	}

	/**
	 * 
	 * File visitor used to recursively visit all the paths and create a list of
	 * paths to files.
	 *
	 */
	private static class CustomFileVisitor implements FileVisitor<Path> {

		/**
		 * paths to files
		 */
		private List<Path> paths;

		/**
		 * Main constructor for this class
		 */
		public CustomFileVisitor() {
			this.paths = new ArrayList<>();
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
			this.paths.add(arg0);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * @return the paths
		 */
		public List<Path> getPaths() {
			return paths;
		}

	}

}
