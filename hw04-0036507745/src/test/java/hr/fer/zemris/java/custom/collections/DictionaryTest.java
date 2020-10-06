package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void isEmptyTest() {
		Dictionary<Integer,Integer> dict = new Dictionary<>();
		
		assertTrue(dict.isEmpty());
		dict.put(3, 4);
		assertFalse(dict.isEmpty());
	}
	
	@Test
	void sizeTest() {
		Dictionary<Integer,Integer> dict = new Dictionary<>();

		assertTrue(dict.size()==0);
		dict.put(1, 1);
		dict.put(2, 2);
		assertTrue(dict.size()==2);
		dict.clear();
		assertTrue(dict.size()==0);
	}
	
	@Test
	void clearTest() {
		Dictionary<Integer,Integer> dict = new Dictionary<>();
		
		dict.put(5, 4);
		dict.put(2, 4);
		dict.put(2, 1);
		
		dict.clear();
		assertTrue(dict.size()==0);
	}
	
	@Test
	void putTest() {
		Dictionary<Integer,String> dict = new Dictionary<>();

		dict.put(12, "Sedam");
		dict.put(4, "Ivica");
		dict.put(6, "Štefica");
		
		assertEquals("Štefica", dict.get(6));
		assertEquals("Sedam", dict.get(12));
		
		dict.put(12, "Pet");
		
		assertEquals("Pet", dict.get(12));
	}
	
	@Test
	void getTest() {
		Dictionary<Integer,String> dict = new Dictionary<>();
		
		dict.put(1, "Java");
		dict.put(2, "Sedam");
		
		assertEquals("Java", dict.get(1));
	}

}
