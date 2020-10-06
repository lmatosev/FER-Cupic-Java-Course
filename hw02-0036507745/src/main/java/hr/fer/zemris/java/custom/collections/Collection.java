package hr.fer.zemris.java.custom.collections;

import java.io.UnsupportedEncodingException;

/**
 * 
 * This class represents a simple model of a collection.
 * 
 * @author Lovro Matošević
 * @version 1.0
 */
public class Collection {

	protected Collection() {

	}

	/**
	 * Function which checks if the collection is empty.
	 * 
	 * @return boolean Function returns true if the collection is empty, and false
	 *         if it isn't.
	 */
	boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Is used to return size in classes that extend this class.
	 * 
	 * @return int Function always returns 0
	 */
	int size() {
		return 0;
	}

	/**
	 * Is used to add an object in classes that extend this class.
	 * 
	 * @param value value to be added
	 */

	void add(Object value) {

	}

	/**
	 * Is used to determine if an object is contained in the collection in classes
	 * that extend this class.
	 * 
	 * @param value value to be checked
	 * @return false always returns false
	 */

	boolean contains(Object value) {
		return false;
	}

	/**
	 * Is used to remove an object in classes that extend this class.
	 * 
	 * @param value value to be removed
	 * @remove false always returns false
	 */

	boolean remove(Object value) {
		return false;
	}

	/**
	 * Is used to convert the collection to an Object array in classes that extend
	 * this class.
	 * 
	 * @throws UnsupportedOperationException - is always thrown
	 */

	Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * The method in this exact class does nothing but will be used in extending
	 * classes to iterate through all the elements and do something.
	 * 
	 * @param processor
	 */

	void forEach(Processor processor) {

	}

	/**
	 * Function that adds all the elements of a collection to the current
	 * collection.
	 * 
	 * @param other Collection whose elements should be added to the current
	 *              collection.
	 */

	void addAll(Collection other) {

		class MyProcessor extends Processor {

			public void process(Object value) {
				add(value);
			}

		}

		Processor processor = new MyProcessor();

		other.forEach(processor);

	}

	/**
	 * Function that will be used in classes that extend this class to remove all
	 * the elements.
	 */

	void clear() {

	}

}
