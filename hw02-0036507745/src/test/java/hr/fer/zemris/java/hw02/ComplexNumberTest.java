package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	void testParse() {
		String s1 = "351";
		String s2 = "-3.17";
		String s3 = "351i";
		String s4 = "-3.17i";
		String s5 = "-2.71-3.15i";
		String s6 = "31+24i";
		String s7 = "i";
		String s8 = "1";

		ComplexNumber c1 = ComplexNumber.parse(s1);
		ComplexNumber c2 = ComplexNumber.parse(s2);
		ComplexNumber c3 = ComplexNumber.parse(s3);
		ComplexNumber c4 = ComplexNumber.parse(s4);
		ComplexNumber c5 = ComplexNumber.parse(s5);
		ComplexNumber c6 = ComplexNumber.parse(s6);
		ComplexNumber c7 = ComplexNumber.parse(s7);
		ComplexNumber c8 = ComplexNumber.parse(s8);

		assertEquals(351, c1.getReal());
		assertEquals(-3.17, c2.getReal());
		assertEquals(351, c3.getImaginary());
		assertEquals(-3.17, c4.getImaginary());
		assertEquals(-2.71, c5.getReal());
		assertEquals(-3.15, c5.getImaginary());
		assertEquals(31, c6.getReal());
		assertEquals(24, c6.getImaginary());
		assertEquals(1, c7.getImaginary());
		assertEquals(1, c8.getReal());
	}

	@Test
	void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-1, 1);
		ComplexNumber c3 = new ComplexNumber(-1, -1);
		ComplexNumber c4 = new ComplexNumber(1, -1);
		ComplexNumber c5 = new ComplexNumber(1, 0);
		ComplexNumber c6 = new ComplexNumber(0, 1);
		ComplexNumber c7 = new ComplexNumber(-1, 0);
		ComplexNumber c8 = new ComplexNumber(0, -1);

		assertEquals(Math.PI / 4, c1.getAngle());
		assertEquals(3 * Math.PI / 4, c2.getAngle());
		assertEquals(5 * Math.PI / 4, c3.getAngle());
		assertEquals(7 * Math.PI / 4, c4.getAngle());
		assertEquals(0, c5.getAngle());
		assertEquals(Math.PI / 2, c6.getAngle());
		assertEquals(Math.PI, c7.getAngle());
		assertEquals(3 * Math.PI / 2, c8.getAngle());
	}

	@Test
	void testComplexNumberConstructor() {
		ComplexNumber comp1 = new ComplexNumber(5.0, 4.9);
		assertEquals(5.0, comp1.getReal());
		assertEquals(4.9, comp1.getImaginary());

	}

	@Test
	void testFromReal() {
		ComplexNumber comp1 = ComplexNumber.fromReal(5.0);
		assertEquals(5.0, comp1.getReal());
		assertEquals(0, comp1.getImaginary());
	}

	@Test
	void testFromImaginary() {
		ComplexNumber comp1 = ComplexNumber.fromImaginary(5.0);
		assertEquals(0, comp1.getReal());
		assertEquals(5.0, comp1.getImaginary());
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber comp1 = ComplexNumber.fromMagnitudeAndAngle(1.5, 3.0);
		assertTrue(Math.abs(-1.4849887 - comp1.getReal()) < 0.00000005);
		assertTrue(Math.abs(0.2116800 - comp1.getImaginary()) < 0.00000005);
	}

	@Test
	void testGetReal() {
		ComplexNumber comp1 = new ComplexNumber(1.5, 3.0);
		assertEquals(1.5,comp1.getReal());
	}

	@Test
	void testGetImaginary() {
		ComplexNumber comp1 = new ComplexNumber(1.5, 3.0);
		assertEquals(3.0,comp1.getImaginary());
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber comp1 = new ComplexNumber(1.5, 3.0);
		assertTrue(Math.abs(3.35410196 - comp1.getMagnitude()) < 0.00000001);

	}

	@Test
	void testAdd() {
		ComplexNumber comp1 = new ComplexNumber(2.0, -1.0);
		ComplexNumber comp2 = comp1.add(new ComplexNumber(1.3, 4.2));
		assertEquals(3.3, comp2.getReal());
		assertEquals(3.2, comp2.getImaginary());
	}

	@Test
	void testSub() {
		ComplexNumber comp1 = new ComplexNumber(5.0, -1.0);
		ComplexNumber comp2 = comp1.sub(new ComplexNumber(4.5, -4));
		assertEquals(0.5, comp2.getReal());
		assertEquals(3.0, comp2.getImaginary());
	}

	@Test
	void testMul() {
		ComplexNumber comp1 = new ComplexNumber(5.0, -1.0);
		ComplexNumber comp2 = comp1.mul(new ComplexNumber(4.5, -4));
		assertEquals(18.5, comp2.getReal());
		assertEquals(-24.5, comp2.getImaginary());
	}

	@Test
	void testDiv() {
		ComplexNumber comp1 = new ComplexNumber(5.0, -1.0);
		ComplexNumber comp2 = comp1.div(new ComplexNumber(4.5, -4));
		assertTrue(Math.abs(0.7310345 - comp2.getReal()) < 0.00000005);
		assertTrue(Math.abs(0.4275862 - comp2.getImaginary()) < 0.00000005);

	}

	@Test
	void testPower() {
		ComplexNumber comp1 = new ComplexNumber(5.24, -3.41);
		ComplexNumber comp2 = comp1.power(5);
		assertTrue(Math.abs(-9237.1445205 - comp2.getReal()) < 0.00000005);
		assertTrue(Math.abs(-2427.9694887 - comp2.getImaginary()) < 0.00000005);

		assertThrows(IllegalArgumentException.class, () -> comp1.power(-5));
	}

	@Test
	void testRoot() {
		ComplexNumber comp1 = new ComplexNumber(-0.34, 19.25);
		ComplexNumber comp2 = comp1.root(5)[3];
		assertTrue(Math.abs(-1.0568155 - comp2.getReal()) < 0.00000005);
		assertTrue(Math.abs(-1.4654389 - comp2.getImaginary()) < 0.00000005);

		assertThrows(IllegalArgumentException.class, () -> comp1.root(0));
	}

	@Test
	void testEqualsObject() {
		ComplexNumber comp1 = new ComplexNumber(2.0, -4.7);
		ComplexNumber comp2 = new ComplexNumber(2.0, -4.7);
		ComplexNumber comp3 = new ComplexNumber(0, -4.6);

		assertTrue(comp1.equals(comp2));
		assertFalse(comp1.equals(comp3));
		assertFalse(comp2.equals(comp3));
	}

}
