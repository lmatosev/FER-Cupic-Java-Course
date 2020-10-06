package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * 
 * Interface representing a model of a color provider. Contains a method to
 * return current color and methods to add and remove
 * {@link ColorChangeListener}s.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public interface IColorProvider {

	/**
	 * Returns the current color
	 * 
	 * @return color - current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the provided listener to the list of listeners.
	 * 
	 * @param l - listener to be added
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the provided listener from the list of listeners.
	 * 
	 * @param l - listener to be removed
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
