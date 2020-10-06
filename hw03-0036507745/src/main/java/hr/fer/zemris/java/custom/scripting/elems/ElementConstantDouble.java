package hr.fer.zemris.java.custom.scripting.elems;

/**
 * 
 * Inherits Element and is used to store a double value.
 *
 */

public class ElementConstantDouble extends Element {
	private double value;

	/**
	 * 
	 * @return value - double value stored in this element
	 */

	public double getValue() {
		return value;
	}

	/**
	 * A basic constructor for this class
	 * 
	 * @param value - value to be stored
	 */

	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Returns the string representation of the stored value
	 * 
	 * @return String
	 */

	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
