package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Used to test if an object satisfies a condition.
 *
 */

public interface Tester {
	/**
	 * Tests the object for the specified condition.
	 * 
	 * @param obj - object to be tested
	 * @return boolean - true if the object satisfies the condition, and false if it
	 *         does not
	 */
	boolean test(Object obj);
}
