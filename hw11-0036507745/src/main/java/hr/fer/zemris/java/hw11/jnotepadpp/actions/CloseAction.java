package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used for closing the current document.
 * 
 * @author Lovro Matošević
 *
 */
public class CloseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7289813922596179076L;
	/**
	 * Model from which the tab is being closed.
	 */
	private MultipleDocumentModel model;
	/**
	 * Provider used for localization
	 */
	private ILocalizationProvider provider;
	/**
	 * Notepad from where the tab is being closed
	 */
	private JNotepadPP notepad;
	/**
	 * Action used for saving the closing document if needed
	 */
	private Action saveAs;

	/**
	 * Main constructor which accepts a string key which is used to set the
	 * localized name of the action, a provider and a model.
	 * 
	 * @param key      - key used for localized name
	 * @param provider - provider used for localization
	 * @param model    - model being used for the action itself
	 */
	public CloseAction(String key, ILocalizationProvider provider, MultipleDocumentModel model, JNotepadPP notepad,
			Action saveAs) {
		super(key, provider);
		this.provider = provider;
		this.model = model;
		this.notepad = notepad;
		this.saveAs = saveAs;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F12"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_CONTROL);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("close_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("close_description"));
			}
		});
	}

	/**
	 * Closes the current document. Prompts the user for saving the document if it was modified.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel document = model.getCurrentDocument();
		if (document.isModified()) {

			String[] options = { provider.getString("yes"), provider.getString("no"), provider.getString("cancel") };

			int option = JOptionPane.showOptionDialog(notepad, provider.getString("exit_dialog_statement"),
					provider.getString("save"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);

			if (option == 0) {
				if (document.getFilePath() == null) {
					this.saveAs.actionPerformed(new ActionEvent(notepad, ActionEvent.ACTION_PERFORMED, null));
				} else {
					this.model.saveDocument(document, null);
				}
			} else if (option == 2) {
				return;
			}

		}
		model.closeDocument(model.getCurrentDocument());
	}

}
