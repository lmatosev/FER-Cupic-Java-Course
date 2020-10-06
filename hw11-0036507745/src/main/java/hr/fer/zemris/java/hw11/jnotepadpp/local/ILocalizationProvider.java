package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * 
 * Model of a localization provider.
 *
 */
public interface ILocalizationProvider {

	/**
	 * Registers the provided listener.
	 * 
	 * @param listener - listener to be registered
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the provided listener from the collection of registed listeners.
	 * 
	 * @param listener - listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns the translation associated with the provided key.
	 * 
	 * @param str - key used for getting the translation
	 * @return translation - translation returned by the provider
	 */
	String getString(String str);

}
