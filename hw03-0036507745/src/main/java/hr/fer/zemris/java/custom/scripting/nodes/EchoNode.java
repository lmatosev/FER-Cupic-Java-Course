package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * 
 * A node representing a command which generates some textual output
 * dynamically.
 *
 */

public class EchoNode extends Node {
	/**
	 * Elements contained in this node.
	 */
	private Element[] elements;

	/**
	 * Constructor for the node which accepts a list of elements.
	 * 
	 * @param elements - a list of elements
	 */

	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * 
	 * @return Element[] - list of this node's elements
	 */

	public Element[] getElements() {
		return elements;
	}

	/**
	 * Returns a string representation of this node.
	 */

	@Override
	public String toString() {
		String output = "{$= ";
		for (int i = 0; i < elements.length; i++) {
			output += elements[i].asText() + " ";
		}

		output += "$}";
		return output;
	}
}
