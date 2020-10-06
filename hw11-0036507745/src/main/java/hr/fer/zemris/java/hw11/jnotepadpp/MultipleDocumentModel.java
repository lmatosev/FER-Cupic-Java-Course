package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * 
 * Model capable of holding zero, one or more documents with concept of the
 * current document.
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document
	 * 
	 * @return document - new document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current document
	 * 
	 * @return document - the current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a new document from the provided path
	 * 
	 * @param path - new document's location
	 * @return document - the resulting document
	 */

	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the current document to the provided path
	 * 
	 * @param model   - the document to be saved
	 * @param newPath - path where the document will be saved, can be null if the
	 *                current document's path should be used
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes the provided document
	 * 
	 * @param model - the document to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds the provided listener to this model
	 * 
	 * @param l - listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes the provided listener from this model
	 * 
	 * @param l - listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of stored documents
	 * 
	 * @return number - number of stored documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns the document stored at the provided index
	 * 
	 * @param index - index of the document
	 * @return document - the document at the provided index
	 */
	SingleDocumentModel getDocument(int index);

}
