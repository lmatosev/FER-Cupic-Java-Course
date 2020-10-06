package hr.fer.zemris.java.hw07.observer2;

/**
 * 
 * Counts and prints the number of changes.
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counter of changes
	 */
	int counter;

	/**
	 * Default constructor which sets the counter to 0.
	 */
	public ChangeCounter() {
		this.counter = 0;
	}

	/**
	 * Increments the counter and prints the current count.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		this.counter++;
		System.out.println("Number of value changes since tracking: " + this.counter);
	}

}
