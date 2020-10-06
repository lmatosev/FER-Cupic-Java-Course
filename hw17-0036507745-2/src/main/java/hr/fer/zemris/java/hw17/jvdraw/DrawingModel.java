package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Representation of a drawing model which contains several methods such as
 * adding, removing and changing order of stored geometrical objects.
 * 
 * 
 * @author lmatosev
 *
 */

public interface DrawingModel {

	/**
	 * Returns the model size
	 * 
	 * @return size - {@link DrawingObjectListModel} size
	 */
	public int getSize();

	/**
	 * Returns the object at the provided index
	 * 
	 * @param index - index of the object to be returned
	 * @return object - object at the given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the provided {@link GeometricalObject} to this model
	 * 
	 * @param object - object to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the provided {@link GeometricalObject} from this model
	 * 
	 * @param object - object to be removed
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes the {@link GeometricalObject} order by the given offset
	 * 
	 * @param object - object which will act as a reference point for changing the
	 *               order
	 * @param offset - offset to be used
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns the index of the provided object or null if it does not exist in this
	 * model.
	 * 
	 * @param object - object whose index will be returned
	 * @return index - index of the provided object
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clears the model
	 */
	public void clear();

	/**
	 * Resets the modified flag
	 */
	public void clearModifiedFlag();

	/**
	 * Returns the value of modified flag
	 * 
	 * @return value - true if the object was modified and false else
	 */
	public boolean isModified();

	/**
	 * Adds the provided listener to model's list of listeners
	 * 
	 * @param l - listener to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the provided listener from the model's list of listeners
	 * 
	 * @param l - listener to be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

}
