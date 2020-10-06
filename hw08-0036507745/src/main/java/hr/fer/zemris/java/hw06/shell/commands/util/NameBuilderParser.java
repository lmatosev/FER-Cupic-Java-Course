package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * Parser used for parsing the expression provided to the massrename command.
 * 
 * @author Lovro Matošević
 *
 */
public class NameBuilderParser {
	/**
	 * Expression being parsed
	 */
	private String expression;
	/**
	 * Builder containing all parsed NameBuilders.
	 */
	private CompositeNameBuilder builder;
	/**
	 * Lexer for this parser.
	 */
	private NameBuilderLexer lexer;
	/**
	 * Token returned by the lexer.
	 */
	private Token token;

	/**
	 * Creates a NameBuilder with text.
	 * 
	 * @param t - text to be added to the NameBuilder
	 * @return builder - a new builder which contains the provided text
	 */
	private static NameBuilder text(String t) {
		return (fr, sb) -> sb.append(t);
	}

	/**
	 * Creates a NameBuilder with groups.
	 * 
	 * @param index - index of the group
	 * @return builder - a new builder which contains the provided group
	 */
	private static NameBuilder group(int index) {
		return (fr, sb) -> sb.append(fr.group(index).toString());
	}

	/**
	 * Creates a NameBuilder with groups and adds the provided padding.
	 * 
	 * @param index    - index of the group
	 * @param padding  - padding to be used if the width is less than minWidth
	 * @param minWidth - minimal width of group
	 * @return builder - a new builder which contains the provided group and padding
	 *         if needed
	 */
	private static NameBuilder group(int index, char padding, int minWidth) {
		return (fr, sb) -> {
			String group = fr.group(index);
			if (group.length() >= minWidth) {
				sb.append(group);
			} else {
				sb.append(String.valueOf(padding).repeat(minWidth - group.length()) + group);
			}
		};
	}

	/**
	 * The main constructor for the parser. Accepts a string representing an
	 * expression to be parsed.
	 * 
	 * @param expr - expression to be parsed
	 */
	public NameBuilderParser(String expr) {
		this.expression = expr;
		this.lexer = new NameBuilderLexer(this.expression);
		this.builder = new CompositeNameBuilder();
		this.parse();
	}

	/**
	 * Parses the provided expression.
	 */
	private void parse() {
		token = lexer.nextToken();
		while (!isTokenOfType(token, TokenType.EOF)) {

			if (isTokenOfType(token, TokenType.GROUP_START)) {
				this.parseGroup();
			} else if (isTokenOfType(token, TokenType.STRING)) {
				String t = (String) token.getValue();
				builder.addNameBuilder(NameBuilderParser.text(t));
			} else {
				throw new NameBuilderParserException("Error while parsing");
			}
			token = lexer.nextToken();
		}

	}

	/**
	 * Parses a group.
	 */
	private void parseGroup() {
		int group;
		char padding;
		int minWidth;
		lexer.setState(NameBuilderLexerState.GROUP);
		token = lexer.nextToken();

		if (isTokenOfType(token, TokenType.NUMBER_STRING)) {
			group = Integer.parseInt((String) token.getValue());
			if (group < 0) {
				throw new NameBuilderParserException("Group number should be non-negative.");
			}
		} else {
			throw new NameBuilderParserException("Error while parsing.");
		}

		token = lexer.nextToken();

		if (isTokenOfType(token, TokenType.GROUP_END)) {
			lexer.setState(NameBuilderLexerState.REGULAR);
			this.builder.addNameBuilder(NameBuilderParser.group(group));
			return;
		} else if (isTokenOfType(token, TokenType.NUMBER_STRING)) {
			String str = (String) token.getValue();
			if (str.length() == 1) {
				padding = ' ';
				minWidth = str.charAt(0);
				this.builder.addNameBuilder(NameBuilderParser.group(group, padding, minWidth));
			} else if (str.length() == 2) {
				padding = str.charAt(0);
				minWidth = Integer.parseInt(String.valueOf(str.charAt(1)));
				this.builder.addNameBuilder(NameBuilderParser.group(group, padding, minWidth));
			} else {
				throw new NameBuilderParserException("Error while parsing.");
			}
		} else {
			throw new NameBuilderParserException("Error while parsing.");
		}

		token = lexer.nextToken();

		if (isTokenOfType(token, TokenType.GROUP_END)) {
			lexer.setState(NameBuilderLexerState.REGULAR);
			return;
		} else {
			throw new NameBuilderParserException("Error while parsing.");
		}

	}

	/**
	 * 
	 * @return builder - returns the stored NameBuilder which is the product of
	 *         parsing.
	 */
	public NameBuilder getNameBuilder() {
		return this.builder;
	}

	/**
	 * Helper method used to check if token is of specific type.
	 * 
	 * @param token - token to be checked
	 * @param type  - type being compared to
	 * @return boolean - true if token is of the provided type and false else
	 */
	private boolean isTokenOfType(Token token, TokenType type) {
		if (token == null) {
			return false;
		}
		return token.getTokenType() == type;
	}

}
