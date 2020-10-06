package hr.fer.zemris.java.custom.scripting.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * 
 * Program used to demonstrate the work of {@link INodeVisitor}. Accepts a path
 * to the file which should be parsed as an argument.
 * 
 * @author Lovro Matošević
 *
 */

public class TreeWriter {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		String strPath = args[0];

		Path path = Paths.get(strPath);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Error while reading the given file. Exiting.");
			return;
		}
		String docBody = sb.toString();
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();

		p.getDocumentNode().accept(visitor);

//		System.out.println(sb.toString());

	}

	/**
	 * 
	 * Class which implements the {@link INodeVisitor} interface. Implements methods
	 * used to visit document nodes.
	 * 
	 * @author Lovro Matošević
	 *
	 */

	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			StringBuilder sb = new StringBuilder();
			String text = node.getText();
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) == '\\') {
					sb.append("\\");
				} else if (text.charAt(i) == '{' && i < text.length() - 1) {
					if (text.charAt(i + 1) == '$') {
						sb.append("\\{");
					}

				} else {
					sb.append(text.charAt(i));
				}
			}
			System.out.print(sb.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String output = "{$FOR ";
			String variable = node.getVariable().asText();
			String startExpression = node.getStartExpression().asText();
			String endExpression = node.getEndExpression().asText();
			String stepExpression = node.getStepExpression().asText();

			output += String.valueOf(variable) + " ";
			output += String.valueOf(startExpression) + " ";
			output += String.valueOf(endExpression) + " ";
			if (stepExpression != null) {
				output += String.valueOf(stepExpression + " ");
			}

			output += "$}";
			System.out.print(output);
			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node child = node.getChild(i);
				child.accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			String output = "{$= ";
			Element[] elements = node.getElements();
			for (int i = 0; i < elements.length; i++) {
				output += elements[i].asText() + " ";
			}

			output += "$}";
			System.out.print(output);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node child = node.getChild(i);
				child.accept(this);
			}
		}

	}

}
