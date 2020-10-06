package hr.fer.zemris.java.hw03.prob1;

/**
 * 
 * Exception that the lexer will throw.
 * 
 */

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public LexerException() {
		super();
	}

	/**
	 * Constructor which accepts a string.
	 * 
	 * @param message
	 */

	public LexerException(String message) {
		super(message);
	}
}
