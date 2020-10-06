package hr.fer.zemris.java.custom.scripting.parser;

/**
 * 
 * Exception that the SmartScriptParser throws.
 * 
 */

public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructor which accepts a string
	 */

	public SmartScriptParserException(String message) {
		super(message);
	}

}
