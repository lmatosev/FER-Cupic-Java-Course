package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * 
 * Command used for printing all available charsets.
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. Should be called with no
	 * arguments.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			Map<String, Charset> charsets = Charset.availableCharsets();
			for (Entry<String, Charset> entry : charsets.entrySet()) {
				System.out.println(entry.getValue().displayName());
			}
		} else {
			System.out.println("Invalid number of arguments!");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<String>();
		String desc = "Command charsets takes no arguments and lists names of supported charsets.";
		lista.add(desc);
		return Collections.unmodifiableList(lista);
	}

}
