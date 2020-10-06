package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command used for popping a path from the stack. Sets the current directory to
 * the popped path.
 * 
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts 0 arguments.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Invalid input. Popd accepts no arguments.");
			return ShellStatus.CONTINUE;
		}
		Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
		if (st == null) {
			env.writeln("Stack does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (st.isEmpty()) {
			env.writeln("Cannot pop from empty stack.");
			return ShellStatus.CONTINUE;
		}

		Path path = st.pop();
		env.setCurrentDirectory(path);
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "popd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "popd {}";
		String desc2 = "Accepts 0 arguments. Pops the directory from the stack if possible and sets it as current directory.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
