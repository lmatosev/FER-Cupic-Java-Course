package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * This class represents a model of a document.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class Document {

	/**
	 * path to the document
	 */
	private Path documentPath;

	/**
	 * list of document's words
	 */
	private Set<String> documentVocabulary;

	/**
	 * document's tfidf vector
	 */
	private Map<String, Double> tfidf;

	/**
	 * Main constructor for this class.
	 */
	public Document() {

		this.documentVocabulary = new HashSet<String>();
		this.tfidf = new HashMap<String, Double>();

	}

	/**
	 * Adds the provided values to the tfidf vector.
	 * 
	 * @param str - word
	 * @param num - value
	 */
	public void addToTfidf(String str, double num) {

		this.tfidf.put(str, num);

	}

	/**
	 * @return the documentPath
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * @param documentPath the documentPath to set
	 */
	public void setDocumentPath(Path documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * @return the documentVocabulary
	 */
	public Set<String> getDocumentVocabulary() {
		return documentVocabulary;
	}

	/**
	 * @param documentVocabulary the documentVocabulary to set
	 */
	public void setDocumentVocabulary(Set<String> documentVocabulary) {
		this.documentVocabulary = documentVocabulary;
	}

	/**
	 * @return the tfidf
	 */
	public Map<String, Double> getTfidf() {
		return tfidf;
	}

	/**
	 * @param tfidf the tfidf to set
	 */
	public void setTfidf(Map<String, Double> tfidf) {
		this.tfidf = tfidf;
	}

}
