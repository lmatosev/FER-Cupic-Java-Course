package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.*;

/**
 *
 * Class used for testing of SmartScriptParser.
 *
 */

public class SmartScriptTester {
	
	/**
	 * 
	 * @param args - the file path of the document to be parsed
	 */
	public static void main(String[] args) {
		String filepath = args[0];

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException ex) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception ex) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println(parser.createOriginalDocumentBody(document2));
	}

}
