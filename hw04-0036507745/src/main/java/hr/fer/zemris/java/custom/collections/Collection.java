package hr.fer.zemris.java.custom.collections;

/**
 * 
 * This interface represents a simple model of a collection.
 * 
 * @author Lovro Matošević
 * @version 1.0
 */

public interface Collection<T> {

	/**
	 * Function which checks if the collection is empty.
	 * 
	 * @return boolean Function returns true if the collection is empty, and false
	 *         if it isn't.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns collection size.
	 * 
	 * @return int - size of the collection
	 */
	abstract int size();

	/**
	 * Is used to add an object to the collection.
	 * 
	 * @param value value to be added
	 */

	abstract void add(T value);

	/**
	 * Is used to determine if an object is contained in the collection.
	 * 
	 * @param value value to be checked
	 * @return boolean - true if the value is contained, and false else
	 */

	abstract boolean contains(Object value);

	/**
	 * Is used to remove an object from the collection.
	 * 
	 * @param value value to be removed
	 * @remove boolean - returns true if the object was successfully removed, and
	 *         false else
	 */

	abstract boolean remove(T value);

	/**
	 * Is used to convert the collection to an Object array.
	 * 
	 */

	abstract T[] toArray();

	/**
	 * Calls the processor's process method for every element of this collection.
	 * 
	 * @param processor
	 */

	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> elemGet = this.createElementsGetter();
		while (elemGet.hasNextElement()) {
			processor.process(elemGet.getNextElement());
		}
	}

	/**
	 * Function that adds all the elements of a collection to the current
	 * collection.
	 * 
	 * @param other Collection whose elements should be added to the current
	 *              collection.
	 */

	default void addAll(Collection<? extends T> other) {

		class MyProcessor<E extends T> implements Processor<E> {

			public void process(E value) {
				add(value);
			}

		}

		Processor<? super T> processor = new MyProcessor<>();

		other.forEach(processor);

	}

	/**
	 * Removes all the elements from the collection.
	 **/

	abstract void clear();

	/**
	 * Creates an instance of the ElementsGetter class.
	 * 
	 * @return ElementsGetter
	 */

	abstract ElementsGetter<T> createElementsGetter();

	/**
	 * Adds all the elements from the Collection col to the current collection if
	 * the condition tested by tester is true.
	 * 
	 * @param col - collection whose elements will be tested and added
	 * @param tester
	 */

	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> elemGet = col.createElementsGetter();
		while (elemGet.hasNextElement()) {
			T element = (T) elemGet.getNextElement();
			if (tester.test(element) == true) {
				this.add(element);
			}
		}
	}


}
