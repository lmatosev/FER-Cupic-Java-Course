package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * 
 * Listener used for listening for selected color changes.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public interface ColorChangeListener {

	/**
	 * Executes an action when there is a new color selected.
	 * 
	 * @param source   - source of change
	 * @param oldColor - old color
	 * @param newColor - new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
