package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {


	@Test
	void test() {
		assertThrows(IllegalArgumentException.class,()-> Factorial.calculateFactorial("štefica"));
		assertThrows(IllegalArgumentException.class,()-> Factorial.calculateFactorial("45"));
		assertThrows(IllegalArgumentException.class,()-> Factorial.calculateFactorial("1"));
		long factorial = Factorial.calculateFactorial("4");
		assertEquals(24, factorial);
	}

}
