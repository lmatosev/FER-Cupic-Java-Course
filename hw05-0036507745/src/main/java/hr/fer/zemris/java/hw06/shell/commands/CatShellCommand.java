package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
 * Command used for printing file contents to the user.
 * 
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts one or two
	 * arguments. The first argument is mandatory and must be a path to a file.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isBlank()) {
			env.writeln("Invalid input.");
			return ShellStatus.CONTINUE;
		}
		List<String> args;
		try {
			args = ShellUtil.parsePath(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid argument provided as path.");
			return ShellStatus.CONTINUE;
		} catch (Exception e) {
			env.writeln("Error while parsing the argument!");
			return ShellStatus.CONTINUE;
		}
		if (args.size() == 1 || args.size() == 2) {
			Path path = Paths.get(args.get(0));
			Charset charset;
			if (args.size() == 2) {
				if (Charset.isSupported(args.get(1))) {
					charset = Charset.forName(args.get(1));
				} else {
					env.writeln("Unsupported charset.");
					return ShellStatus.CONTINUE;
				}
			} else {
				charset = Charset.defaultCharset();
			}
			if (Files.exists(path) && Files.isRegularFile(path)) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(path.toFile()), charset))) {
					String line;
					while ((line = br.readLine()) != null) {
						env.writeln(line);
					}
				} catch (IOException e) {

				}
			} else {
				env.writeln("Invalid path provided as argument.");
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
		return "cat";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "cat {filePath} {charset}";
		String desc2 = "Command cat takes one or two arguments. The first argument is path to some file and is mandatory.";
		String desc3 = "The second argument is charset name that should be used to interpret chars from bytes.";
		String desc4 = "If not provided, a default platform charset will be used.";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		lista.add(desc4);
		return Collections.unmodifiableList(lista);
	}

}
