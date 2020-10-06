package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * 
 * Listener used for the {@link MultipleDocumentModel}.
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method run when the current document was changed.
	 * 
	 * @param previousModel - previous current document
	 * @param currentModel - new current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Method run when the document was added to the {@link MultipleDocumentModel}.
	 * 
	 * @param model - document which was added
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Method run when the document was removed from the {@link MultipleDocumentModel}.
	 * 
	 * @param model - document which was removed
	 */
	void documentRemoved(SingleDocumentModel model);
	
}
