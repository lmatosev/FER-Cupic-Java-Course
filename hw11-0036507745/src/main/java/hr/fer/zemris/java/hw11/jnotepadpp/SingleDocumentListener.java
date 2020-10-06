package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * 
 * Listener used for {@link SingleDocumentModel}.
 *
 */
public interface SingleDocumentListener {

	/**
	 * 
	 * Method run when the provided document's modify status is changed.
	 *
	 *
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * 
	 * Method run when the provided document's file path is changed.
	 *
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
