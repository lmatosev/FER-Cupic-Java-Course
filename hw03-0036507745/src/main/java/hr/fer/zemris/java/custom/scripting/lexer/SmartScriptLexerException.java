package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * 
 * Exception that the lexer will throw.
 * 
 */

public class SmartScriptLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructor which accepts a string
	 */

	public SmartScriptLexerException(String message) {
		super(message);
	}
}
