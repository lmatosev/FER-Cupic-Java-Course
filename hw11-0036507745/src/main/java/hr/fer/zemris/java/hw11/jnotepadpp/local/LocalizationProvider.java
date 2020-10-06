package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * This class represents a localization provider which is used for translating.
 * 
 * @author Lovro Matošević
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * The singleton instance of {@link LocalizationProvider}
	 */
	private static final LocalizationProvider provider = new LocalizationProvider();
	/**
	 * Current language being used
	 */
	private String language;
	/**
	 * Bundle used for getting translations
	 */
	private ResourceBundle bundle;

	/**
	 * Main constructor which sets the default language to english.
	 */
	private LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);

	}

	/**
	 * Sets the language to the provided one.
	 * 
	 * @param language - language which will be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		this.fire();
	}

	/**
	 * Returns the singleton instance of the {@link LocalizationProvider}.
	 * 
	 * @return provider - the {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return LocalizationProvider.provider;
	}

	/**
	 * Returns the translation for the provided key.
	 * 
	 * @return translation - the translation which was returned from the given key
	 */
	@Override
	public String getString(String key) {
		return this.bundle.getString(key);
	}

}
