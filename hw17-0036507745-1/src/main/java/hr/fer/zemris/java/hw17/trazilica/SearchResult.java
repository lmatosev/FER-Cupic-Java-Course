package hr.fer.zemris.java.hw17.trazilica;

/**
 * 
 * Class representing a search result.
 * 
 * @author Lovro Matošević
 *
 */

public class SearchResult {

	/**
	 * similarity factor between the query and the document
	 */
	private double similarityFactor;
	/**
	 * document compared to the query
	 */
	private Document document;

	/**
	 * Main constructor for this class.
	 * 
	 * @param similarity - similarity factor
	 * @param doc        - document used
	 */
	public SearchResult(double similarity, Document doc) {

		this.similarityFactor = similarity;
		this.document = doc;

	}

	/**
	 * @return the similarityFactor
	 */
	public double getSimilarityFactor() {
		return similarityFactor;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

}
