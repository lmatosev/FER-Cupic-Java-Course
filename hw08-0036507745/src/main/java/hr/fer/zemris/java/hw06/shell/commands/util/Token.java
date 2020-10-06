package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * Class representing a token used by the NameBuilder lexer and parser.
 * 
 */
public class Token {
	/**
	 * Type of the token
	 */
	private TokenType type;
	/**
	 * Value which the token contains
	 */
	private Object value;

	/**
	 * Constructor for the token.
	 * 
	 * @param type  - type of the token
	 * @param value - value to be contained
	 */

	public Token(TokenType type, Object value) {
		if (type == null) {
			throw new IllegalArgumentException("Token type cannot be null.");
		}
		this.type = type;
		this.value = value;
	}

	/**
	 * 
	 * @return Object- Object contained by the token.
	 */

	public Object getValue() {
		return this.value;
	}

	/**
	 * 
	 * @return TokenType - type of the current token
	 */

	public TokenType getTokenType() {
		return this.type;
	}

}
