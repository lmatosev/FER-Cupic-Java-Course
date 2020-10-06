package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Listener for {@link GeometricalObject}.
 * 
 * @author Lovro Matošević
 *
 */
public interface GeometricalObjectListener {

	/**
	 * 
	 * 
	 * @param o - object which was changed
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
