package hr.fer.zemris.java.custom.collections;

import java.util.EmptyStackException;

/**
 * Class that models a simple object stack with some basic stack methods.
 * 
 * @author Lovro Matošević
 *
 */

public class ObjectStack {

	private ArrayIndexedCollection internalCol;

	/**
	 * Default constructor for this class.
	 */

	public ObjectStack() {
		this.internalCol = new ArrayIndexedCollection();
	}

	/**
	 * Method used to check if the stack is empty.
	 * 
	 * @return boolean - true if the stack is empty and false if it is not.
	 */
	public boolean isEmpty() {
		return this.internalCol.isEmpty();
	}

	/**
	 * 
	 * @return int - returns the current size of the stack.
	 */

	public int size() {
		return this.internalCol.size();
	}

	/**
	 * Pushes the provided Object to the stack.
	 * 
	 * @param value - the object to be added
	 */
	public void push(Object value) {
		this.internalCol.add(value);
	}

	/**
	 * Removes and returns the object last pushed to the stack.
	 * 
	 * @return Object - returns the object last pushed to the stack
	 * @throws EmptyStackException - is thrown if there is an attempt to pop from an
	 *                             empty stack
	 */

	public Object pop() {
		if (this.isEmpty()) {
			throw new hr.fer.zemris.java.custom.collections.EmptyStackException();
		}
		Object returnObject = this.internalCol.get(this.internalCol.size() - 1);
		this.internalCol.remove(this.internalCol.size() - 1);
		return returnObject;
	}
	/**
	 * Returns the last object pushed to the stack but doesn't remove it.
	 * 
	 * @return Object - returns the last object pushed to the stack.
	 * @throws EmptyStackException - is thrown if there is an attempt to peek from
	 *                             an empty stack
	 */

	public Object peek() {
		if (this.isEmpty()) {
			throw new hr.fer.zemris.java.custom.collections.EmptyStackException();
		}
		return this.internalCol.get(this.internalCol.size() - 1);
	}

	/**
	 * Removes all the elements from the stack.
	 */

	void clear() {
		this.internalCol.clear();
	}

}
