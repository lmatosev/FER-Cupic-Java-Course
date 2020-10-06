package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * 
 * Type of the token.
 *
 */

public enum TokenType {
	/**
	 * Type indicating the end of input.
	 */
	EOF,
	/**
	 * Type indicating text
	 */
	TEXT,
	/**
	 * Type indicating a tag
	 */
	TAG,
	/**
	 * Type indicating the name of a tag
	 */
	TAGNAME,
	/**
	 * Type indicating a variable
	 */
	VARIABLE,
	/**
	 * Type indicating a function
	 */
	FUNCTION,
	/**
	 * Type indicating an operator
	 */
	OPERATOR,
	/**
	 * Type indicating a string
	 */
	STRING,
	/**
	 * Type indicating a double
	 */
	DOUBLE,
	/**
	 * Type indicating an integer
	 */
	INTEGER

}
