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
 * Command used for removing the path on top of the stack. Doesnt change the
 * current directory.
 * 
 */
public class DropdShellCommand implements ShellCommand {

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
			env.write("Cannot pop from empty stack.");
			return ShellStatus.CONTINUE;
		}

		st.pop();

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "dropd {}";
		String desc2 = "Accepts 0 arguments. Pops a directory from stack and drops it.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
