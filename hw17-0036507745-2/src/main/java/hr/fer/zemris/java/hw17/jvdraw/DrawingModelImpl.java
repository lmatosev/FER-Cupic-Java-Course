package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Implementation of the {@link DrawingModel} interface.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class DrawingModelImpl implements DrawingModel {

	/**
	 * list of stored {@link GeometricalObject} objects
	 */
	private List<GeometricalObject> objects;
	/**
	 * modified flag
	 */
	private boolean modified;
	/**
	 * list of registered listeners
	 */
	private List<DrawingModelListener> listeners;

	/**
	 * Main constructor for this class.
	 */
	public DrawingModelImpl() {
		this.objects = new ArrayList<>();
		this.modified = false;
		this.listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return this.objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return this.objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		this.objects.add(object);
		this.modified = true;

		object.addGeometricalObjectListener(new GeometricalObjectListener() {

			@Override
			public void geometricalObjectChanged(GeometricalObject o) {

				DrawingModelImpl impl = DrawingModelImpl.this;

				int index = impl.indexOf(o);

				for (var listener : impl.listeners) {

					listener.objectsChanged(impl, index, index);

				}

			}
		});

		int index = this.objects.indexOf(object);

		for (var listener : listeners) {

			listener.objectsAdded(this, index, index);

		}

		this.modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {

		int index = this.objects.indexOf(object);

		this.objects.remove(object);

		for (var listener : listeners) {

			listener.objectsRemoved(this, index, index);

		}

		this.modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {

		int index = this.objects.indexOf(object);

		if (index > -1) {

			if (offset > 0) {

				for (int i = index; i > Math.max(0, index - offset); i--) {

					GeometricalObject obj1 = this.objects.get(i);
					GeometricalObject obj2 = this.objects.get(i - 1);

					this.objects.remove(i);
					this.objects.add(i, obj2);
					this.objects.remove(i - 1);
					this.objects.add(i - 1, obj1);

					for (var listener : listeners) {

						listener.objectsChanged(this, index, Math.max(0, index - offset));

					}

				}

			} else {

				for (int i = index; i < Math.min(this.objects.size() - 1, index - offset); i++) {

					GeometricalObject obj1 = this.objects.get(i);
					GeometricalObject obj2 = this.objects.get(i + 1);

					this.objects.remove(i);
					this.objects.add(i, obj2);
					this.objects.remove(i + 1);
					this.objects.add(i + 1, obj1);

				}

				for (var listener : listeners) {

					listener.objectsChanged(this, index, Math.min(this.objects.size(), index - offset));

				}

			}

		}

		this.modified = true;

	}

	@Override
	public int indexOf(GeometricalObject object) {
		return this.objects.indexOf(object);
	}

	@Override
	public void clear() {
		this.objects.clear();
		this.modified = true;

		for (var listener : listeners) {

			listener.objectsRemoved(this, 0, this.objects.size());

		}
	}

	@Override
	public void clearModifiedFlag() {
		this.modified = false;
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		this.listeners.remove(l);
	}

}
