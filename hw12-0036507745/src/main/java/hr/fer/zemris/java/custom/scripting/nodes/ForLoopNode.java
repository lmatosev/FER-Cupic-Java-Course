package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * 
 * A node representing a single for-loop construct.
 *
 */

public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**
	 * Constructor for this node which accepts 4 parameters. The first one is an
	 * instance of ElementVariable. The others are instances of Element. Step
	 * expression argument can be null.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("Argument cannot be null");
		}

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * 
	 * @return ElementVariable - variable
	 */

	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * 
	 * @return Element - start expression
	 */

	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * 
	 * @return Element - end expression
	 */

	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * 
	 * @return Element - step expression
	 */

	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns a string representation of this node.
	 */

	@Override
	public String toString() {
		String output = "{$FOR ";

		output += String.valueOf(this.variable.asText()) + " ";
		output += String.valueOf(this.startExpression.asText()) + " ";
		output += String.valueOf(this.endExpression.asText()) + " ";
		if (this.stepExpression != null) {
			output += String.valueOf(this.stepExpression.asText()) + " ";
		}

		output += "$}";
		return output;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
