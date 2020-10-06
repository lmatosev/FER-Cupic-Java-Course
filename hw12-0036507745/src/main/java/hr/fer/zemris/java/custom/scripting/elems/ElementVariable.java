package hr.fer.zemris.java.custom.scripting.elems;

public class ElementVariable extends Element {

	private String name;

	/**
	 * A basic constructor for this class
	 * 
	 * @param name - string value to be stored
	 */

	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return String - string value stored in this element
	 */

	public String getName() {
		return name;
	}

	/**
	 * Returns the string representation of the stored value
	 * 
	 * @return String
	 */

	@Override
	public String asText() {
		return this.name;
	}

}
