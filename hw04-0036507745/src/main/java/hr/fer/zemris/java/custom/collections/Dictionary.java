package hr.fer.zemris.java.custom.collections;

/**
 * Represents a simple model of a dictionary. Keys are not allowed to be null,
 * values are.
 * 
 * @author Lovro Matošević
 *
 * @param <K> - type to be used as key
 * @param <V> - type to be used as value
 */

public class Dictionary<K, V> {
	/**
	 * Collection used for storage of K,V pairs.
	 */
	private ArrayIndexedCollection<StoredPair> internalCol;

	/**
	 * The default constructor.
	 */

	public Dictionary() {
		this.internalCol = new ArrayIndexedCollection<>();
	}

	/**
	 * Checks if the dictionary is empty.
	 * 
	 * @return boolean - true if the collection is empty, and false else.
	 */

	public boolean isEmpty() {
		return internalCol.size() == 0;
	}

	/**
	 * Returns the size of the dictionary.
	 * 
	 * @return int - size
	 */

	public int size() {
		return internalCol.size();
	}

	/**
	 * Clears all the elements from the dictionary.
	 */

	public void clear() {
		internalCol.clear();
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
		StoredPair newPair = new StoredPair(key, value);

		for (int i = 0; i < this.internalCol.size(); i++) {
			if (internalCol.get(i).getKey() == key) {
				internalCol.remove(i);
				internalCol.insert(newPair, i);
				return;
			}
		}

		internalCol.add(newPair);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * @param key
	 * @return value
	 */

	public V get(Object key) {
		for (int i = 0; i < this.internalCol.size(); i++) {
			if (key.equals(this.internalCol.get(i).getKey())) {
				return this.internalCol.get(i).getValue();
			}
		}

		return null;
	}

	/**
	 * Class used for storing key and value pairs.
	 * 
	 *
	 */

	private class StoredPair {
		private K key;
		private V value;

		public StoredPair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for the key stored.
		 * 
		 * @return key
		 */

		public K getKey() {
			return this.key;
		}

		/**
		 * Getter for the value stored.
		 * 
		 * @return value
		 */

		public V getValue() {
			return this.value;
		}
	}
}
