package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * 
 * Command used to make directories.
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. Accepts only one argument,
	 * and it must be a valid directory path.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isBlank()) {
			env.writeln("Invalid input.");
			return ShellStatus.CONTINUE;
		}
		List<String> strPath;
		try {
			strPath = ShellUtil.parsePath(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid argument provided as path.");
			return ShellStatus.CONTINUE;
		} catch (Exception e) {
			env.writeln("Error while parsing the argument!");
			return ShellStatus.CONTINUE;
		}
		if (strPath.size() == 1) {
			Path path = Paths.get(strPath.get(0));
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				env.writeln("Error while creating the directory!");
				return ShellStatus.CONTINUE;
			}

		} else {
			env.writeln("Invalid number of arguments.");

		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "mkdir {directoryName}";
		String desc2 = "The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
