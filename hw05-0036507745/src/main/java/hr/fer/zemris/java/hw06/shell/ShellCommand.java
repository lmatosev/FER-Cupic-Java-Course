package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * 
 * A command for MyShell. Will be executed in the provided environment.
 *
 */

public interface ShellCommand {
	/**
	 * Executes the command in the provided environment using the provided
	 * arguments.
	 * 
	 * @param env       - environment in which the command will be executed.
	 * @param arguments - arguments used while executing the command.
	 * @return returns ShellStatus.CONTINUE always, except when the ShellIOException
	 *         has occured.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * 
	 * @return name of the command
	 */
	String getCommandName();

	/**
	 * 
	 * @return list of strings containing the command's description
	 */
	List<String> getCommandDescription();
}
