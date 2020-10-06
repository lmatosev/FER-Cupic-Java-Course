package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * 
 * Command used for terminating the shell.
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. This command accepts no
	 * arguments and will always terminate the shell when executed.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<String>();
		String desc = "Terminates the shell when called.";
		lista.add(desc);
		return Collections.unmodifiableList(lista);
	}

}
