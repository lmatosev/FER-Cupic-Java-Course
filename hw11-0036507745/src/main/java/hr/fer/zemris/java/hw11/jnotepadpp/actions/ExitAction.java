package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used for exiting the {@link JNotepadPP}. Iterates through all the
 * documents and asks the user to save the modified ones.
 * 
 * @author Lovro Matošević
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5050548213592449042L;

	/**
	 * {@link MultipleDocumentModel} where the action is being executed
	 */
	private MultipleDocumentModel model;
	/**
	 * {@link JNotepadPP} in which the action is being executed.
	 */
	private JNotepadPP notepad;
	/**
	 * Action used for saving documents whose path is <code>null</code>
	 */
	private Action saveAs;
	/**
	 * Provider used for localization
	 */
	private ILocalizationProvider provider;

	/**
	 * Main constructor which accepts a string key which is used to set the
	 * localized name of the action, a provider and a model.
	 * 
	 * @param key      - key used for localized name
	 * @param provider - provider used for localization
	 * @param model    - model being used for the action itself
	 * @param notepad  - {@link JNotepadPP} which will be exited
	 * @param saveAs   - action used to save documents which have no provided path
	 */
	public ExitAction(String key, ILocalizationProvider provider, MultipleDocumentModel model, JNotepadPP notepad,
			Action saveAs) {
		super(key, provider);
		this.provider = provider;
		this.model = model;
		this.notepad = notepad;
		this.saveAs = saveAs;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

	}

	/**
	 * Iterates through all documents and closes them. Asks the user to save
	 * modified ones.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		for (var document : model) {
			JTabbedPane pane = (JTabbedPane) model;
			pane.setSelectedIndex(0);

			if (document.isModified()) {

				String[] options = { provider.getString("yes"), provider.getString("no"),
						provider.getString("cancel") };

				int option = JOptionPane.showOptionDialog(notepad, provider.getString("exit_dialog_statement"),
						provider.getString("save"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);

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

			model.closeDocument(document);

		}

		this.notepad.dispose();

	}

}
