package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	void nextTest() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		int prim = model.next();
		assertEquals(5, prim);
	}

	@Test
	void emptyModelSizeTest() {
		PrimListModel model = new PrimListModel();

		assertEquals(1, model.getSize());
	}
		
	@Test
	void sizeAddMultipleTest() {
		PrimListModel model = new PrimListModel();

		assertEquals(1, model.getSize());
		
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		
		assertEquals(8, model.getSize());
	}
	
	
	@Test
	void getElementTest() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		assertEquals(1, model.getElementAt(0));
		assertEquals(5, model.getElementAt(3));
		assertEquals(13, model.getElementAt(6));
		assertEquals(23, model.getElementAt(9));

	}

}
