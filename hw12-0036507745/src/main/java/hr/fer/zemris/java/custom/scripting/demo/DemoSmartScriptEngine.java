package hr.fer.zemris.java.custom.scripting.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * 
 * Demo program for SmartScriptEngine. Accepts a path to file as an argument. Then executes the engine.
 * 
 * @author Lovro Matošević
 *
 */

public class DemoSmartScriptEngine {

	/**
	 * 
	 * @param args - the path to file which should be run by
	 *             {@link SmartScriptEngine}
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		String fileName = args[0];

		String documentBody = readFromDisk(fileName);

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		parameters.put("broj", "4");

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	private static String readFromDisk(String fileName) {
		Path path = Paths.get(fileName);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
