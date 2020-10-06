package hr.fer.zemris.java.hw07.observer2;

/**
 * 
 * Used for printing the squared value when the state changes.
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints the squared value.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getStoredNew();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
