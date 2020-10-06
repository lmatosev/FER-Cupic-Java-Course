package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	void testNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.add(v2.getValue());
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	void testStringDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));

		v1.add(v2.getValue());
		assertEquals(13.0, v1.getValue());
		assertEquals(1, v2.getValue());
	}

	@Test
	void testStringIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));

		v1.add(v2.getValue());
		assertEquals(13, v1.getValue());
		assertEquals(1, v2.getValue());
	}

	@Test
	void testInvalidArgument() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue()));
		assertEquals(1, v2.getValue());
	}

	@Test
	void testCompareDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("24.5");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(72));

		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals(72, v2.getValue());
	}

	@Test
	void testCompareNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals(null, v2.getValue());
	}
}
