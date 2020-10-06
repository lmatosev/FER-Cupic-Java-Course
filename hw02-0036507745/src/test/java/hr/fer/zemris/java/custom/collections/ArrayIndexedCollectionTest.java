package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	@Test
	void defaultConstructorTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(16, col.getCapacity());
	}

	@Test
	void capacityConstructorTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);
		assertEquals(4,col.getCapacity());
		assertThrows(IllegalArgumentException.class,()->{
			Collection col2 = new ArrayIndexedCollection(-5);
		});
	}

	@Test
	void collectionConstructorTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add("Sedam");
		col.add(4);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col);
		assertTrue(col2.contains(1));
		assertTrue(col2.contains("Sedam"));
		assertTrue(col2.contains(4));

	}

	@Test
	void capacityAndCollectionConstructorTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add("kasfm");
		col.add(25);
		col.add(0);
		col.add(-5.9);
		col.add(25);
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col, 150);
		assertEquals(150,col2.getCapacity());
		assertTrue(col2.contains(-5.9));
		assertTrue(col2.contains("kasfm"));
		assertFalse(col2.contains(-4));
		
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(col, 2);
		assertEquals(col.size(),col3.getCapacity());
		
		
		assertThrows(IllegalArgumentException.class,()->{
			Collection col4 = new ArrayIndexedCollection(-5);
		});
		
	}

	@Test
	void addTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Zagreb");
		col.add(7);
		assertTrue(col.contains("Zagreb"));
		assertTrue(col.contains(7));
		assertFalse(col.contains(55));
		assertThrows(NullPointerException.class, () -> col.add(null));
	}

	@Test
	void addOverflowResizeTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("Zagreb");
		col.add(7);
		col.add("Split");
		col.add(9);
		col.add(0);
		col.add(15);
		assertTrue(col.contains(15));
		assertTrue(col.size() == 6);
	}

	@Test
	void getTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Split");
		col.add(0);
		assertEquals("Split", col.get(0));
		assertEquals(0, col.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(53));
	}

	@Test
	void clearTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		col.clear();
		assertTrue(col.isEmpty());
	}

	@Test
	void insertTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		col.insert("Dinamo", 2);
		assertEquals(2, col.indexOf("Dinamo"));
		assertEquals(4, col.indexOf(34));
	}

	@Test
	void indexOfTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Rijeka");
		col.add(5);
		col.add(-13);
		col.add(0);
		assertEquals(0, col.indexOf("Rijeka"));
		assertEquals(1, col.indexOf(5));
		assertEquals(2, col.indexOf(-13));
		assertEquals(3, col.indexOf(0));
		assertEquals(-1, col.indexOf(null));
		assertEquals(-1, col.indexOf(73));

	}

	@Test
	void removeIndexTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Knin");
		col.add(1);
		col.add(4);
		col.add(505);
		col.remove(3);
		col.remove(0);
		assertFalse(col.contains("Knin"));
		assertFalse(col.contains(505));
		assertTrue(col.contains(1));
		assertTrue(col.contains(4));
	}

	@Test
	void removeObjectTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Knin");
		col.add(1);
		col.add(4);
		col.add(505);
		col.remove("Knin");
		col.remove((Object) 505);
		assertFalse(col.contains("Knin"));
		assertFalse(col.contains(505));
		assertTrue(col.contains(1));
		assertTrue(col.contains(4));
	}
	
	@Test
	void containsTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		
		assertTrue(col.contains("Osijek"));
		assertTrue(col.contains(0));
		assertTrue(col.contains(12));
		assertTrue(col.contains(34));
		assertFalse(col.contains(1));
		
	}
	
	@Test
	void forEachTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(20);
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(20);

		
		class MyProcessor extends Processor {
			public void process(Object value) {
				col2.add(value);
			}
		}
		col.forEach(new MyProcessor());
		assertTrue(col2.size()==4);
	}
	
	@Test 
	void addAllTest(){
		ArrayIndexedCollection col = new ArrayIndexedCollection(20);
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		
		col2.addAll(col);
		assertTrue(col2.size()==4);
	}
	
	@Test
	void toArrayTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(20);
		col.add("Osijek");
		col.add(0);
		col.add(12);
		col.add(34);
		
		Object[] arr = col.toArray();
		assertTrue((int)arr[1]==0);
		assertTrue((int)arr[3]==34);

	}
	
	

}
