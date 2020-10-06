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
 * Action used to show the user the statistical information on the current
 * document such as the total number of characters, number of non-blank
 * characters and the number of lines.
 * 
 * @author Lovro Matošević
 *
 */
public class StatisticsAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4527243874798539195L;

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
	public StatisticsAction(String key, ILocalizationProvider provider, MultipleDocumentModel model,
			JNotepadPP notepad) {
		super(key, provider);
		this.provider = provider;
		this.model = model;
		this.notepad = notepad;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt control S"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ALT | KeyEvent.VK_S);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("statistics_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("statistics_description"));
			}
		});
	}

	/**
	 * Displays the current document statistics in a dialog box.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		SingleDocumentModel document = model.getCurrentDocument();

		String text = document.getTextComponent().getText();

		int totalCount = 0;
		int nonBlankCount = 0;
		int lineCount = 1;

		for (int i = 0; i < text.length(); i++) {

			totalCount++;
			char chr = text.charAt(i);
			if (!Character.isWhitespace(chr)) {
				nonBlankCount++;
			}
			if (chr == '\n') {
				lineCount++;
			}

		}

		StringBuilder sb = new StringBuilder();
		sb.append(provider.getString("statistics_dialog_statement_1")).append(totalCount).append(" ")
				.append(provider.getString("statistics_dialog_statement_2")).append(nonBlankCount).append(" ")
				.append(provider.getString("statistics_dialog_statement_3")).append(lineCount).append(" ")
				.append(provider.getString("statistics_dialog_statement_4"));
		String message = sb.toString();
		String[] options = { "Ok" };
		JOptionPane.showOptionDialog(notepad, message, provider.getString("statistics"), JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

	}

}
