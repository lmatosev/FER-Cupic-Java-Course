package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * 
 * A parser for a specific structured document format.
 * 
 * @author Lovro Matošević
 *
 */

public class SmartScriptParser {
	/**
	 * The input text that will be parsed.
	 */
	private String text;
	/**
	 * Instance of a lexer that this parser uses to generate tokens.
	 */
	private SmartScriptLexer lexer;
	/**
	 * The main node.
	 */
	private DocumentNode docNode;
	/**
	 * Stack used for creating the specified tree-like structure.
	 */
	private Stack<Object> stack;

	/**
	 * The main constructor for this parser. It accepts an input string which is
	 * going to be parsed.
	 * 
	 * @param body - the body of the document that shall be parsed
	 * @throws SmartScriptParserException - if anything exception is caught while
	 *                                    parsing; this most probably means that the
	 *                                    input text was invalid
	 */

	public SmartScriptParser(String body) {
		this.text = body;
		this.lexer = new SmartScriptLexer(text);
		try {
			this.Parse();
		} catch (SmartScriptLexerException ex) {
			throw new SmartScriptParserException("Invalid expression");
		}
	}

	/**
	 * 
	 * @return DocumentNode - the document generated by this parser
	 */

	public DocumentNode getDocumentNode() {
		return this.docNode;
	}

	/**
	 * Helper method used to check if a token is of specific type.
	 * 
	 * @param type
	 * @return boolean - returns true if the token was of type <code>type</code>,
	 *         and false else.
	 */

	private boolean isTokenOfType(TokenType type) {
		return this.lexer.getToken().getTokenType() == type;
	}

	/**
	 * Parser the input string.
	 * 
	 * @throws SmartScriptParserException - if the input was invalid
	 */

	private void Parse() {
		this.docNode = new DocumentNode();
		this.stack = new Stack<>();
		stack.push(docNode);

		while (true) {
			Token token;

			token = lexer.nextToken();

			if (isTokenOfType(TokenType.EOF)) {
				break;
			} else if (isTokenOfType(TokenType.TEXT)) {
				Node node = (Node) stack.pop();
				TextNode newNode = new TextNode((String) token.getValue());
				node.addChildNode(newNode);
				stack.push(node);
			} else if (isTokenOfType(TokenType.TAG)) {
				if (lexer.getState() == LexerState.BASIC) {
					lexer.switchState();
					try {
						token = lexer.nextToken();
					} catch (SmartScriptLexerException ex) {
						throw new SmartScriptParserException("Invalid expression");
					}
					if (isTokenOfType(TokenType.TAGNAME)) {
						if (((String) token.getValue()).equalsIgnoreCase("FOR")) {
							ForLoopNode newNode = parseFor();
							Node node = (Node) stack.pop();
							node.addChildNode(newNode);
							stack.push(node);
							stack.push(newNode);
						} else if (((String) token.getValue()).equalsIgnoreCase("END")) {
							stack.pop();
							if (stack.isEmpty()) {
								throw new SmartScriptParserException("Invalid expression.");
							}
							token = lexer.nextToken();
							if (isTokenOfType(TokenType.TAG)) {
								lexer.switchState();
							} else {
								throw new SmartScriptParserException("Invalid expression.");
							}
						} else if (token.getValue().equals("=")) {
							EchoNode newNode = parseEcho();
							Node node = (Node) stack.pop();
							node.addChildNode(newNode);
							stack.push(node);
						} else {
							throw new SmartScriptParserException("Invalid expression.");
						}

					} else {
						System.out.println(token.getTokenType());
						throw new SmartScriptParserException("Invalid expression.");
					}
				} else {
					lexer.setState(LexerState.BASIC);
				}
			}

		}
	}

	/**
	 * Parses an echo node.
	 * 
	 * @return EchoNode
	 * @throws SmartScriptParserException
	 */

	@SuppressWarnings("unused")
	private EchoNode parseEcho() {
		Token token = lexer.nextToken();
		List<Object> col = new ArrayList<>();
		Element[] elements = new Element[50];
		int elementIndex = 0;

		while (isTokenOfType(TokenType.TAG) == false) {
			if (isTokenOfType(TokenType.VARIABLE)) {
				ElementVariable element = new ElementVariable((String) token.getValue());
				col.add(element);
			} else if (isTokenOfType(TokenType.STRING)) {
				ElementString element = new ElementString((String) token.getValue());
				col.add(element);

			} else if (isTokenOfType(TokenType.OPERATOR)) {
				ElementOperator element = new ElementOperator((String) token.getValue());
				col.add(element);

			} else if (isTokenOfType(TokenType.INTEGER)) {
				ElementConstantInteger element = new ElementConstantInteger((int) token.getValue());
				col.add(element);

			} else if (isTokenOfType(TokenType.DOUBLE)) {
				ElementConstantDouble element = new ElementConstantDouble((double) token.getValue());
				col.add(element);

			} else if (isTokenOfType(TokenType.FUNCTION)) {
				ElementFunction element = new ElementFunction((String) token.getValue());
				col.add(element);

			} else {
				throw new SmartScriptParserException("Invalid input.");
			}
			token = lexer.nextToken();
		}

		Object[] arr = col.toArray();
		Element[] output = new Element[col.size()];
		for (int i = 0; i < output.length; i++) {
			output[i] = (Element) arr[i];
		}

		if (output.length == 0) {
			throw new SmartScriptParserException("Echo node must not be empty!");
		}

		lexer.switchState();
		EchoNode returnNode = new EchoNode(output);
		return returnNode;
	}

	/**
	 * Parses a for loop node.
	 * 
	 * @return ForLoopNode
	 * @throws SmartScriptParserException;
	 */

	private ForLoopNode parseFor() {
		Token token = lexer.nextToken();
		ElementVariable variable;
		Element startExpression;
		Element endExpression;
		Element stepExpression;
		if (isTokenOfType(TokenType.VARIABLE)) {
			variable = new ElementVariable((String) token.getValue());
		} else {
			throw new SmartScriptParserException("Wrong for loop argument.");
		}
		token = lexer.nextToken();
		if (isTokenOfType(TokenType.VARIABLE)) {
			startExpression = new ElementVariable((String) token.getValue());
		} else if (isTokenOfType(TokenType.STRING)) {
			startExpression = new ElementString((String) token.getValue());
		} else if (isTokenOfType(TokenType.DOUBLE)) {
			startExpression = new ElementConstantDouble((double) token.getValue());
		} else if (isTokenOfType(TokenType.INTEGER)) {
			startExpression = new ElementConstantInteger((int) token.getValue());
		} else {
			throw new SmartScriptParserException("Wrong for loop argument.");
		}

		token = lexer.nextToken();

		if (isTokenOfType(TokenType.VARIABLE)) {
			endExpression = new ElementVariable((String) token.getValue());
		} else if (isTokenOfType(TokenType.STRING)) {
			endExpression = new ElementString((String) token.getValue());
		} else if (isTokenOfType(TokenType.DOUBLE)) {
			endExpression = new ElementConstantDouble((double) token.getValue());
		} else if (isTokenOfType(TokenType.INTEGER)) {
			endExpression = new ElementConstantInteger((int) token.getValue());
		} else {
			throw new SmartScriptParserException("Wrong for loop argument.");
		}

		token = lexer.nextToken();

		if (isTokenOfType(TokenType.TAG)) {
			lexer.switchState();
			stepExpression = null;
		} else {
			if (isTokenOfType(TokenType.VARIABLE)) {
				stepExpression = new ElementVariable((String) token.getValue());
			} else if (isTokenOfType(TokenType.STRING)) {
				stepExpression = new ElementString((String) token.getValue());
			} else if (isTokenOfType(TokenType.DOUBLE)) {
				stepExpression = new ElementConstantDouble((double) token.getValue());
			} else if (isTokenOfType(TokenType.INTEGER)) {
				stepExpression = new ElementConstantInteger((int) token.getValue());
			} else {
				throw new SmartScriptParserException("Wrong for loop argument.");
			}
		}

		ForLoopNode node = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		return node;
	}

	/**
	 * Creates and returns a string representing the original document body which
	 * was parsed.
	 * 
	 * @param node - the node from which the string will be created
	 * @return String
	 */

	public static String createOriginalDocumentBody(Node node) {
		if (node == null) {
			throw new NullPointerException();
		}

		if (node.numberOfChildren() == 0) {
			return node.toString();
		}

		String output = "";

		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).numberOfChildren() != 0) {
				output += node.getChild(i).toString();
				output += createOriginalDocumentBody(node.getChild(i));
				output += "{$END$}";
			} else {
				output += createOriginalDocumentBody(node.getChild(i));
			}

		}

		return output;
	}

}