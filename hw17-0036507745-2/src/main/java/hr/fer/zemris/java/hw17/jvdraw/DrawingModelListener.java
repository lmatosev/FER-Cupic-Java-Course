package hr.fer.zemris.java.hw17.jvdraw;

/**
 * 
 * Listener for the {@link DrawingModel}.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public interface DrawingModelListener {

	/**
	 * Executes when there are objects added to the model.
	 * 
	 * @param source - model to which the object was added
	 * @param index0 - start of interval
	 * @param index1 - end of interval
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Executes when there are objects removed from the model.
	 * 
	 * @param source - model from which the object was removed
	 * @param index0 - start of interval
	 * @param index1 - end of interval
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Executes when there are object which are changed.
	 * 
	 * @param source - source of change
	 * @param index0 - start of interval
	 * @param index1 - end of interval
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
