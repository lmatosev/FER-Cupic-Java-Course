package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.*;

/**
 * 
 * Base class for all graph nodes.
 *
 */

public class Node {
	/**
	 * Collection of nodes
	 */
	private ArrayIndexedCollection nodes;

	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child - child to be added
	 */

	public void addChildNode(Node child) {
		if (nodes == null) {
			nodes = new ArrayIndexedCollection();
		}
		nodes.add(child);
	}

	/**
	 * Returns a number of (direct) children.
	 * 
	 * @return int - number of children
	 */

	public int numberOfChildren() {
		if (nodes == null) {
			return 0;
		}
		return nodes.size();
	}

	/**
	 * Returns selected child or throws an exception if the index is invalid.
	 * 
	 * @param index - index of the child node to be returned
	 * @return Node
	 */

	public Node getChild(int index) {
		return (Node) nodes.get(index);
	}

}
