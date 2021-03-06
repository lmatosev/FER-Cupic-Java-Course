package hr.fer.zemris.java.custom.collections;

/**
 * An ordered collection. The user of this interface has precise control over
 * where in the list each element is inserted. The user can access elements by
 * their integer index (position in the list), and search for elements in the
 * list.
 *
 * 
 */

public interface List<T> extends Collection<T> {

	/**
	 * Returns the Object at the user provided index.
	 * 
	 * @param index
	 * @return Object - object at the given index
	 */

	T get(int index);

	/**
	 * Inserts an object to the specified position.
	 * 
	 * @param value
	 * @param position
	 */

	void insert(T value, int position);

	/**
	 * Returns the index of the specified object.
	 * 
	 * @param value
	 * @return int - the index of the object
	 */

	int indexOf(T value);

	/**
	 * Removes the object at the user specified index.
	 * 
	 * @param index - index of the object to be removed
	 */

	void remove(int T);

}
