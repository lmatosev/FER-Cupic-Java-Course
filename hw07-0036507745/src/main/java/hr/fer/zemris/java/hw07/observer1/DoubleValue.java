package hr.fer.zemris.java.hw07.observer1;

/**
 * 
 * Prints the value stored multiplied by 2, but only the first num times.
 * 
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Number of times the printing should be done
	 */
	int num;

	/**
	 * Initializes the number to the provided value.
	 * 
	 * @param num - number to be set
	 */
	public DoubleValue(int num) {
		this.num = num;
	}

	/**
	 * Decrements the number, and if the number counter is non-negative, prints the
	 * value stored in IntegerStorage multiplied by 2 /*
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		this.num--;
		if (this.num <= 0) {
			istorage.removeObserver(this);
		}
		System.out.println("Double value: " + istorage.getValue() * 2);

	}

}
