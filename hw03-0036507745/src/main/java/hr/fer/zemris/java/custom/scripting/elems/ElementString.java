package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {
	private String value;

	/**
	 * A basic constructor for this class
	 * 
	 * @param value - string value to be stored
	 */

	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the string representation of the stored value
	 * 
	 * @return String
	 */

	@Override
	public String asText() {
		String output = "\"";
		for (int i = 1; i < this.value.length()-1; i++) {
			if (this.value.charAt(i) == '"') {
				output += "\\\"";
			} else if(this.value.charAt(i)=='\\'){
				output+="\\\\";
			}else {
				output += this.value.charAt(i);
			}
		}
		output+="\"";
		return output;
	}

	/**
	 * 
	 * @return value - String value to be returned
	 */

	public String getValue() {
		return value;
	}

}
