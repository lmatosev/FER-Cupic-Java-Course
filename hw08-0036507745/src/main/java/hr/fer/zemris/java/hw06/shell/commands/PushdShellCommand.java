package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * Command used for pushing a path to the stack. Pushes the current path to the
 * stack and sets the provided path as the current directory.
 * 
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts one argument and
	 * it must be a valid path to directory.
	 */
	@SuppressWarnings("unchecked")
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
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}
		Path path = ShellUtil.validizePath(env, strPath.get(0));

		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("Path must be an existing directory.");
			return ShellStatus.CONTINUE;
		}
		Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
		if (st == null) {
			st = new Stack<>();
		}
		st.push(env.getCurrentDirectory());
		env.setSharedData("cdstack", st);
		env.setCurrentDirectory(path);

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "pushd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "pushd {path}";
		String desc2 = "Pushes the current directory to stack, and sets the path provided as working directory.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
