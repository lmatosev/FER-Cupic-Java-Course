package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Editor for {@link GeometricalObject}. Used to edit objects and contains
 * methods to check the editing and accept it.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8865050037641889447L;

	/**
	 * Checks if the editing was valid.
	 */
	public abstract void checkEditing();

	/**
	 * Accepts the editing.
	 */
	public abstract void acceptEditing();

}
