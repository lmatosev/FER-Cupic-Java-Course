package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command used for printing the current working directory to user.
 * 
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts 0 arguments.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Invalid input. Pwd accepts no arguments.");
			return ShellStatus.CONTINUE;
		}
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "pwd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "pwd";
		String desc2 = "Takes no arguments, prints the current working directory to the terminal.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
