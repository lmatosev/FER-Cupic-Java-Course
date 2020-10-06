package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used for opening a new document. Asks the user to provide the path.
 * 
 * @author Lovro Matošević
 *
 */
public class OpenAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link MultipleDocumentModel} where the action is being executed
	 */
	private MultipleDocumentModel model;
	/**
	 * {@link JNotepadPP} in which the action is being executed.
	 */
	private JNotepadPP notepad;
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
	public OpenAction(String key, ILocalizationProvider provider, MultipleDocumentModel model, JNotepadPP notepad) {
		super(key, provider);
		this.provider = provider;
		this.model = model;
		this.notepad = notepad;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		String translation = provider.getString("open_description");
		this.putValue(Action.SHORT_DESCRIPTION, translation);
		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("open_description"));
			}
		});
	}

	/**
	 * Prompts the user to select the document that will be opened.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open file");
		if (jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path path = jfc.getSelectedFile().toPath();
		if (!Files.isReadable(path)) {

			String[] options = { "Ok" };
			String dialogName = provider.getString("error");
			String dialogStatement = provider.getString("open_dialog_statement");

			JOptionPane.showOptionDialog(notepad, dialogStatement, dialogName, JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			return;
		}

		this.model.loadDocument(path);

	}

}
