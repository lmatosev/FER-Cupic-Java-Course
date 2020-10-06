package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * 
 * Class representing a model of a geometrical object.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public abstract class GeometricalObject {

	/**
	 * list of {@link GeometricalObject} listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Creates and returns a new {@link GeometricalObjectEditor}.
	 * 
	 * @return editor - {@link GeometricalObjectEditor} specific for this object.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Accepts the provided {@link GeometricalObjectVisitor}.
	 * 
	 * @param v - visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Adds the provided {@link GeometricalObjectListener} to the list of listeners
	 * 
	 * @param listener - listener to be added
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener listener) {

		listeners.add(listener);

	}

	/**
	 * Removes the provided {@link GeometricalObjectListener} from the list of
	 * listeners.
	 * 
	 * @param listener - listener to be removed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener listener) {

		listeners.remove(listener);

	}

	/**
	 * Method used to notify all the registered listeners that the object was changed.
	 */
	public void fire() {

		for (var listener : listeners) {

			listener.geometricalObjectChanged(this);

		}

	}

}
