package hr.fer.zemris.java.custom.scripting.elems;

public class ElementOperator extends Element {
	private String symbol;

	/**
	 * A basic constructor for this class
	 * 
	 * @param symbol - string to be stored
	 */

	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the string representation of the stored value
	 * 
	 * @return String
	 */

	@Override
	public String asText() {
		return this.symbol;
	}

	/**
	 * 
	 * @return String - symbol stored in this element
	 */

	public String getSymbol() {
		return this.symbol;
	}

}
