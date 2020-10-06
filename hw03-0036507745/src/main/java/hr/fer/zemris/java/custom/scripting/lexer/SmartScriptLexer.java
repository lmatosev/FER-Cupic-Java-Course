package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * 
 * This class represents a simple lexer used for the SmartScriptParser. It has
 * two states - the basic and the tag state. If the input is invalid
 * SmartScriptLexerException will be thrown.
 * 
 * @author Lovro Matošević
 *
 */

public class SmartScriptLexer {
	/**
	 * Input string saved as a char array.
	 */
	private char[] data;
	/**
	 * Instance of the class token.
	 */
	private Token token;
	/**
	 * Current index of the char array.
	 */
	private int currentIndex;
	/**
	 * The state which the lexer is in.
	 */
	private LexerState state;
	/**
	 * Variable used for checking if the tag name has already been read if in the
	 * tag state.
	 */
	private boolean isTagNameSet;

	/**
	 * The basic constructor for this class which accepts a string that will be
	 * processed.
	 * 
	 * @param input - input text that will be processed.
	 */

	public SmartScriptLexer(String input) {
		this.data = input.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
		this.isTagNameSet = false;
	}

	/**
	 * Returns the next token.
	 * 
	 * @return Token
	 * @throws SmartScriptLexerException - is thrown if there are no more tokens and
	 *                                   the method is called or if the input is
	 *                                   invalid
	 */

	public Token nextToken() {
		if (this.token != null && this.token.getTokenType() == TokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens.");
		}

		if (currentIndex == data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		if (data.length == 0) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}


		if (this.state == LexerState.TAGS) {
			this.skipBlanks();
			if (this.isTagNameSet == false) {
				if (data[currentIndex] == '=') {
					this.token = new Token(TokenType.TAGNAME, "=");
					this.isTagNameSet = true;
					currentIndex++;
					return this.token;
				}
				String varName = extractVariable();
				this.token = new Token(TokenType.TAGNAME, varName);
				this.isTagNameSet = true;
				return this.token;
			}

			if (data[currentIndex] == '$') {
				if (currentIndex == data.length) {
					throw new SmartScriptParserException("Invalid input");
				}
				if (data[currentIndex + 1] == '}') {
					this.token = new Token(TokenType.TAG, null);
					this.isTagNameSet = false;
					this.currentIndex += 2;
					return this.token;
				}
				System.out.println(String.copyValueOf(data, currentIndex, 5));
				throw new SmartScriptParserException("Invalid input.");
			}

			if (data[currentIndex] == '@') {
				String function = extractFunction();
				this.token = new Token(TokenType.FUNCTION, function);
				return this.token;
			}

			if (data[currentIndex] == '"') {
				String string = extractString();
				this.token = new Token(TokenType.STRING, string);
				return this.token;
			}

			if (Character.isLetter(data[currentIndex])) {
				String variable = extractVariable();
				this.token = new Token(TokenType.VARIABLE, variable);
				return this.token;
			}

			if (Character.isDigit(data[currentIndex])) {
				try {
					int integerNum = extractInteger();
					this.token = new Token(TokenType.INTEGER, integerNum);
					return this.token;

				} catch (SmartScriptLexerException ex) {
					double doubleNum = extractDouble();
					this.token = new Token(TokenType.DOUBLE, doubleNum);
					return this.token;
				}
			}

			if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
				double doubleNum = extractDouble();
				this.token = new Token(TokenType.DOUBLE, doubleNum);
				return this.token;
			}

			String operator = extractOperator();
			this.token = new Token(TokenType.OPERATOR, operator);
			return this.token;

		}


		if (data[currentIndex] == '{') {
			if (currentIndex < data.length - 1) {
				if (data[currentIndex + 1] == '$') {
					this.token = new Token(TokenType.TAG, null);
					currentIndex += 2;
					return this.token;
				}
			}
		}

		String output = "";

		if (data[currentIndex] == '\\') {
			if (currentIndex == data.length - 1)
				throw new SmartScriptLexerException("Invalid input");
			if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {
				output = String.valueOf(data[currentIndex + 1]);
				currentIndex += 2;
				if (this.currentIndex >= data.length) {
					throw new SmartScriptLexerException();
				}
			} else {
				throw new SmartScriptLexerException();
			}
		} else {
			output += String.valueOf(data[currentIndex]);
			this.currentIndex++;
		}

		while (this.currentIndex < data.length) {
			if (data[currentIndex] == '\\' && currentIndex < data.length - 1) {
				if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {
					output += String.valueOf(data[currentIndex + 1]);
					currentIndex++;
				} else {
					throw new SmartScriptLexerException("Invalid input");
				}
			} else if (data[currentIndex] == '{') {
				if (currentIndex < data.length - 1) {
					if (data[currentIndex + 1] == '$') {
						this.token = new Token(TokenType.TEXT, output);
						return this.token;
					} else {
						output += String.valueOf(data[currentIndex]);
					}
				} else {
					output += "{";
				}
			}
			output += data[currentIndex];
			this.currentIndex++;
		}
		this.token = new Token(TokenType.TEXT, output);
		return this.token;

	}

	/**
	 * Extracts a double from the input text.
	 * 
	 * @return double
	 * @throws SmartScriptLexerException - if the current part of the text cannot be
	 *                                   perceived as a double
	 */

	private double extractDouble() {
		String output = "";
		boolean negative = false;
		if (data[currentIndex] == '-') {
			negative = true;
			currentIndex++;
		}

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			output += String.valueOf(data[currentIndex]);
			currentIndex++;
		}
		if (currentIndex < data.length && data[currentIndex] == '.') {
			output += ".";
			currentIndex++;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				output += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
		}

		try {
			Double returnValue = Double.parseDouble(output);
			if (negative == true) {
				return -returnValue;
			}
			return returnValue;
		} catch (NumberFormatException ex) {
			throw new SmartScriptLexerException("Invalid number format.");
		}
	}

	/**
	 * Extracts an integer from the input text.
	 * 
	 * @return int
	 * @throws SmartScriptLexerException - if the current part of the text cannot be
	 *                                   perceived as an integer
	 */

	private int extractInteger() {
		String output = "";
		int startIndex=currentIndex;
		while (Character.isDigit(data[startIndex]) && startIndex < data.length) {
			if(startIndex<data.length-2) {
				if(data[startIndex+1]=='.' && Character.isDigit(data[startIndex+2])) {
					throw new SmartScriptLexerException();
				}
			}
			output += String.valueOf(data[startIndex]);
			startIndex++;
		}

		try {
			int returnValue = Integer.parseInt(output);
			currentIndex=startIndex;
			return returnValue;
		} catch (Exception ex) {
			throw new SmartScriptLexerException("Invalid expression.");
		}
	}

	/**
	 * Extracts a function from the input text.
	 * 
	 * @return String
	 * @throws SmartScriptLexerException - if the current part of the text is an
	 *                                   invalid function name
	 */

	private String extractFunction() {
		String output = "@";
		currentIndex++;
		output += this.extractVariable();
		if (output.length() == 1) {
			throw new SmartScriptLexerException("Invalid expression");
		}
		return output;
	}

	/**
	 * Extracts an operator from the input text.
	 * 
	 * @return String
	 * @throws SmartScriptLexerException - if the current part of the text is an
	 *                                   invalid operator
	 */

	private String extractOperator() {
		char c = data[currentIndex];
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
			currentIndex++;
			return String.valueOf(c);
		} else {
			throw new SmartScriptLexerException("Invalid expression.");
		}
	}

	/**
	 * Extracts a string from the input text.
	 * 
	 * @return String
	 * @throws SmartScriptLexerException - if the current part of the text is an
	 *                                   invalid string
	 */

	private String extractString() {
		String output = "\"";
		currentIndex++;

		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				if (currentIndex < data.length - 1) {
					if (data[currentIndex + 1] == '\\') {
						output += "\\";
						currentIndex += 2;
					} else if (data[currentIndex + 1] == '\"') {
						output += "\"";
						currentIndex += 2;
					} else if (data[currentIndex + 1] == 'n') {
						output += Character.toString((char) 10);
						currentIndex += 2;
					} else if (data[currentIndex + 1] == 'r') {
						output += Character.toString((char) 13);
						currentIndex += 2;
					} else if (data[currentIndex + 1] == 't') {
						output += Character.toString((char) 9);
						currentIndex += 2;
					}
				} else {
					throw new SmartScriptLexerException("Invalid escape character usage.");
				}

			} else {
				output += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
		}
		output += "\"";
		currentIndex++;
		return output;
	}

	/**
	 * Extracts a variable from the input text.
	 * 
	 * @return String
	 * @throws SmartScriptLexerException - if the current part of the text is an
	 *                                   invalid variable
	 */

	private String extractVariable() {
		String output = "";
		if (!Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException("Invalid name.");
		}

		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {
			output += String.valueOf(data[currentIndex]);
			currentIndex++;
		}
		return output;
	}

	/**
	 * Sets the state of the lexer.
	 * 
	 * @param state - the state that the lexer will be put in
	 */

	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	public LexerState getState() {
		return this.state;
	}

	/**
	 * Switches the state of the lexer.
	 */

	public void switchState() {
		if (this.state == LexerState.TAGS) {
			this.state = LexerState.BASIC;
		} else {
			this.state = LexerState.TAGS;
		}
	}

	/**
	 * Returns the last token extracted.
	 * 
	 * @return Token
	 */

	public Token getToken() {
		if (this.token == null) {
			throw new SmartScriptLexerException();
		}
		return this.token;
	}

	/**
	 * Helper method used to skip all whitespace characters.
	 */

	private void skipBlanks() {
		while (true) {
			if (this.currentIndex >= data.length) {
				break;
			}
			if (Character.isWhitespace(this.data[this.currentIndex])) {
				this.currentIndex++;
			} else {
				break;
			}
		}
	}
}
