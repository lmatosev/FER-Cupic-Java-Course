package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * An exception which the NameBuilderLexer throws when there is an error while
 * lexing.
 *
 */

public class NameBuilderLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public NameBuilderLexerException() {
		super();
	}

	/**
	 * Constructor which accepts a string
	 */

	public NameBuilderLexerException(String message) {
		super(message);
	}

}