package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action which is used for sorting the selected lines ascending.
 * 
 * @author Lovro Matošević
 *
 */
public class AscendingSortAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828145971307740951L;

	/**
	 * Model used
	 */
	private MultipleDocumentModel model;
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
	public AscendingSortAction(String key, ILocalizationProvider provider, MultipleDocumentModel model) {
		super(key, provider);
		this.model = model;
		this.provider = provider;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A S"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A | KeyEvent.VK_S);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("ascending_sort_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("ascending_sort_description"));
			}
		});
	}

	/**
	 * Sorts the selected lines ascending. If only a part of a line is selected, it
	 * counts as a whole line.
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
		List<String> selectedLines = new ArrayList<>();
		for (int i = startingLineNum; i <= endingLineNum; i++) {
			selectedLines.add(lines[i]);
		}

		Locale locale = new Locale(provider.getString("locale"));
		Collator collator = Collator.getInstance(locale);
		Collections.sort(selectedLines, collator);

		for (int i = startingLineNum; i <= endingLineNum; i++) {
			lines[i] = selectedLines.get(i - startingLineNum);
		}

		StringBuilder sb = new StringBuilder();

		for (String line : lines) {
			sb.append(line).append("\n");
		}

		text.setText(sb.toString());
	}

}
