package hr.fer.zemris.java.custom.collections;

import java.util.*;

/**
 * 
 * This class represents a linked list implementation of the Collection class.
 * 
 * All methods throw NullPointerException if an object or a collection provided
 * is null.
 * 
 * @author Lovro Matošević
 *
 */

public class LinkedListIndexedCollection<T> implements List<T> {

	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount;

	/**
	 * Default constructor for the collection.
	 */

	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.modificationCount = 0;
	}

	/**
	 * This constructor adds all the elements of a user provided collection to the
	 * current collection
	 * 
	 * @param other - the collection whose elements will be added to this collection
	 */

	public LinkedListIndexedCollection(Collection<T> other) {
		this();
		if (other == null) {
			throw new NullPointerException();
		}
		this.addAll(other);
		this.size = other.size();
	}

	/**
	 * 
	 * Private class that represents a node in a linked list. It contains pointers
	 * to the previous and the next node and also stores the value of a single
	 * object.
	 *
	 */

	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;

		public ListNode(Object value) {
			this.value = value;
			this.previous = null;
			this.next = null;
		}

		@SuppressWarnings("unused")
		public ListNode() {
			this(null);
		}

	}

	/**
	 * Returns the current size of the collection.
	 */

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Checks if the user provided object is contained in this collection.
	 * 
	 * @return boolean - returns true if the object is inside, and false if it is
	 *         not.
	 */

	@Override
	public boolean contains(Object value) {
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			if (tempNode.value.equals(value)) {
				return true;
			}
			tempNode = tempNode.next;
		}
		return false;
	}

	/**
	 * Converts this collection to an array of objects.
	 * 
	 * @return Object[]
	 */

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		T[] returnArray = (T[]) new Object[this.size];
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			returnArray[i] = (T) tempNode.value;
			tempNode = tempNode.next;
		}
		return returnArray;
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
		ListNode newNode = new ListNode(value);
		if (this.first == null) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		}
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Returns the Object which is located at the user provided index.
	 * 
	 * It runs in O(n/2) time.
	 * 
	 * @param index the index from which the object will be returned
	 * @return Object - the object at the specified index
	 * @throws IndexOutOfBoundException if the index provided is invalid.
	 */

	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (index < (size / 2)) {
			ListNode tempNode = this.first;
			for (int i = 0; i < (this.size / 2) + 1; i++) {
				if (i == index) {
					return (T) tempNode.value;
				}
				tempNode = tempNode.next;
			}
		} else {
			ListNode tempNode = this.last;
			for (int i = this.size - 1; i > (this.size / 2) - 1; i--) {
				if (i == index) {
					return (T) tempNode.value;
				}
				tempNode = tempNode.previous;
			}
		}
		return null;
	}

	/**
	 * Clears all the elements from the collection.
	 */

	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
		this.modificationCount++;

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

	public void insert(T value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode tempNode = this.first;
		ListNode newNode = new ListNode(value);

		for (int i = 0; i < this.size; i++) {
			if (i == position) {
				newNode.next = tempNode;
				newNode.previous = tempNode.previous;
				tempNode.previous.next = newNode;
				tempNode.previous = newNode;
				this.size++;
				return;
			}
			tempNode = tempNode.next;
		}

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
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			if (tempNode.value.equals(value)) {
				return i;
			}
			tempNode = tempNode.next;
		}
		return -1;
	}

	/**
	 * Removes a specific object from the list if it is contained.
	 * 
	 * @param value - the Object that should be removed
	 * @return boolean - returns true if the object was successfully removed, and
	 *         false if it wasn't.
	 */

	@Override
	public boolean remove(Object obj) {
		ListNode tempNode = this.first;

		for (int i = 0; i < this.size; i++) {
			if (tempNode.value.equals(obj)) {
				this.remove(i);
			}
			tempNode = tempNode.next;
		}
		return false;
	}

	/**
	 * Removes the element contained on the specific index in the collection.
	 * 
	 * @param index the index of the element that should be removed.
	 * @throws IndexOutOfBoundsException if the index provided is invalid.
	 */

	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			if (i == index) {
				if (i == 0) {
					this.first = this.first.next;
					this.first.previous = null;
				} else if (i == this.size - 1) {
					this.last = tempNode.previous;
					this.last.next = null;
				} else {
					tempNode.previous.next = tempNode.next;
					tempNode.next.previous = tempNode.previous;
				}
			}
			tempNode = tempNode.next;
		}
		this.size--;
		this.modificationCount++;

	}

	/**
	 * Creates a new instance of ElementsGetter
	 * 
	 * @return ElementsGetter
	 */

	@Override
	public ElementsGetter<T> createElementsGetter() {
		ElementsGetter<T> el = new LinkedListElementsGetter<T>(this);
		return el;
	}

	/**
	 * 
	 * Class which represents an implementation of the ElementsGetter interface. It
	 * is used to iterate through the linked list.
	 *
	 */

	private static class LinkedListElementsGetter<T> implements ElementsGetter<T> {
		private ListNode currentNode;
		private long savedModificationCount;
		private LinkedListIndexedCollection<T> collection;

		/**
		 * Constructor for the ArrayElementsGetter class.
		 * 
		 * @param col - instance of current collection
		 */

		public LinkedListElementsGetter(LinkedListIndexedCollection<T> col) {
			this.currentNode = col.first;
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
			if (this.currentNode == null) {
				return false;
			}
			if (this.savedModificationCount != this.collection.modificationCount) {
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

		public T getNextElement() {
			if (this.savedModificationCount != this.collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (this.hasNextElement() == false) {
				throw new NoSuchElementException();
			}
			@SuppressWarnings("unchecked")
			T value = (T) this.currentNode.value;
			this.currentNode = this.currentNode.next;
			return value;
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
