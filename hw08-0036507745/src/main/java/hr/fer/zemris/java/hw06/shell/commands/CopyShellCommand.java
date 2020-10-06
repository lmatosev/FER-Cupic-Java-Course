package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
 * Command used to copy file's contents to another file.
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Executes the current command in the provided environment. This command
	 * accepts exactly 2 arguments. The first argument should be an existing file
	 * path, and the second one can be a directory path or a file path, but it must
	 * be valid. If the destination file exists, the user will be prompted if he
	 * wants to overwrite the file.
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
		if (strPath.size() == 2) {
			String srcPath = strPath.get(0);
			String destPath = strPath.get(1);

			Path src = ShellUtil.validizePath(env, srcPath);
			Path dest = ShellUtil.validizePath(env, destPath);

			if (Files.exists(src) && Files.isRegularFile(src)) {
				if (Files.exists(dest) && Files.isRegularFile(dest)) {
					env.writeln("Destination file exists. Overwrite? Y/N");
					String input = env.readLine();
					if (input.equalsIgnoreCase("n")) {
						env.writeln("Stopping copying.");
						return ShellStatus.CONTINUE;
					}
				}
				if (Files.exists(dest) && Files.isDirectory(dest)) {
					StringBuilder sb = new StringBuilder();
					sb.append(dest.toString() + "/" + src.getFileName());
					dest = Paths.get(sb.toString());
				}

				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src.toFile())));
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(dest.toFile())))) {

					String line;
					while ((line = br.readLine()) != null) {
						bw.write(line + "\n");
					}

				} catch (IOException e) {
					env.writeln("Error while copying.");
					return ShellStatus.CONTINUE;
				}

			} else {
				env.writeln("Source must be an existing file.");
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
		return "copy";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "copy {source} {destination}";
		String desc2 = "The copy command expects two arguments: source file name and destination file name.";
		String desc3 = "If the second argument is directory, the original file will be copied into that directory using the original file name.";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		return Collections.unmodifiableList(lista);
	}

}
