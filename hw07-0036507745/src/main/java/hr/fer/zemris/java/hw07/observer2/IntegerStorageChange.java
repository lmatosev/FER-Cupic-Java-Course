package hr.fer.zemris.java.hw07.observer2;

/**
 * 
 * Stores information on the old and new integer values.
 *
 */

public class IntegerStorageChange {

	/**
	 * IntegerStorage stored
	 */
	private IntegerStorage storage;
	/**
	 * The value before
	 */
	private int storedBefore;
	/**
	 * The value after
	 */
	private int storedNew;

	/**
	 * Sets all the parameters to the provided values.
	 * 
	 * @param storage      - storage to be set
	 * @param storedBefore - old integer value to be set
	 * @param storedNew    - new integer value to be set
	 */
	public IntegerStorageChange(IntegerStorage storage, int storedBefore, int storedNew) {
		this.storage = storage;
		this.storedBefore = storedBefore;
		this.storedNew = storedNew;
	}

	/**
	 * 
	 * @return the IntegerStorage stored in this class
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * 
	 * @return the integer that was stored before the change
	 */
	public int getStoredBefore() {
		return storedBefore;
	}

	/**
	 * 
	 * @return the integer that was stored after the change
	 */
	public int getStoredNew() {
		return storedNew;
	}

}
