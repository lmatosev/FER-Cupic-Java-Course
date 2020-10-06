package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * Lexer used by the NameBuilderParser.
 *
 */

public class NameBuilderLexer {
	/**
	 * Input string saved as a char array.
	 */
	private char[] data;
	/**
	 * Index currently being checked.
	 */
	private int currentIndex;
	/**
	 * Last token that was returned.
	 */
	private Token token;
	/**
	 * State of the lexer
	 */
	private NameBuilderLexerState state;

	/**
	 * Main constructor for this lexer which accepts a string that needs to be
	 * checked.
	 * 
	 * @param input
	 */

	public NameBuilderLexer(String input) {
		this.data = input.toCharArray();
		this.currentIndex = 0;
		this.state = NameBuilderLexerState.REGULAR;
	}

	/**
	 * Returns the next token.
	 * 
	 * @return Token - token produced by the lexer
	 */

	public Token nextToken() {
		if (this.token != null && this.token.getTokenType() == TokenType.EOF) {
			throw new NameBuilderLexerException("No more tokens.");
		}

		this.skipBlanks();

		if (currentIndex == data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		if (data.length == 0) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		if (this.state == NameBuilderLexerState.GROUP) {
			if (data[currentIndex] == ',') {
				currentIndex++;
				this.skipBlanks();
			}
			if (Character.isDigit(data[currentIndex])) {
				String str = this.extractNumber();
				this.token = new Token(TokenType.NUMBER_STRING, str);
				return this.token;
			}
			if (data[currentIndex] == '}') {
				this.token = new Token(TokenType.GROUP_END, null);
				currentIndex++;
				return this.token;
			}

			throw new NameBuilderLexerException("Invalid symbol.");
		}

		if (data[currentIndex] == '$') {
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '{') {
				this.token = new Token(TokenType.GROUP_START, null);
				currentIndex += 2;
				return this.token;
			}
		}

		StringBuilder sb = new StringBuilder();
		while (currentIndex != data.length) {
			if (data[currentIndex] == '$') {
				if (currentIndex < data.length - 1 && data[currentIndex + 1] == '{') {
					this.token = new Token(TokenType.STRING, sb.toString());
					return this.token;
				}
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}

		this.token = new Token(TokenType.STRING, sb.toString());
		return this.token;
	}

	/**
	 * Extracts and returns a string.
	 * 
	 * @return String - string extracted
	 */

	private String extractNumber() {
		StringBuilder sb = new StringBuilder();
		while (Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex]);
			this.currentIndex++;
		}
		return sb.toString();
	}

	/**
	 * Skips all the whitespace characters when called.
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

	/**
	 * 
	 * @param state - state to be set
	 */
	public void setState(NameBuilderLexerState state) {
		this.state = state;
	}
}
