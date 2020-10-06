package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * 
 * Command used for printing out all the available commands and their
 * descriptions.
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. Accepts zero or one
	 * arguments. The argument provided should be a valid command name.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			for (Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				env.writeln(entry.getKey());
			}
			return ShellStatus.CONTINUE;
		}

		String[] argumentSplit = arguments.trim().split("\\s+");
		if (argumentSplit.length == 1) {
			String commandName = argumentSplit[0];
			if (env.commands().containsKey(commandName)) {
				List<String> description = env.commands().get(commandName).getCommandDescription();
				env.writeln("Name: " + commandName);
				env.writeln("Description: ");
				for (var str : description) {
					env.writeln(str);
				}
			} else {
				env.writeln("Invalid command name!");
			}

		} else {
			env.writeln("Invalid number of arguments!");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<String>();
		String desc = "help {commandName}";
		String desc2 = "If started with no arguments, it lists names of all supported commands.";
		String desc3 = "If started with single argument, it prints the name and the description of the selected command";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		return Collections.unmodifiableList(lista);
	}

}
