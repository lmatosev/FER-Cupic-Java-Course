package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * All types for the NameBuilderLexer's token.
 * 
 *
 */
public enum TokenType {
	/**
	 * Type representing the end of the group.
	 */
	GROUP_END,
	/**
	 * Type representing a start of the group.
	 */
	GROUP_START,
	/**
	 * Type representing a number string.
	 */
	NUMBER_STRING,
	/**
	 * Type representing the end of file.
	 */
	EOF,
	/**
	 * Type representing a string.
	 */
	STRING,

}
