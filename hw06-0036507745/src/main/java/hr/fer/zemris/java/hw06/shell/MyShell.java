package hr.fer.zemris.java.hw06.shell;

/**
 * 
 * This program represents a simple shell with some basic commands. Type help to
 * list all currently available commands. Command "exit" terminates the shell.
 * 
 * @author Lovro Matošević
 *
 */
public class MyShell {

	public static void main(String[] args) {
		Environment env = new ShellEnvironment();
		ShellStatus status;
		env.writeln("Welcome to MyShell v 1.0");
		do {
			String line = env.readLine();
			String commandName = extractName(line);
			String arguments = extractArguments(line);
			ShellCommand command = env.commands().get(commandName);
			if (command == null) {
				env.writeln("Invalid command! Please enter a valid command.");
				status = ShellStatus.CONTINUE;
			} else {
				try {
					status = command.executeCommand(env, arguments);
				} catch (ShellIOException ex) {
					status = ShellStatus.TERMINATE;
				}
			}
		} while (status != ShellStatus.TERMINATE);
	}

	/**
	 * Helper method used to extract only the arguments from the input line.
	 * 
	 * @param line - the line from which the extraction is done
	 * @return String containing the arguments
	 */
	private static String extractArguments(String line) {
		String[] inputSplit = line.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < inputSplit.length; i++) {
			sb.append(inputSplit[i] + " ");
		}
		return sb.toString();
	}

	/**
	 * Helper method used to extract the name of the command.
	 * 
	 * @param line - the line from which the extraction is done
	 * @return String containing the command's name
	 */

	private static String extractName(String line) {
		String[] inputSplit = line.split("\\s+");
		return inputSplit[0];
	}

}
