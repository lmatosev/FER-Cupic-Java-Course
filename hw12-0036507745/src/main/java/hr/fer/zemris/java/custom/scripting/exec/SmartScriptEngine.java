package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * Engine used for executing the document whose parsed tree it obtains.
 * 
 * @author Lovro Matošević
 *
 */

public class SmartScriptEngine {

	/**
	 * The base node
	 */
	private DocumentNode documentNode;
	/**
	 * Request context used for certain operations
	 */
	private RequestContext requestContext;
	/**
	 * Stack used for storing various variables and constants.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor which determines what should be done when visiting a certain node.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc} Simply writes the text from the given {@link TextNode}.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Error while writing.");
				return;
			}
		}

		/**
		 * {@inheritDoc} Initalizes the elements from the given {@link ForLoopNode} and
		 * iterates through the for loop.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ElementVariable variable = node.getVariable();
			String variableName = variable.getName();

			Element initialElem = node.getStartExpression();
			Element endElem = node.getEndExpression();
			Element stepElem = node.getStepExpression();

			ValueWrapper initialValue = this.setElementValue(initialElem);
			ValueWrapper endValue = this.setElementValue(endElem);
			ValueWrapper stepValue = new ValueWrapper((int) 1);
			if (stepElem != null) {
				stepValue = this.setElementValue(stepElem);
			}

			multistack.push(variableName, new ValueWrapper(initialValue.getValue()));

			ValueWrapper variableValue = initialValue;

			while (variableValue.numCompare(endValue.getValue()) <= 0) {

				for (int i = 0; i < node.numberOfChildren(); i++) {
					Node child = node.getChild(i);
					child.accept(visitor);
				}

				variableValue.add(stepValue.getValue());

				ValueWrapper newValue = new ValueWrapper(multistack.pop(variableName).getValue());
				newValue = variableValue;
				multistack.push(variableName, new ValueWrapper(newValue.getValue()));
			}

			multistack.pop(variableName);

		}

		/**
		 * Helper method used to create a {@link ValueWrapper} of the provided element.
		 * 
		 * @param elem - element whose value will be wrapped
		 * @return wrapper - the resulting {@link ValueWrapper}
		 */
		private ValueWrapper setElementValue(Element elem) {

			Object returnValue = null;

			if (elem instanceof ElementConstantInteger) {
				returnValue = ((ElementConstantInteger) elem).getValue();
			} else if (elem instanceof ElementConstantDouble) {
				returnValue = ((ElementConstantDouble) elem).getValue();
			} else if (elem instanceof ElementString) {
				returnValue = Double.parseDouble(((ElementString) elem).getValue());
			} else if (elem instanceof ElementVariable) {
				String variableName = ((ElementVariable) elem).getName();
				Object value = multistack.peek(variableName).getValue();
				if (value instanceof Integer) {
					returnValue = ((int) value);
				} else if (value instanceof Double) {
					returnValue = (double) value;
				}
			}

			return new ValueWrapper(returnValue);
		}

		/**
		 * {@inheritDoc} Processes node's elements and does an action depending on the
		 * type of the element.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectMultistack objectStack = new ObjectMultistack();

			for (Element elem : node.getElements()) {

				if (elem instanceof ElementConstantDouble) {

					ValueWrapper vw = new ValueWrapper(((ElementConstantDouble) elem).getValue());
					objectStack.push("constant", vw);

				} else if (elem instanceof ElementConstantInteger) {

					ValueWrapper vw = new ValueWrapper(((ElementConstantInteger) elem).getValue());
					objectStack.push("constant", vw);

				} else if (elem instanceof ElementString) {

					ValueWrapper vw = new ValueWrapper(((ElementString) elem).getValue());
					objectStack.push("constant", vw);

				} else if (elem instanceof ElementVariable) {

					String name = ((ElementVariable) elem).getName();
					ValueWrapper value = multistack.peek(name);
					objectStack.push("constant", value);

				} else if (elem instanceof ElementOperator) {

					this.executeOperator((ElementOperator) elem, objectStack);

				} else if (elem instanceof ElementFunction) {

					this.executeFunction((ElementFunction) elem, objectStack);

				}
			}

			ObjectMultistack tempStack = new ObjectMultistack();

			ValueWrapper vw;
			while (!objectStack.isEmpty("constant")) {

				vw = objectStack.pop("constant");
				tempStack.push("constant", vw);

			}

			while (!tempStack.isEmpty("constant")) {
				// TODO tu kasnije makni ovaj e.printStackTrace()
				try {
					requestContext.write(tempStack.pop("constant").getValue().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		/**
		 * Helper method used to execute the operator.
		 * 
		 * @param elem        - provided element opeartor
		 * @param objectStack - stack used for executing the operations
		 */
		private void executeOperator(ElementOperator elem, ObjectMultistack objectStack) {

			String operator = elem.getSymbol();

			ValueWrapper vw1 = objectStack.pop("constant");
			ValueWrapper vw2 = objectStack.pop("constant");

			switch (operator) {
			case "+":
				vw1.add(vw2.getValue());
				objectStack.push("constant", vw1);
				break;
			case "-":
				vw2.subtract(vw1.getValue());
				objectStack.push("constant", vw2);
				break;
			case "*":
				vw1.multiply(vw2.getValue());
				objectStack.push("constant", vw1);
				break;
			case "/":
				vw2.divide(vw1.getValue());
				objectStack.push("constant", vw2);
				break;
			default:
				break;
			}

		}

		/**
		 * Helper method used to execute the given function.
		 * 
		 * @param elem        - the provided function
		 * @param objectStack - stack used while executing the function
		 */
		private void executeFunction(ElementFunction elem, ObjectMultistack objectStack) {

			String funcName = elem.getName();

			switch (funcName) {
			case "sin":
				ValueWrapper vw = objectStack.pop("constant");
				double val;
				if (vw.getValue() instanceof Integer) {
					val = Math.sin(Math.toRadians((double) ((int) vw.getValue())));
				} else {
					val = Math.sin(Math.toRadians((double) vw.getValue()));
				}
				vw = new ValueWrapper(val);
				objectStack.push("constant", vw);
				break;

			case "decfmt":
				ValueWrapper f = objectStack.pop("constant");
				ValueWrapper x = objectStack.pop("constant");
				DecimalFormat format = new DecimalFormat(f.getValue().toString());
				String str = format.format(x.getValue());
				ValueWrapper r = new ValueWrapper(str);
				objectStack.push("constant", r);
				break;

			case "dup":
				x = objectStack.pop("constant");
				objectStack.push("constant", x);
				objectStack.push("constant", x);
				break;

			case "swap":
				ValueWrapper a = objectStack.pop("constant");
				ValueWrapper b = objectStack.pop("constant");
				objectStack.push("constant", a);
				objectStack.push("constant", b);
				break;

			case "setMimeType":
				x = objectStack.pop("constant");
				requestContext.setMimeType(x.getValue().toString());
				break;

			case "paramGet":
				ValueWrapper dv = objectStack.pop("constant");
				ValueWrapper name = objectStack.pop("constant");
				String value = requestContext.getParameter(name.getValue().toString());
				objectStack.push("constant", value == null ? dv : new ValueWrapper(value));
				break;

			case "pparamGet":
				dv = objectStack.pop("constant");
				name = objectStack.pop("constant");
				value = requestContext.getPersistentParameter(name.getValue().toString());
				objectStack.push("constant", value == null ? dv : new ValueWrapper(value));
				break;

			case "pparamSet":
				name = objectStack.pop("constant");
				ValueWrapper paramValue = objectStack.pop("constant");
				requestContext.setPersistentParameter(name.getValue().toString(), paramValue.getValue().toString());
				break;

			case "pparamDel":
				name = objectStack.pop("constant");
				requestContext.removePersistentParameter(name.getValue().toString());
				break;

			case "tparamGet":
				dv = objectStack.pop("constant");
				name = objectStack.pop("constant");
				value = requestContext.getTemporaryParameter(name.getValue().toString());
				objectStack.push("constant", value == null ? dv : new ValueWrapper(value));
				break;

			case "tparamSet":
				name = objectStack.pop("constant");
				paramValue = objectStack.pop("constant");
				requestContext.setTemporaryParameter(name.getValue().toString(), paramValue.getValue().toString());
				break;

			case "tparamDel":
				name = objectStack.pop("constant");
				requestContext.removeTemporaryParameter(name.getValue().toString());
				break;

			default:
				break;
			}

		}

		/**
		 * {@inheritDoc} Accepts all direct children of the provided node.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node child = node.getChild(i);
				child.accept(visitor);
			}
		}
	};

	/**
	 * The main constructor for this class. Accepts a document node and a request
	 * context.
	 * 
	 * @param documentNode   - node used for executing the engine
	 * @param requestContext - used by functions
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes the engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
