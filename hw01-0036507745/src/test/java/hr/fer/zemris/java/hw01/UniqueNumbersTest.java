package hr.fer.zemris.java.hw01;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UniqueNumbersTest {

	@Test
	void sizeTest() {
		TreeNode glava = null;
		assertEquals(0, UniqueNumbers.treeSize(glava));
		glava = UniqueNumbers.addNode(glava, 20);
		glava = UniqueNumbers.addNode(glava, 4);
		glava = UniqueNumbers.addNode(glava, 52);
		glava = UniqueNumbers.addNode(glava, 17);
		glava = UniqueNumbers.addNode(glava, 4);
		glava = UniqueNumbers.addNode(glava, 4);
		glava = UniqueNumbers.addNode(glava, 6);
		glava = UniqueNumbers.addNode(glava, 5);
		assertEquals(6,UniqueNumbers.treeSize(glava));
		
		
	
	}
	
	@Test
	void valueTest() {
		TreeNode glava = null;
		assertEquals(0, UniqueNumbers.treeSize(glava));
		glava = UniqueNumbers.addNode(glava, 20);
		glava = UniqueNumbers.addNode(glava, 4);
		glava = UniqueNumbers.addNode(glava, 52);
		glava = UniqueNumbers.addNode(glava, 17);
		glava = UniqueNumbers.addNode(glava, 4);
		assertEquals(4,UniqueNumbers.treeSize(glava));
		assertEquals(20,glava.value);
		assertEquals(4,glava.left.value);
		assertEquals(52,glava.right.value);
		assertEquals(17,glava.left.right.value);
	}
	
	@Test
	void containsTest() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 17);
		glava = UniqueNumbers.addNode(glava, 15);
		glava = UniqueNumbers.addNode(glava, 34);
		glava = UniqueNumbers.addNode(glava, 2);
		assertEquals(true,UniqueNumbers.containsValue(glava, 2));
		assertEquals(true,UniqueNumbers.containsValue(glava, 17));
		assertEquals(false,UniqueNumbers.containsValue(glava, 0));

	}

}
