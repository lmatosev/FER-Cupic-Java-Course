package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 
 * Custom implementation of the ListModel which is used as a ListModel of prime
 * numbers.
 * 
 * @author Lovro Matošević
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * Prime number which was last added
	 */
	private int lastAddedPrim;
	/**
	 * List of prime numbers which are stored.
	 */
	private List<Integer> primList;
	/**
	 * List of stored listeners.
	 */
	private List<ListDataListener> listenerList;

	/**
	 * The default constructor.
	 */
	public PrimListModel() {
		this.primList = new ArrayList<>();
		this.listenerList = new ArrayList<>();
		this.primList.add(1);
		this.lastAddedPrim = 1;
	}

	/**
	 * Calculates and returns the next prime number.
	 * 
	 * @return prime - the next prime which was found
	 */
	public int next() {

		for (int i = this.lastAddedPrim + 1; i < i * 50; i++) {
			if (this.isPrim(i)) {
				this.primList.add(i);
				this.lastAddedPrim = i;
				break;
			}
		}

		this.notifyListeners();
		return lastAddedPrim;
	}

	/**
	 * Helper method used to notify all the stored listeners.
	 */
	private void notifyListeners() {
		for (var listener : this.listenerList) {
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, this.primList.size(),
					this.primList.size());
			listener.contentsChanged(event);
		}
	}

	/**
	 * Helper method used to determine if the provided number is a prime number.
	 * 
	 * @param num - number being checked
	 * @return true if the provided number is prime and false else
	 */
	private boolean isPrim(int num) {

		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Adds the provided data listener to the internal list.
	 */
	@Override
	public void addListDataListener(ListDataListener arg0) {
		this.listenerList.add(arg0);
	}

	/**
	 * Returns the element at the provided index of the list.
	 */
	@Override
	public Integer getElementAt(int arg0) {
		return this.primList.get(arg0);
	}

	/**
	 * Returns the current list size.
	 */
	@Override
	public int getSize() {
		return this.primList.size();
	}

	/**
	 * Removes the provided data listener from the internal list.
	 */
	@Override
	public void removeListDataListener(ListDataListener arg0) {
		this.listenerList.remove(arg0);
	}

}
