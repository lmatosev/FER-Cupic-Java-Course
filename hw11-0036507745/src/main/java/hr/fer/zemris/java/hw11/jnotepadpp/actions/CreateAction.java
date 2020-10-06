package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used for creating a new document in the provided
 * {@link MultipleDocumentModel}.
 * 
 * @author Lovro Matošević
 *
 */
public class CreateAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6600667392795674542L;
	/**
	 * Model where the document will be created
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
	public CreateAction(String key, ILocalizationProvider provider, MultipleDocumentModel model) {
		super(key, provider);
		this.model = model;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		String translation = provider.getString("create_description");
		this.putValue(Action.SHORT_DESCRIPTION, translation);
		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("create_description"));
			}
		});
	}

	/**
	 * Creates a new document in the stored model.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		this.model.createNewDocument();

	}

}
