package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {
	private String name;

	/**
	 * A basic constructor for this class
	 * 
	 * @param name - name to be stored
	 */

	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return String - name that is stored in this element
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
