package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class represents an implementation of the {@link ILocalizationProvider}.
 * Adds the ability to register, de-register and inform listeners. It does not
 * implement the getString() method.
 * 
 * @author Lovro Matošević
 *
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Registered listeners
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Main constructor used to initialize the class.
	 */
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}

	/**
	 * Adds the provided {@link ILocalizationListener} to the list of registered
	 * listeners.
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes the given {@link ILocalizationListener} from the list of registered
	 * listeners.
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Informs all registered listeners that there is a change in localization.
	 */
	public void fire() {

		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

}
