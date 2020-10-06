package hr.fer.zemris.java.custom.collections;

import java.util.*;

/**
 * This class presents an array implementation of the Collection class.
 * 
 * The methods of this class throw a <code>NullPointerException</code> if the
 * collections or class objects provided to them are null.
 * 
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class ArrayIndexedCollection<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private int size;
	private T[] elements;
	private long modificationCount;

	/**
	 * The default constructor which sets initial capacity to 16.
	 */

	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * The constructor which sets the initial capacity that the user provides.
	 * 
	 * @param initialCapacity - initial capacity that will be used for the
	 *                        collection
	 * @throws IllegalArgumentException if the capacity provided is less than 1
	 */

	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = (T[]) new Object[initialCapacity];
		this.modificationCount = 0;
		this.size = 0;

	}

	/**
	 * The constructor which adds all elements of a user provided collection to this
	 * collection;
	 * 
	 * @param other - the collection whose elements will be added to this collection
	 * @throws NullPointerException if the collection provided is null
	 */

	public ArrayIndexedCollection(Collection<T> other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * The constructor which accepts a collection to be added to this collection and
	 * sets the initial capacity. If the capacity provided is less than other
	 * collection's capacity, this collection's capacity will be set to other
	 * collection's capacity.
	 * 
	 * @param other           the collection that will be added to this collection
	 * @param initialCapacity the initial capacity to be set
	 * @throws NullPointerException     if the collection provided is null
	 * @throws IllegalArgumentException if the capacity provided is less than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> other, int initialCapacity) {

		if (other == null) {
			throw new NullPointerException();
		}
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		int capacity;
		if (other.size() > initialCapacity) {
			capacity = other.size();
		} else {
			capacity = initialCapacity;
		}

		this.elements = (T[]) new Object[capacity];
		this.modificationCount = 0;
		this.size = 0;
		this.addAll(other);

	}

	/**
	 * Returns the current size of the collection.
	 */

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds the provided object to the current collection.
	 * 
	 * It runs in O(1) time.
	 * 
	 * @param value - the value to be added to this collection
	 * @throws NullPointerException if the object provided is null
	 */

	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException();
		}
		this.insert(value, size);
	}

	/**
	 * Inserts a user provided object to the array at a custom index provided by the
	 * user.
	 * 
	 * It runs in O(n) time.
	 * 
	 * @param value    - the object to be inserted
	 * @param position - the index where the value should be inserted
	 * @throws IndexOutOfBoundsException if the position provided is invalid
	 * @throws NullPointerException      if the value provided is null
	 */

	@SuppressWarnings("unchecked")
	public void insert(T value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null) {
			throw new NullPointerException();
		}

		if (this.size() == this.elements.length) {
			int capacity = this.elements.length*2;
			Object[] tempArray = new Object[capacity];
			for (int i = 0; i < this.size; i++) {
				tempArray[i] = this.elements[i];
			}
			this.elements = (T[]) tempArray;
		}
		for (int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}

		this.elements[position] = (T) value;
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Returns the Object which is located at the user provided index.
	 * 
	 * It runs in O(n) time.
	 * 
	 * @param index the index from which the object will be returned
	 * @return Object - the object at the specified index
	 * @throws IndexOutOfBoundException if the index provided is invalid.
	 */

	public T get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}

	/**
	 * Clears all the elements from the array.
	 */

	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * Checks if the user provided object is contained in this collection.
	 * 
	 * @return boolean - returns true if the object is inside, and false if it is
	 *         not.
	 */

	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		if(this.indexOf(value)!=-1) {
			return true;
		}
		return false;
	}

	/**
	 * Converts this collection to an array of objects.
	 * 
	 * @return Object[]
	 */
	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		return newArray;
	}

	/**
	 * Returns the index of the user provided object.
	 * 
	 * @param value the object that will be checked
	 * @return int - returns the index of the specified object in case it is
	 *         contained in the array, and -1 if it isn't.
	 */

	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes the element contained on the specific index in the collection.
	 * 
	 * @param index the index of the element that should be removed.
	 * @throws IndexOutOfBoundsException if the index provided is invalid.
	 */

	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = index; i < this.size; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.size--;
		this.modificationCount++;
	}

	/**
	 * Removes a specific object from the list if it is contained.
	 * 
	 * @param value - the Object that should be removed
	 * @return boolean - returns true if the object was successfully removed, and
	 *         false if it wasn't.
	 */

	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return int - collection capacity
	 */
	public int getCapacity() {
		return this.elements.length;
	}

	/**
	 * Creates a new instance of ElementsGetter
	 * 
	 * @return ElementsGetter
	 */

	@Override
	public ElementsGetter<T> createElementsGetter() {
		ElementsGetter<T> el = new ArrayElementsGetter<T>(this);
		return el;
	}

	/**
	 * 
	 * Class which represents an implementation of the ElementsGetter interface. It
	 * is used to iterate through the array.
	 *
	 */

	private static class ArrayElementsGetter<T> implements ElementsGetter<T> {
		private int currentIndex;
		private T[] getterElements;
		private int size;
		private long savedModificationCount;
		private ArrayIndexedCollection<T> collection;

		/**
		 * Constructor for the ArrayElementsGetter class.
		 * 
		 * @param col - instance of current collection
		 */

		public ArrayElementsGetter(ArrayIndexedCollection<T> col) {
			this.currentIndex = 0;
			this.getterElements = (T[]) col.elements;
			this.size = col.size;
			this.savedModificationCount = col.modificationCount;
			this.collection = col;
		}

		/**
		 * Checks if there are any remaining elements.
		 * 
		 * @return boolean - false if there are no elements left and true else
		 * @throws ConcurrentModificationException - if the collection was modified
		 *                                         after the creation of this instance
		 *                                         of ElementsGetter
		 */

		public boolean hasNextElement() {
			if (currentIndex == this.size) {
				return false;
			}
			if (this.collection.modificationCount != this.savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			return true;
		}

		/**
		 * Returns the next element.
		 * 
		 * @return Object - next element
		 * @throws ConcurrentModificationException - if the collection was modified
		 *                                         after the creation of this instance
		 *                                         of ElementsGetter
		 * 
		 * @throws NoSuchElementException          - if there are no more elements to be
		 *                                         returned
		 */

		@SuppressWarnings("unchecked")
		public T getNextElement() {
			if (this.hasNextElement() == false) {
				throw new NoSuchElementException();
			}
			if (this.collection.modificationCount != this.savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			Object value = this.getterElements[currentIndex];
			this.currentIndex++;
			return (T) value;
		}

		/**
		 * Processes all the remaining elements.
		 * 
		 * @param Processor - the processor whose method process will be called
		 */

		@Override
		public void processRemaining(Processor<T> p) {
			while (this.hasNextElement()) {
				p.process(this.getNextElement());
			}

		}
	}

}
