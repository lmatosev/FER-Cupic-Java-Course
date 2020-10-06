package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.charset.*;

import org.junit.jupiter.api.Test;

class SmartScriptParserTest {

	@Test
	void testValidInputs() {
		
		String docBody1 = loader("document1.txt");
		assertDoesNotThrow(() -> new SmartScriptParser(docBody1));

		String docBody2 = loader("document2.txt");
		assertDoesNotThrow(() -> new SmartScriptParser(docBody2));

		String docBody3 = loader("document3.txt");
		assertDoesNotThrow(() -> new SmartScriptParser(docBody3));

		String docBody4 = loader("document4.txt");
		assertDoesNotThrow(() -> new SmartScriptParser(docBody4));
		
		String docBody5 = loader("document14.txt");
		assertDoesNotThrow(()->new SmartScriptParser(docBody5));

	}
	
	
	@Test
	void testInvalidForLoop() {
		String docBody = loader("document5.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
		
		String docBody2 = loader("document6.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody2));
	}
	
	@Test
	void testEmptyEcho() {
		String docBody = loader("document7.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testInvalidEscapingTags() {
		String docBody = loader("document8.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
		
		String docBody2 = loader("document9.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody2));
	}
	
	@Test
	void testInvalidEscapingText() {
		String docBody = loader("document9.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testTagNotClosed() {
		String docBody = loader("document10.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testInvalidDoubleInput() {
		String docBody = loader("document11.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testInvalidTagName() {
		String docBody = loader("document11.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testEmptyTag() {
		String docBody = loader("document12.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	void testInvalidNumberOfEndTags() {
		String docBody = loader("document13.txt");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);

		} catch (IOException ex) {
			return null;
		}

	}

}
