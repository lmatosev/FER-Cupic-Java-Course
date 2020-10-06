package hr.fer.zemris.java.custom.collections;

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

public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Default constructor for the collection.
	 */

	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
	}

	/**
	 * This constructor adds all the elements of a user provided collection to the
	 * current collection
	 * 
	 * @param other - the collection whose elements will be added to this collection
	 */

	public LinkedListIndexedCollection(Collection other) {
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
		@SuppressWarnings("unused")
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
	public Object[] toArray() {
		Object[] returnArray = new Object[this.size];
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			returnArray[i] = tempNode.value;
			tempNode = tempNode.next;
		}
		return returnArray;
	}

	/**
	 * Iterates through all the elements of the collection and calls processor's
	 * process method on each one.
	 * 
	 * @param processor - the processor whose method process will be called
	 */

	@Override
	public void forEach(Processor processor) {
		ListNode tempNode = this.first;
		for (int i = 0; i < this.size; i++) {
			processor.process(tempNode.value);
			tempNode = tempNode.next;
		}
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
	public void add(Object value) {
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

	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (index < (size / 2)) {
			ListNode tempNode = this.first;
			for (int i = 0; i < (this.size / 2) + 1; i++) {
				if (i == index) {
					return tempNode.value;
				}
				tempNode = tempNode.next;
			}
		} else {
			ListNode tempNode = this.last;
			for (int i = this.size - 1; i > (this.size / 2) - 1; i--) {
				if (i == index) {
					return tempNode.value;
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

	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode newNode = new ListNode(value);

		if (position < (size / 2)) {
			ListNode tempNode = this.first;
			for (int i = 0; i < (this.size / 2) + 1; i++) {
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
		} else {
			ListNode tempNode = this.last;
			for (int i = this.size - 1; i > (this.size / 2) - 1; i--) {
				if (i == position) {
					newNode.next = tempNode;
					newNode.previous = tempNode.previous;
					tempNode.previous.next = newNode;
					tempNode.previous = newNode;
					this.size++;
					return;
				}
				tempNode = tempNode.previous;
			}
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
		
		if (index < (size / 2)) {
			ListNode tempNode = this.first;
			for (int i = 0; i < (this.size / 2) + 1; i++) {
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
		} else {
			ListNode tempNode = this.last;
			for (int i = this.size - 1; i > (this.size / 2) - 1; i--) {
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
				tempNode = tempNode.previous;
			}
		}
		
	
		this.size--;
	}

}
