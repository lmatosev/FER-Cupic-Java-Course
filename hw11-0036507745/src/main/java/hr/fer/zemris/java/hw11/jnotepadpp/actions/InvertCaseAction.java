package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used to invert the case of the selected part of the text.
 * 
 * @author Lovro Matošević
 *
 */
public class InvertCaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342036774828103327L;

	/**
	 * Model from where the document will be edited.
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
	public InvertCaseAction(String key, ILocalizationProvider provider, MultipleDocumentModel model) {
		super(key, provider);
		this.model = model;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift I"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("invertcase_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("invertcase_description"));
			}
		});

	}

	/**
	 * Inverts the case of all characters in the selected text.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel document = model.getCurrentDocument();
		String text = document.getTextComponent().getSelectedText();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char chr = text.charAt(i);
			if (Character.isLowerCase(chr)) {
				sb.append(Character.toUpperCase(chr));
			} else if (Character.isUpperCase(chr)) {
				sb.append(Character.toLowerCase(chr));
			} else {
				sb.append(chr);
			}
		}
		document.getTextComponent().replaceSelection(sb.toString());
	}

}
