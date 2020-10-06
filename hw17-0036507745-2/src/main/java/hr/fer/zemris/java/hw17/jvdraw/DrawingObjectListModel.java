package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * List model used by {@link JVDraw} to display drawn geometrical objects.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624131616624069627L;

	/**
	 * stored model
	 */
	private DrawingModel model;

	/**
	 * Main constructor for this class
	 * 
	 * @param model - model to be stored
	 */
	public DrawingObjectListModel(DrawingModel model) {

		this.model = model;

		this.model.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return this.model.getObject(index);
	}

	@Override
	public int getSize() {
		return this.model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.fireContentsChanged(source, index0, index1);
	}

}
