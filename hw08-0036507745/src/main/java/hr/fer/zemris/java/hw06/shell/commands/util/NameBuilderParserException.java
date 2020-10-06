package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * An exception which the NameBuilderParser throws when there is an error while
 * parsing.
 *
 */

public class NameBuilderParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public NameBuilderParserException() {
		super();
	}

	/**
	 * Constructor which accepts a string
	 */

	public NameBuilderParserException(String message) {
		super(message);
	}

}