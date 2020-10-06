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
 * Action used for saving the current document. Prompts the user to select where
 * the document should be saved.
 * 
 * @author Lovro Matošević
 *
 */
public class SaveAsAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5899110727312642022L;

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
	 */
	public SaveAsAction(String key, ILocalizationProvider provider, MultipleDocumentModel model, JNotepadPP notepad) {
		super(key, provider);
		this.provider = provider;
		this.model = model;
		this.notepad = notepad;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F2"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S | KeyEvent.VK_F2);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("save"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("save_description"));
			}
		});
	}

	/**
	 * Saves the current document. User will be asked where the document should be
	 * saved to.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getString("save"));
		if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			String[] options = { "Ok" };
			JOptionPane.showOptionDialog(notepad, provider.getString("save_cancel_statement"),
					provider.getString("information"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[0]);
			return;
		}
		Path path = jfc.getSelectedFile().toPath();

		if (Files.exists(path)) {
			String[] options = { provider.getString("yes"), provider.getString("no") };
			if (JOptionPane.showOptionDialog(notepad, provider.getString("save_as_exists_dialog_statement"),
					provider.getString("warning"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
					options, options[0]) != 0) {
				return;
			}

		}

		try

		{
			this.model.saveDocument(model.getCurrentDocument(), path);
		} catch (Exception e) {
			String[] options = { provider.getString("ok") };
			JOptionPane.showOptionDialog(notepad, provider.getString("save_error_statement"),
					provider.getString("error"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options,
					options[0]);
			return;
		}

		return;

	}

}
