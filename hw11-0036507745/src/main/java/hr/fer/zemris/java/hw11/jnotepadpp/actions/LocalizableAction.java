package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Class which represents an extension of the {@link AbstractAction} class. It
 * also provides localization. It will set action's name to the provided key
 * using the given provider in the constructor.
 * 
 * @author Lovro Matošević
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 234051158186936709L;

	/**
	 * Constructor which accepts a key and a provider.
	 * 
	 * @param key - key used for action's name
	 * @param provider - provider used to translate the key
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		String translation = provider.getString(key);
		this.putValue(NAME, translation);

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				String translation = provider.getString(key);
				putValue(NAME, translation);
			}
		});
	}
}
