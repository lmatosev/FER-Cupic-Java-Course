package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class containing utility methods for MyShell.
 *
 */
public class ShellUtil {

	/**
	 * Parses the arguments to a list of strings which is then returned. Escaping is
	 * allowed.
	 * 
	 * @param arguments - the string to be parsed
	 * @return a List<String> containing the strings which are the product of the
	 *         parser
	 */

	public static List<String> parsePath(String arguments) {
		List<String> lista = new ArrayList<>();
		if (!arguments.contains("\"")) {
			String[] argumentSplit = arguments.split("\\s+");
			for (String str : argumentSplit) {
				lista.add(str);
			}
			return lista;
		}
		int currentIndex = 0;

		while (currentIndex < arguments.length() - 1) {
			while (Character.isWhitespace(arguments.charAt(currentIndex))) {
				currentIndex++;
			}

			if (arguments.charAt(currentIndex) == '"') {
				StringBuilder sb = new StringBuilder();
				currentIndex++;
				while (arguments.charAt(currentIndex) != '"') {
					if (arguments.charAt(currentIndex) == '\\') {
						if (arguments.charAt(currentIndex + 1) == '\\') {
							sb.append("\\");
							currentIndex++;
						} else if (arguments.charAt(currentIndex + 1) == '"') {
							sb.append("\"");
							currentIndex++;
						} else {
							sb.append(arguments.charAt(currentIndex) + arguments.charAt(currentIndex) + 1);
							currentIndex++;
						}
					} else {
						sb.append(arguments.charAt(currentIndex));
					}
					currentIndex++;
				}
				lista.add(sb.toString());
				// Check if there is something after the quotes which is not a space character
				if (currentIndex < arguments.length() - 1
						&& !Character.isWhitespace(arguments.charAt(currentIndex + 1))) {
					throw new IllegalArgumentException("Illegal argument.");
				}
			} else {
				StringBuilder sb = new StringBuilder();
				while (!Character.isWhitespace(arguments.charAt(currentIndex))
						&& currentIndex < arguments.length() - 1) {
					sb.append(arguments.charAt(currentIndex));
					currentIndex++;
				}
				lista.add(sb.toString());
			}
			currentIndex++;
		}
		return lista;
	}

}
