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
 * Command used for listing all the paths currently on the stack.
 * 
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts 0 arguments.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Invalid input. Listd accepts no arguments.");
			return ShellStatus.CONTINUE;
		}

		Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
		if (st == null || st.isEmpty()) {
			env.writeln("Nema pohranjenih elemenata");
			return ShellStatus.CONTINUE;
		}
		List<Path> lista = new ArrayList<>();
		while (!st.isEmpty()) {
			Path path = st.pop();
			env.writeln(path.toString());
			lista.add(path);
		}
		Collections.reverse(lista);
		for (var path : lista) {
			st.push(path);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "listd";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "listd {}";
		String desc2 = "Accepts 0 arguments. Prints all the paths currently on the stack.";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

}
