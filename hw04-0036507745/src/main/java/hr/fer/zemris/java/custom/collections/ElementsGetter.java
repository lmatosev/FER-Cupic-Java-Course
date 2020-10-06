package hr.fer.zemris.java.custom.collections;

/**
 * ElementsGetter consists of three basic methods. One checks if there exists a
 * next element. The second one returns the next element if there exists one.
 * The last one processes all elements that are left.
 *
 */
public interface ElementsGetter<T> {

	/**
	 * Checks if there are any elements left.
	 * 
	 * @return boolean - true if there are still elements to be returned, and false
	 *         else
	 */
	boolean hasNextElement();

	/**
	 * Returns the next element.
	 * 
	 * @return Object - element that is returned
	 */

	T getNextElement();

	/**
	 * Processes all remaining elements.
	 * 
	 * @param processor
	 */

	void processRemaining(Processor<T> p);
}
