package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * 
 * This class is a decorator for an instance of {@link ILocalizationProvider}.
 * Offers methods connect() and disconnect() which manage the connection status.
 * 
 * @author Lovro Matošević
 *
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Value indicating if there is an established connection
	 */
	private boolean connected;
	/**
	 * Provider being used
	 */
	private ILocalizationProvider provider;
	/**
	 * Listener being used
	 */
	private ILocalizationListener listener;

	/**
	 * 
	 * Main constructor which accepts and sets the provided
	 * {@link ILocalizationProvider};
	 * 
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		};
		this.connected = false;
	}

	/**
	 * Used to diconnect the registered listener.
	 */
	public void disconnect() {
		this.provider.removeLocalizationListener(listener);
		this.connected = false;
	}

	/**
	 * Used to connect the registered listener.
	 */
	public void connect() {
		if (this.connected == true) {
			return;
		}
		this.provider.addLocalizationListener(listener);
		this.connected = true;
	}

	/**
	 * Returns the localized translation.
	 */
	@Override
	public String getString(String key) {
		return this.provider.getString(key);
	}

}
