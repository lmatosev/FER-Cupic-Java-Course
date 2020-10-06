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
 * Action used to turn all characters from the selected text to upper case.
 * 
 * @author Lovro Matošević
 *
 */
public class ToUppercaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342036774828103327L;

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
	public ToUppercaseAction(String key, ILocalizationProvider provider, MultipleDocumentModel model) {
		super(key, provider);
		this.model = model;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("uppercase_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("uppercase_description"));
			}
		});

	}

	/**
	 * Switches the case of all the characters from the selection to upper case.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel document = model.getCurrentDocument();
		String text = document.getTextComponent().getSelectedText();
		text = text.toUpperCase();
		document.getTextComponent().replaceSelection(text);
	}

}
