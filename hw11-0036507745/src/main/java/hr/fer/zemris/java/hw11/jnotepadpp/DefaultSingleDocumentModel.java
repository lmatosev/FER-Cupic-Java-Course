package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * Represents an implementation of the model of a single document, having
 * information about file path from which document was loaded (can be null for
 * new document), document modification status and reference to Swing component
 * which is used for editing.
 * 
 * @author Lovro Matošević
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Path of the this document
	 */
	private Path path;
	/**
	 * A {@link JTextArea} used for editing this document
	 */
	private JTextArea editor;
	/**
	 * Value which indicates if this document was modified
	 */
	private boolean isModified;
	/**
	 * List of registered listeners
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Main constructor for this model. Accepts a path to the document and starting
	 * text.
	 * 
	 * @param path - path to be set
	 * @param text - starting text to be set
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.editor = new JTextArea(text);
		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				DefaultSingleDocumentModel.this.setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				DefaultSingleDocumentModel.this.setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				DefaultSingleDocumentModel.this.setModified(true);
			}
		});
		this.path = path;
		this.isModified = true;
		this.listeners = new ArrayList<>();
	}

	/**
	 * Returns the text component used for editing this document.
	 * 
	 * @return area - {@link JTextArea} used for editing
	 */
	@Override
	public JTextArea getTextComponent() {
		return this.editor;
	}

	/**
	 * Returns the file path of the the current document.
	 * 
	 * @return path - path of the current document
	 */
	@Override
	public Path getFilePath() {
		return this.path;
	}

	/**
	 * Sets the document's path.
	 * 
	 * @param path - path to be set
	 */
	@Override
	public void setFilePath(Path path) {
		this.path = path;
		this.notifyListenersPathUpdated();
	}

	/**
	 * Used to check if the current document is modified.
	 * 
	 * @return modified - true if it was modified and false else
	 */
	@Override
	public boolean isModified() {
		return this.isModified;
	}

	/**
	 * Sets the modified status of this document.
	 * 
	 * @param modified - boolean value to be set
	 */
	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		this.notifyListenersStatusUpdated();
	}

	/**
	 * Adds the {@link SingleDocumentListener} to this model.
	 * 
	 * @param l - listener to be added
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	/**
	 * Removes the {@link SingleDocumentListener} from this model.
	 * 
	 * @param l - listener to be removed
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Helper method used to notify registered listeners that the modify status was
	 * updated.
	 */
	private void notifyListenersStatusUpdated() {
		for (var listener : this.listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}

	/**
	 * Helper method used to notify registered listeners that the document's path
	 * was updated.
	 */
	private void notifyListenersPathUpdated() {
		for (var listener : this.listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

}
