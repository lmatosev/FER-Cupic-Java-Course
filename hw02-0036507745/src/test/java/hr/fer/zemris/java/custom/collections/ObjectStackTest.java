package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectStackTest {

	@Test
	void testSize() {
		ObjectStack stack = new ObjectStack();
		assertEquals(0,stack.size());
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3,stack.size());
	}

	@Test
	void testPush() {
		ObjectStack stack = new ObjectStack();
		stack.push(2);
		assertEquals(2,stack.peek());
	}

	@Test
	void testPop() {
		ObjectStack stack = new ObjectStack();
		assertThrows(EmptyStackException.class,()->stack.pop());
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		stack.push(6);
		stack.push(7);
		stack.push(8);
		stack.push(9);
		stack.push(10);
		stack.push(11);
		stack.push(12);
		stack.push(13);
		stack.push(14);
		
		assertEquals(14,stack.pop());
	}

	@Test
	void testPeek() {
		ObjectStack stack = new ObjectStack();
		assertThrows(EmptyStackException.class,()->stack.peek());
	}

}
