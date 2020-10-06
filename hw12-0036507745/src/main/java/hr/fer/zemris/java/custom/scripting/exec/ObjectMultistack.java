package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Works as a special kind of Map. Allows the user to store multiple values for
 * same key. Keys are instances of String, and values are instances of
 * ValueWrapper.
 * 
 * @author Lovro Matošević
 *
 */
public class ObjectMultistack {

	/**
	 * Map used for storing the key-value pairs.
	 */
	Map<String, MultistackEntry> map;

	/**
	 * The default constructor.
	 */
	public ObjectMultistack() {
		this.map = new HashMap<>();
	}

	/**
	 * Pushes the provided ValueWrapper to the stack.
	 * 
	 * @param keyname      - key used for map
	 * @param valueWrapper - value used for map
	 */

	public void push(String keyname, ValueWrapper valueWrapper) {
		if (map.containsKey(keyname)) {
			MultistackEntry entry = map.get(keyname);
			MultistackEntry newEntry = new MultistackEntry(valueWrapper);
			newEntry.next = entry;
			map.put(keyname, newEntry);
		} else {
			MultistackEntry entry = new MultistackEntry(valueWrapper);
			map.put(keyname, entry);
		}
	}

	/**
	 * Pops a single entry from the stack.
	 * 
	 * @param keyName - key of the stack to be popped from
	 * @return ValueWrapper - the wrapper that was stored last using the provided
	 *         key.
	 * 
	 */
	public ValueWrapper pop(String keyName) {
		if (this.isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		MultistackEntry entry = map.get(keyName);
		MultistackEntry tempEntry = entry.next;
		map.remove(keyName);
		if (tempEntry != null) {
			map.put(keyName, tempEntry);
		}

		return entry.getWrapper();
	}

	/**
	 * Returns the ValueWrapper which was pushed last to the stack using the
	 * provided key.
	 * 
	 * @param keyName - key
	 * @return ValueWrapper - value which was last pushed using the provided key
	 */
	public ValueWrapper peek(String keyName) {
		if (this.isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		MultistackEntry entry = map.get(keyName);
		return entry.getWrapper();
	}

	/**
	 * Returns true if the stack is empty, and false else.
	 * 
	 * @param keyName
	 * @return true if the stack with provided key as key is empty and false else
	 */
	public boolean isEmpty(String keyName) {
		if (!this.map.containsKey(keyName)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Used as a node of a single-linked list. Stores an instance of ValueWrapper
	 * and a pointer to the next entry.
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Wrapper which is stored.
		 */
		ValueWrapper wrapper;
		/**
		 * Pointer to the next entry.
		 */
		MultistackEntry next;

		/**
		 * Constructor which accepts a ValueWrapper.
		 * 
		 * @param wrapper - wrapper to be stored
		 */
		public MultistackEntry(ValueWrapper wrapper) {
			this.wrapper = wrapper;
			this.next = null;
		}

		/**
		 * 
		 * @return returns the stored ValueWrapper
		 */

		public ValueWrapper getWrapper() {
			return this.wrapper;
		}
	}
}
