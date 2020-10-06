package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
