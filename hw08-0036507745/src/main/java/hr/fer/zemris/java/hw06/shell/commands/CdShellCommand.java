package hr.fer.zemris.java.hw06.shell.commands;

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
 * Command used for changing current directory.
 * 
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts one argument. The
	 * argument provided should be a valid absolute or relative path.
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

		if (strPath.size() != 1) {
			env.write("Invalid number of arguments provided.");
			return ShellStatus.CONTINUE;
		}
		Path path = Paths.get(strPath.get(0)).normalize();
		if (path.isAbsolute()) {
			env.setCurrentDirectory(path);
		} else {
			path = env.getCurrentDirectory().resolve(path);
			if (Files.exists(path) && Files.isDirectory(path)) {
				env.setCurrentDirectory(env.getCurrentDirectory().resolve(path));
			} else {
				env.writeln("Invalid path provided.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "cd {path}";
		String desc2 = "Accepts a single argument, path to the new working directory and sets the working directory to the provided path.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
