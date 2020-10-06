package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.GenericArrayType;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testConstructor() {
		Vector2D vector = new Vector2D(5, 4);
		
		assertEquals(5,vector.getX());
		assertEquals(4,vector.getY());
	}

	@Test
	void testGetX() {
		Vector2D vector = new Vector2D(1715, 1915);
		
		assertEquals(1715, vector.getX());
	}

	@Test
	void testGetY() {
		Vector2D vector = new Vector2D(1715, 1915);
		
		assertEquals(1915, vector.getY());	}

	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(3, 2);
		Vector2D vector2 = new Vector2D(4, 1);

		vector.translate(vector2);
		assertEquals(7, vector.getX());
		assertEquals(3, vector.getY());
	}

	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(-5, 4.5);
		Vector2D vector2 = new Vector2D(23, -2.5);

		Vector2D vectorResult = vector.translated(vector2);

		assertEquals(18, vectorResult.getX());
		assertEquals(2, vectorResult.getY());
	}

	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(3, 2);

		vector.rotate(Math.PI / 3);

		assertTrue(3. / 2 - Math.sqrt(3) - vector.getX() < 1E-7);
		assertTrue((3. / 2) * Math.sqrt(3) + 1 - vector.getY() < 1E-7);
	}

	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(5, 4);

		Vector2D vectorResult = vector.rotated(Math.PI);
		assertTrue(-5 - vectorResult.getX() < 1E-9);
		assertTrue(-4 - vectorResult.getY() < 1E-9);

	}

	@Test
	void testScale() {
		Vector2D vector = new Vector2D(20, -5);
		vector.scale(-4);

		assertEquals(-80, vector.getX());
		assertEquals(20, vector.getY());
	}

	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(1, 17);
		Vector2D vectorResult = vector.scaled(3);

		assertEquals(3, vectorResult.getX());
		assertEquals(51, vectorResult.getY());
	}

	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(1, 17);
		Vector2D vectorResult = vector.copy();
		
		assertEquals(vector.getX(),vectorResult.getX());
		assertEquals(vector.getY(),vectorResult.getY());
	}

}
