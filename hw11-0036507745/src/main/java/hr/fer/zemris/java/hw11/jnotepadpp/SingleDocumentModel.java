package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * 
 * Represents a model of single document, having information about file path from which
 * document was loaded (can be null for new document), document modification
 * status and reference to Swing component which is used for editing.
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns the text component used for editing this document.
	 * 
	 * @return area - {@link JTextArea} used for editing
	 */
	JTextArea getTextComponent();

	/**
	 * Returns the file path of the the current document.
	 * 
	 * @return path - path of the current document
	 */
	Path getFilePath();

	/**
	 * Sets the document's path.
	 * 
	 * @param path - path to be set
	 */
	void setFilePath(Path path);

	/**
	 * Used to check if the current document is modified.
	 * 
	 * @return modified - true if it was modified and false else
	 */
	boolean isModified();

	/**
	 * Sets the modified status of this document.
	 * 
	 * @param modified - boolean value to be set
	 */
	void setModified(boolean modified);

	/**
	 * Adds the {@link SingleDocumentListener} to this model.
	 * 
	 * @param l - listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes the {@link SingleDocumentListener} from this model.
	 * 
	 * @param l - listener to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
