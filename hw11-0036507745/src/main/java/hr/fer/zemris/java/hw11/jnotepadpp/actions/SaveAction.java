package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action used to save the current document.
 * 
 * 
 * @author Lovro Matošević
 *
 */
public class SaveAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 776539421579141543L;

	/**
	 * {@link MultipleDocumentModel} where the action is being executed
	 */
	private MultipleDocumentModel model;
	/**
	 * Action used for saving documents whose path is <code>null</code>
	 */
	private Action saveAs;

	/**
	 * Main constructor which accepts a string key which is used to set the
	 * localized name of the action, a provider and a model.
	 * 
	 * @param key      - key used for localized name
	 * @param provider - provider used for localization
	 * @param model    - model being used for the action itself
	 * @param saveAs   - action used to save documents which have no provided path
	 */
	public SaveAction(String key, ILocalizationProvider provider, MultipleDocumentModel model, Action saveAs) {
		super(key, provider);
		this.model = model;
		this.saveAs = saveAs;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("save_description"));

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("save_description"));
			}
		});
	}

	/**
	 * Saves the current document.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (model.getCurrentDocument().getFilePath() == null) {
			this.saveAs.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			return;
		}

		this.model.saveDocument(model.getCurrentDocument(), null);

	}

}
