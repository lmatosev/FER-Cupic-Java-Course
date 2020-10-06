package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple model of a hash table. Keys are not allowed to be null, but values
 * are.
 * 
 * @author Lovro Matošević
 *
 * @param <K> - acceptable type of key
 * @param <V> - acceptable type of value
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	public static final int DEFAULT_CAPACITY = 16;
	private TableEntry<K, V>[] table;
	private int size;
	private int occupancy;
	private int modificationCount;

	/**
	 * The default constructor, which sets the capacity to default capacity.
	 */

	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which accepts an integer and sets this SimpleHashtable's capacity
	 * to the given integer.
	 * 
	 * @param capacity - the capacity to be set
	 */

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}
		int trueCapacity = 1;
		while (trueCapacity < capacity) {
			trueCapacity = trueCapacity * 2;
		}
		this.table = (TableEntry<K, V>[]) new TableEntry[trueCapacity];
		this.occupancy = 0;
		this.modificationCount = 0;
	}

	/**
	 * Associates the specified value with the specified key in this dictionary.
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException - if the key provided is null
	 */

	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		TableEntry<K, V> newEntry = new TableEntry<>(key, value);
		int slotNumber = Math.abs(key.hashCode() % this.table.length);
		if (this.containsKey(key)) {
			TableEntry<K, V> tempEntry = this.table[slotNumber];
			while (!tempEntry.getKey().equals(key)) {
				tempEntry = tempEntry.next;
			}
			tempEntry.setValue(value);
			return;

		} else {
			if (this.table[slotNumber] == null) {
				this.table[slotNumber] = newEntry;
				this.occupancy++;
				if (this.occupancy >= 0.75 * this.table.length) {
					resizeHashMap();
				}
			} else {
				TableEntry<K, V> tempEntry = this.table[slotNumber];
				while (tempEntry.next != null) {
					tempEntry = tempEntry.next;
				}
				tempEntry.next = newEntry;
			}
		}

		this.modificationCount++;
		this.size++;
	}

	/**
	 * Helper method used to resize the HashMap when the occupancy is greater or
	 * equal than 75% of the number of slots.
	 */

	@SuppressWarnings("unchecked")
	private void resizeHashMap() {
		int newCapacity = this.table.length * 2;
		int newOccupancy = 0;
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[newCapacity];
		for (int i = 0; i < this.table.length; i++) {
			if (table[i] != null) {
				TableEntry<K, V> tempEntry = this.table[i];
				while (tempEntry != null) {
					this.resizeHashMapPut(tempEntry.getKey(), tempEntry.getValue(), newTable, newOccupancy);
					tempEntry = tempEntry.next;
				}
			}
		}
		this.table = newTable;
	}

	/**
	 * Another helper method used when resizing. Puts the specified key-value pair
	 * to the new table while tracking the new occupancy.
	 * 
	 * @param key
	 * @param value
	 * @param newTable
	 * @param newOccupancy
	 */

	private void resizeHashMapPut(K key, V value, TableEntry<K, V>[] newTable, int newOccupancy) {
		int slotNumber = Math.abs(key.hashCode() % newTable.length);
		TableEntry<K, V> newEntry = new TableEntry<>(key, value);
		if (newTable[slotNumber] == null) {
			newTable[slotNumber] = newEntry;
			newOccupancy++;
		} else {
			TableEntry<K, V> tempEntry = newTable[slotNumber];
			while (tempEntry.next != null) {
				tempEntry = tempEntry.next;
			}
			tempEntry.next = newEntry;
		}

	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * @param key
	 * @return value
	 */

	public V get(Object key) {
		if (!this.containsKey(key)) {
			return null;
		}
		int slotNumber = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> tempEntry = this.table[slotNumber];
		while (!tempEntry.getKey().equals(key)) {
			tempEntry = tempEntry.next;
		}
		return tempEntry.getValue();
	}

	/**
	 * Returns the size of the dictionary.
	 * 
	 * @return int - size
	 */

	public int size() {
		return this.size;
	}

	/**
	 * Checks if this SimpleHashtable contains a specific key.
	 * 
	 * @param key - the key to be checked
	 * @return boolean - true if the key is contained, and false else.
	 */

	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		int slotNumber = Math.abs(key.hashCode() % this.table.length);
		if (table[slotNumber] == null) {
			return false;
		} else {
			TableEntry<K, V> tempEntry = this.table[slotNumber];
			while (tempEntry != null) {
				if (tempEntry.getKey().equals(key)) {
					return true;
				}
				tempEntry = tempEntry.next;
			}
		}
		return false;
	}

	/**
	 * Checks if this SimpleHashtable contains a specific value.
	 * 
	 * @param value - the value to be checked
	 * @return boolean - true if the value is contained, and false else.
	 */

	public boolean containsValue(Object value) {
		for (int i = 0; i < this.table.length; i++) {
			if (table[i] == null) {
				continue;
			} else {
				TableEntry<K, V> tempEntry = this.table[i];
				while (tempEntry != null) {
					if (tempEntry.getValue().equals(value)) {
						return true;
					}
					tempEntry = tempEntry.next;
				}
			}
		}
		return false;
	}

	/**
	 * Removes a record whose key is equal to the user given key.
	 * 
	 * @param key
	 */

	public void remove(Object key) {
		if (!this.containsKey(key)) {
			return;
		}
		int slotNumber = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> tempEntry = this.table[slotNumber];
		this.size--;
		this.modificationCount++;
		if (tempEntry.getKey().equals(key)) {
			table[slotNumber] = tempEntry.next;
			return;
		}
		while (!tempEntry.next.getKey().equals(key)) {
			tempEntry = tempEntry.next;
		}
		tempEntry.next = tempEntry.next.next;

	}

	/**
	 * Checks if this SimpleHashtable is empty.
	 * 
	 * @return boolean - true if it is empty, and false else.
	 */

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public void clear() {
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
		this.occupancy = 0;
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * Returns the string representation of the current SimpleHashMap.
	 */

	public String toString() {
		String output = "[";
		for (int i = 0; i < this.table.length; i++) {
			if (table[i] != null) {
				TableEntry<K, V> tempEntry = this.table[i];
				while (tempEntry != null) {
					if (output.equals("[")) {
						output += tempEntry.toString();
					} else {
						output = output + ", " + tempEntry.toString();
					}
					tempEntry = tempEntry.next;
				}

			}
		}

		output += "]";
		return output;
	}

	/**
	 * Class used for storing key-value pairs. It is implemented as a linked list.
	 *
	 * @param <K>
	 * @param <V>
	 */

	public static class TableEntry<K, V> {
		/**
		 * The stored key.
		 */
		private K key;
		/**
		 * The stored value.
		 */
		private V value;
		/**
		 * Pointer to the next table entry in the list.
		 */
		private TableEntry<K, V> next;

		/**
		 * Main constructor for this class. Accepts and sets the key and the value.
		 * 
		 * @param key
		 * @param value
		 */

		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * Returns the stored key.
		 */

		public K getKey() {
			return this.key;
		}

		/**
		 * Returns the stored value.
		 */

		public V getValue() {
			return this.value;
		}

		/**
		 * Sets the stored value to the user given value.
		 * 
		 * @param value
		 */

		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the string representation of this TableEntry in form of "key=value".
		 */

		@Override
		public String toString() {
			String output = this.key + "=" + this.value;
			return output;
		}

	}

	/**
	 * Factory method for the iterator.
	 */

	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}

	/**
	 * 
	 * Represents an implementation of an iterator which is used to iterate over
	 * elements of this Hashtable.
	 *
	 */

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Used to track the current element returned.
		 */
		private int current;
		/**
		 * Hashtable which is being iterated through.
		 */
		private SimpleHashtable<K, V> Hashtable;
		/**
		 * Key of the last element returned. Used for the remove() method.
		 */
		private K lastReturned;
		/**
		 * Used to track modification count.
		 */
		private int iteratorModificationCount;

		/**
		 * Main constructor for this class.
		 * 
		 * @param simpleHashtable
		 */

		public IteratorImpl(SimpleHashtable<K, V> simpleHashtable) {
			this.Hashtable = simpleHashtable;
			this.current = 0;
			this.lastReturned = null;
			this.iteratorModificationCount = simpleHashtable.modificationCount;
		}

		/**
		 * Checks if there are still elements left to be iterated through.
		 * 
		 * @return boolean - returns true if there are remaining elements to be
		 *         returned, and false else.
		 * @throws ConcurrentModificationException - if the collection has been modified
		 */

		@Override
		public boolean hasNext() {
			if (this.iteratorModificationCount != this.Hashtable.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (this.current < Hashtable.size) {
				return true;
			}
			return false;
		}

		/**
		 * Finds and returns the next element.
		 * 
		 * @return TableEntry
		 * @throws NoSuchElementException - if there are no more elements left.
		 */

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			int counter = 0;
			for (int i = 0; i < Hashtable.table.length; i++) {
				if (Hashtable.table[i] != null) {
					TableEntry<K, V> tempEntry = Hashtable.table[i];
					while (tempEntry != null) {
						if (this.current == counter) {
							this.current++;
							this.lastReturned = tempEntry.getKey();
							return tempEntry;
						}
						tempEntry = tempEntry.next;
						counter++;
					}
				}
			}

			return null;
		}

		/**
		 * Removes the element that was last returned by the next() method.
		 * 
		 * @throws ConcurrentModificationException - if the collection was modified
		 * @throws IllegalStateException           - if the remove() method was called
		 *                                         twice before calling the next()
		 *                                         method.
		 */

		@Override
		public void remove() {
			if (this.iteratorModificationCount != this.Hashtable.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (!this.Hashtable.containsKey(lastReturned)) {
				throw new IllegalStateException();
			}
			this.iteratorModificationCount++;
			this.Hashtable.remove(lastReturned);
			this.current--;
		}

	}

}
