package hr.fer.zemris.java.hw07.observer1;

/**
 * 
 * Observer for the IntegerStorage class.
 * 
 */
public interface IntegerStorageObserver {
	/**
	 * Does something when the value was changed.
	 */
	public void valueChanged(IntegerStorage istorage);
}
