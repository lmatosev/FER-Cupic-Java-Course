package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Base class for all graph nodes.
 *
 */

public abstract class Node {
	/**
	 * Collection of nodes
	 */
	private List<Node> nodes;

	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child - child to be added
	 */

	public void addChildNode(Node child) {
		if (nodes == null) {
			nodes = new ArrayList<>();
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
		return nodes.get(index);
	}
	
	
	public abstract void accept(INodeVisitor visitor);

}
