package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * 
 * A node representing a piece of textual data.
 *
 */

public class TextNode extends Node {
	/**
	 * Text contained in this node.
	 */
	private String text;

	/**
	 * Constructor for this node which accepts a string.
	 * 
	 * @param text
	 */

	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * 
	 * @return String - text contained in this node
	 */

	public String getText() {
		return text;
	}

	/**
	 * Returns a string representation of this node.
	 */

	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < this.text.length(); i++) {
			if (this.text.charAt(i) == '\\') {
				output += "\\";
			} else if (this.text.charAt(i) == '{' && i < text.length() - 1) {
				if (this.text.charAt(i + 1) == '$') {
					output += "\\{";
				}

			} else {
				output += this.text.charAt(i);
			}
		}
		return output;
	}

}
