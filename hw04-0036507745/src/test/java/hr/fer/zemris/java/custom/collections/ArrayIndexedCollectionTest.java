package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	@Test
	void defaultConstructorTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		assertEquals(16, col.getCapacity());
	}

	@Test
	void capacityConstructorTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(4);
		assertEquals(4, col.getCapacity());
		assertThrows(IllegalArgumentException.class, () -> {
			Collection<Integer> col2 = new ArrayIndexedCollection<>(-5);
		});
	}

	@Test
	void collectionConstructorTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(1);
		col.add(4);
		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>(col);
		assertTrue(col2.contains(1));
		assertTrue(col2.contains(4));

	}

	@Test
	void capacityAndCollectionConstructorTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(5);
		col.add(25);
		col.add(0);
		col.add(25);

		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>(col, 150);
		assertEquals(150, col2.getCapacity());
		assertFalse(col2.contains(-4));

		ArrayIndexedCollection<Integer> col3 = new ArrayIndexedCollection<>(col, 2);
		assertEquals(col.size(), col3.getCapacity());

		assertThrows(IllegalArgumentException.class, () -> {
			Collection<Integer> col4 = new ArrayIndexedCollection<>(-5);
		});

	}

	@Test
	void addTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(7);
		col.add(55);
		assertTrue(col.contains(7));
		assertTrue(col.contains(55));
		assertThrows(NullPointerException.class, () -> col.add(null));
	}

	@Test
	void addOverflowResizeTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(3);
		col.add(7);
		col.add(9);
		col.add(0);
		col.add(15);
		assertTrue(col.contains(15));
		assertTrue(col.size() == 4);
	}

	@Test
	void getTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(0);
		assertEquals(0, col.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(53));
	}

	@Test
	void clearTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(0);
		col.add(12);
		col.add(34);
		col.clear();
		assertTrue(col.isEmpty());
	}

	@Test
	void insertTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(0);
		col.add(12);
		col.add(34);
		col.insert(1986, 2);
		assertEquals(2, col.indexOf(1986));
		assertEquals(3, col.indexOf(34));
	}

	@Test
	void indexOfTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(7);
		col.add(5);
		col.add(-13);
		col.add(0);
		assertEquals(0, col.indexOf(7));
		assertEquals(1, col.indexOf(5));
		assertEquals(2, col.indexOf(-13));
		assertEquals(3, col.indexOf(0));
		assertEquals(-1, col.indexOf(null));
		assertEquals(-1, col.indexOf(73));

	}

	@Test
	void removeIndexTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(1995);
		col.add(1);
		col.add(4);
		col.add(505);
		col.remove(3);
		col.remove(0);
		assertFalse(col.contains(1995));
		assertFalse(col.contains(505));
		assertTrue(col.contains(1));
		assertTrue(col.contains(4));
	}

	@Test
	void removeObjectTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(1995);
		col.add(1);
		col.add(4);
		col.add(505);
		col.remove((Object)1995);
		col.remove((Object) 505);
		assertFalse(col.contains(1995));
		assertFalse(col.contains(505));
		assertTrue(col.contains(1));
		assertTrue(col.contains(4));
	}

	@Test
	void containsTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(0);
		col.add(12);
		col.add(34);

		assertTrue(col.contains(0));
		assertTrue(col.contains(12));
		assertTrue(col.contains(34));
		assertFalse(col.contains(1));

	}

	@Test
	void forEachTest() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(0);
		col.add(12);
		col.add(34);

		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>(20);

		class MyProcessor<Integer> implements Processor<Integer> {
			public void process(Object value) {
				col2.add((java.lang.Integer) value);
			}
		}
		col.forEach(new MyProcessor());
		assertTrue(col2.size() == 3);
	}

	@Test
	void addAllTest() {
		ArrayIndexedCollection<Integer> col= new ArrayIndexedCollection<>();
		col.add(0);
		col.add(12);
		col.add(34);

		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>();

		col2.addAll(col);
		assertTrue(col2.size() == 3);
	}

	@Test
	void toArrayTest() {
		ArrayIndexedCollection<Integer> col= new ArrayIndexedCollection<>();
		col.add(2);
		col.add(0);
		col.add(12);
		col.add(34);

		Object[] arr = col.toArray();
		assertTrue((int) arr[1] == 0);
		assertTrue((int) arr[3] == 34);

	}

}
