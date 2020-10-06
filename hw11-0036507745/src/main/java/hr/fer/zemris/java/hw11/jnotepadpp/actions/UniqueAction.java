package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used to delete all duplicate lines.
 * 
 * @author Lovro Matošević
 *
 */
public class UniqueAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828145971307740951L;

	/**
	 * Model used for editing
	 */
	private MultipleDocumentModel model;

	/**
	 * Main constructor which accepts a string key which is used to set the
	 * localized name of the action, a provider and a model.
	 * 
	 * @param key      - key used for localized name
	 * @param provider - provider used for localization
	 * @param model    - model being used for the action itself
	 */
	public UniqueAction(String key, ILocalizationProvider provider, MultipleDocumentModel model) {
		super(key, provider);
		this.model = model;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U A"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U | KeyEvent.VK_A);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("unique_action"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("unique_action_description"));
			}
		});
	}

	/**
	 * Deletes all duplicate lines. If there are duplicate lines found, the first
	 * line will be kept.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextArea text = model.getCurrentDocument().getTextComponent();
		int selectionStart = text.getSelectionStart();
		int selectionEnd = text.getSelectionEnd();
		int startingLineNum = 0;
		int endingLineNum = 0;
		try {
			startingLineNum = text.getLineOfOffset(selectionStart);
			endingLineNum = text.getLineOfOffset(selectionEnd);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		String[] lines = text.getText().split("\\n");
		Set<String> selectedLinesUnique = new LinkedHashSet<>();
		for (int i = startingLineNum; i <= endingLineNum; i++) {
			selectedLinesUnique.add(lines[i]);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < startingLineNum; i++) {
			sb.append(lines[i]).append("\n");
		}

		for (String line : selectedLinesUnique) {
			sb.append(line).append("\n");
		}

		for (int i = endingLineNum + 1; i < lines.length; i++) {
			sb.append(lines[i]).append("\n");
		}

		text.setText(sb.toString());
	}

}
