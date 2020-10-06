package hr.fer.zemris.java.custom.scripting.elems;

/**
 * 
 * Inherits Element and is used to store an integer value.
 *
 */

public class ElementConstantInteger extends Element{
	private int value;
	
	/**
	 * 
	 * @return value - integer value stored in this element
	 */
	
	public int getValue() {
		return value;
	}
	
	/**
	 * A basic constructor for this class
	 * 
	 * @param value - integer value to be stored
	 */

	public ElementConstantInteger(int value) {
		this.value=value;
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
