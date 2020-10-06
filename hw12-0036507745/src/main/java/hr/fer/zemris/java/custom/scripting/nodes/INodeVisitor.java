package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * 
 * Represents a model of a visitor used to visit instances of {@link TextNode},
 * {@link ForLoopNode}, {@link EchoNode} and {@link DocumentNode}.
 *
 */

public interface INodeVisitor {

	/**
	 * Visits and processes the given {@link TextNode}.
	 * 
	 * @param node - node being visited
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits and processes the given {@link ForLoopNode}.
	 * 
	 * @param node - node being visited
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits and processes the given {@link EchoNode}.
	 * 
	 * @param node - node being visited
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits and processes the given {@link DocumentNode}.
	 * 
	 * @param node - node being visited
	 */
	public void visitDocumentNode(DocumentNode node);

}
