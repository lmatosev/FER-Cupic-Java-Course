package hr.fer.zemris.java.hw03.prob1;

/**
 * 
 * This class represents a simple lexer which has two states. The basic and the
 * extended state. If the input is invalid LexerException will be thrown.
 * 
 * @author Lovro Matošević
 *
 */

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	/**
	 * Constructor for this lexer.
	 * 
	 * @param text - text to processed by the lexer
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}

	/**
	 * Extracts the next token from the input text.
	 * 
	 * @return Token
	 * @throws LexerException - if the input text is invalid
	 */

	public Token nextToken() {

		if (this.token != null && this.token.getType() == TokenType.EOF) {
			throw new LexerException("No more tokens.");
		}

		if (data.length == 0) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		this.skipBlanks();

		if (this.state == LexerState.EXTENDED) {
			if (currentIndex == data.length) {
				this.token = new Token(TokenType.EOF, null);
				return this.token;
			}
			if (data[currentIndex] == '#') {
				this.token = new Token(TokenType.SYMBOL, '#');
				currentIndex++;
				return this.token;
			}
			String output = "";
			while (data[currentIndex] != '#' && currentIndex < data.length
					&& !Character.isWhitespace(data[currentIndex])) {
				output += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			}

			this.token = new Token(TokenType.WORD, output);
			return this.token;
		}

		if (this.currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String output = "";
			if (data[currentIndex] == '\\') {
				if (currentIndex == data.length - 1)
					throw new LexerException("Invalid input");
				if (data[currentIndex + 1] == '\\' || Character.isDigit(data[currentIndex + 1])) {
					output = String.valueOf(data[currentIndex + 1]);
					currentIndex += 2;
					if (this.currentIndex >= data.length) {
						throw new LexerException();
					}
				} else {
					throw new LexerException();
				}
			} else {
				output += String.valueOf(data[currentIndex]);
				this.currentIndex++;
			}
			while (this.currentIndex < data.length) {
				if (Character.isLetter(data[currentIndex])) {
					output += String.valueOf(data[currentIndex]);
				} else {
					if (data[currentIndex] == '\\') {
						if (currentIndex == data.length - 1) {
							throw new LexerException("Invalid input");
						}
						if (data[currentIndex + 1] == '\\' || Character.isDigit(data[currentIndex + 1])) {
							output += String.valueOf(data[currentIndex + 1]);
							currentIndex++;
						} else {
							throw new LexerException("Invalid input");
						}
					} else {
						break;
					}
				}
				this.currentIndex++;
			}
			this.token = new Token(TokenType.WORD, output);
			return this.token;
		}

		if (Character.isDigit(data[currentIndex])) {
			String output = String.valueOf(data[currentIndex]);
			currentIndex++;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				output += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
			try {
				Long value = Long.parseLong(output);
				this.token = new Token(TokenType.NUMBER, value);
				return this.token;
			} catch (Exception ex) {
				throw new LexerException();
			}
		}

		this.token = new Token(TokenType.SYMBOL, data[currentIndex]);
		this.currentIndex++;
		return this.token;
	}

	/**
	 * Returns the last token extracted.
	 * 
	 * @throws LexerException - if there were no tokens extracted yet
	 * @return Token
	 */

	public Token getToken() {
		if (this.token == null) {
			throw new LexerException();
		}
		return this.token;
	}

	/**
	 * Sets the state of the lexer.
	 * 
	 * @param state - state that will be set
	 * @throws NullPointerException if the state provided is null
	 */

	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
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
