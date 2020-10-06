package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class used for storing integer values. Also stores a list of observers.
 *
 */
public class IntegerStorage {
	/**
	 * Value stored
	 */
	private int value;
	/**
	 * The list of observers
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * The main constructor for this class.
	 * 
	 * @param initialValue - the value to be stored
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Adds the provided observer to the list of observers
	 * 
	 * @param observer - observer to be added
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the provided observer from the list of observers.
	 * 
	 * @param observer - observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Clears the observer list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * @return value stored
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value and informs the observers if the value has been changed.
	 * 
	 * @param value - value to be set
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				List<IntegerStorageObserver> observers2 = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observers2) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
