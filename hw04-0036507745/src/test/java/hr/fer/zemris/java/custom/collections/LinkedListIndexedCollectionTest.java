package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	void addObjectTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(5);
		col.add(8);
		col.add(17);
		assertEquals(8, col.get(1));
		assertEquals(17, col.get(2));

	}

	@Test
	void getTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(15);
		col.add(3);
		assertEquals(3, col.get(1));
		assertEquals(15, col.get(0));

	}

	@Test
	void clearTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(5);
		col.add(6);
		assertTrue(col.size() == 2);
		col.clear();
		assertTrue(col.isEmpty());
	}

	@Test
	void insertTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.add(3);
		col.add(25);
		col.add(-53);
		col.insert(77, 3);
		assertTrue(col.contains(77));
		assertEquals(77, col.get(3));
		assertEquals(25, col.get(2));
		assertThrows(NullPointerException.class, () -> col.insert(null, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 14));
	}

	@Test
	void indexOfTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.add(3);
		col.add(25);
		col.add(-53);
		assertEquals(-1, col.indexOf(1));
		assertEquals(3, col.indexOf(-53));

	}

	@Test
	void defaultConstructorTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		assertTrue(col.isEmpty());

	}

	@Test
	void constructorWithCollectionTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(5);
		col.add(74);
		col.add(0);

		LinkedListIndexedCollection<Integer> col2 = new LinkedListIndexedCollection<Integer>(col);
		assertTrue(col2.contains(5));
		assertTrue(col2.contains(74));
		assertTrue(col2.contains(0));
		assertEquals(3, col2.size());
		assertEquals(2, col2.indexOf(0));
		assertEquals(0, col2.indexOf(5));

		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));

	}

	@Test
	void removeObjectTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
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
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
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
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(0);
		col.add(12);
		col.add(34);

		LinkedListIndexedCollection<Integer> col2 = new LinkedListIndexedCollection<Integer>();

		class MyProcessor<Integer> implements Processor<Integer> {
			public void process(Integer value) {
				col2.add((java.lang.Integer) value);
			}
		}
		col.forEach(new MyProcessor());
		assertTrue(col2.size() == 3);
	}

	@Test
	void addAllTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(0);
		col.add(12);
		col.add(34);

		LinkedListIndexedCollection<Integer> col2 = new LinkedListIndexedCollection<Integer>();

		col2.addAll(col);
		assertTrue(col2.size() == 3);
	}

	@Test
	void toArrayTest() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(0);
		col.add(12);
		col.add(34);

		Object[] arr = col.toArray();
		assertTrue((int) arr[0] == 0);
		assertTrue((int) arr[2] == 34);

	}

}
