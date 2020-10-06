package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * An implementation of Tester. Returns true if the integer object is even, and
 * false else.
 *
 */

public class EvenIntegerTester implements Tester {

	/**
	 * Tests the object for the specified condition.
	 * 
	 * @return - true if the object satisfies the test and false if it does not
	 */

	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;

	}
}
