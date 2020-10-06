package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	void addObjectTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add(8);
		col.add("Mjau");
		col.add(17);
		assertTrue(col.contains("Mjau"));
		assertEquals(8, col.get(1));
		assertEquals(17, col.get(3));

	}

	@Test
	void getTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(15);
		col.add(3);
		col.add("Vau");
		assertEquals("Vau", col.get(2));
		assertEquals(3, col.get(1));
		assertEquals(15, col.get(0));

	}

	@Test
	void clearTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add(6);
		col.add("Sedam");
		assertTrue(col.size() == 3);
		col.clear();
		assertTrue(col.isEmpty());
	}

	@Test
	void insertTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(2);
		col.add(3);
		col.add("Sedam");
		col.add(25);
		col.add(-53.4);
		col.add("Dvadeset pet");
		col.insert(77, 3);
		assertEquals("Sedam", col.get(2));
		assertTrue(col.contains(77));
		assertEquals(77, col.get(3));
		assertEquals(25, col.get(4));
		assertThrows(NullPointerException.class, () -> col.insert(null, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 14));
	}

	@Test
	void indexOfTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(2);
		col.add(3);
		col.add("Sedam");
		col.add(25);
		col.add(-53.4);
		assertEquals(-1, col.indexOf(1));
		assertEquals(4, col.indexOf(-53.4));
		assertEquals(2, col.indexOf("Sedam"));

	}

	@Test
	void defaultConstructorTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertTrue(col.isEmpty());

	}

	@Test
	void constructorWithCollectionTest() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(5);
		col1.add(74);
		col1.add(0.05);
		col1.add("klmn");

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
		assertTrue(col2.contains(5));
		assertTrue(col2.contains(74));
		assertTrue(col2.contains(0.05));
		assertTrue(col2.contains("klmn"));
		assertEquals(4, col2.size());
		assertEquals(2, col2.indexOf(0.05));
		assertEquals(0, col2.indexOf(5));

		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));

	}

	@Test
	void removeObjectTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(153);
		col.add(25352);
		assertTrue(col.contains(153));
		assertTrue(col.contains(25352));
		col.remove((Object) 25352);
		assertTrue(col.contains(153));
		assertFalse(col.contains(25352));
	}
	
	@Test
	void removeIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(153);
		col.add(25352);
		assertTrue(col.contains(153));
		assertTrue(col.contains(25352));
		col.remove(1);
		assertTrue(col.contains(153));
		assertFalse(col.contains(25352));
	}

	@Test
	void forEachTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();

		class MyProcessor extends Processor {
			public void process(Object value) {
				col2.add(value);
			}
		}
		col.forEach(new MyProcessor());
		assertTrue(col2.size() == 4);
	}

	@Test
	void addAllTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();

		col2.addAll(col);
		assertTrue(col2.size() == 4);
	}

	@Test
	void toArrayTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);

		Object[] arr = col.toArray();
		assertTrue((int) arr[1] == 0);
		assertTrue((int) arr[3] == 34);

	}

}
