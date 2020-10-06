package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;
import hr.fer.zemris.java.hw06.shell.commands.util.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.util.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.util.NameBuilderParser;

/**
 * Command used for mass renaming and/or transfer of files. Commands which can
 * be used in the massrename shell commands are following: filter, groups, show
 * and execute. Filter command prints all the files from source directory which
 * satisfy the provided mask. Groups is the same except it adjusts the output to
 * show the groups which were matched. Show command accepts one extra argument,
 * an expression which tells the command how to generate the new name. Then
 * prints all the matching files and their new names. Finally the execute
 * command is effectively the same as show, but it also copies the files which
 * satisfy the mask from the source directory to the target directory and
 * renames them using the expression provided.
 * 
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * Executes the command in the specified environment. Accepts 4 or more
	 * arguments. First two arguments should be valid existing directories. The
	 * third argument must be one of available commands (filter, groups, show or
	 * execute). The fourth argument is the mask used for filtering files in the
	 * source directory.
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
			e.printStackTrace();
			return ShellStatus.CONTINUE;
		}

		if (strPath.size() < 4) {
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}

		Path dir1 = ShellUtil.validizePath(env, strPath.get(0));
		Path dir2 = ShellUtil.validizePath(env, strPath.get(1));

		if (!Files.exists(dir1) || !Files.exists(dir2) || !Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
			env.writeln("Path must be an existing path to a directory.");
			return ShellStatus.CONTINUE;
		}

		String cmd = strPath.get(2);

		if (cmd.equals("filter") || cmd.equals("groups")) {

			if (strPath.size() != 4) {
				env.writeln("Invalid number of arguments for cmd.");
				return ShellStatus.CONTINUE;
			}

			String mask = strPath.get(3);
			List<FilterResult> filterList = null;
			try {
				filterList = filter(dir1, mask);
			} catch (IOException e) {
				env.writeln("Error while filtering.");
				return ShellStatus.CONTINUE;
			}

			if (cmd.equals("filter")) {
				for (var name : filterList) {
					env.writeln(name.toString());
				}
			} else {
				for (var name : filterList) {
					StringBuilder sb = new StringBuilder();
					sb.append(name.toString());
					sb.append(" ");
					for (int i = 0; i < name.numberOfGroups() + 1; i++) {
						sb.append(i + ": ");
						sb.append(name.group(i) + " ");
					}
					env.writeln(sb.toString());
				}
			}

		} else if (cmd.equals("show") || cmd.equals("execute")) {
			if (strPath.size() != 5) {
				env.writeln("Invalid number of arguments for cmd.");
				return ShellStatus.CONTINUE;
			}
			String mask = strPath.get(3);
			try {
				List<FilterResult> files = filter(dir1, mask);
				NameBuilderParser parser = new NameBuilderParser(strPath.get(4));
				NameBuilder builder = parser.getNameBuilder();
				for (var file : files) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String novoIme = sb.toString();
					env.writeln(dir1.toString() + "/" + file.toString() + " => " + dir2.toString() + "/" + novoIme);

					if (cmd.equals("execute")) {
						Files.move(dir1.resolve(file.toString()), dir2.resolve(novoIme),
								StandardCopyOption.REPLACE_EXISTING);
					}
				}
			} catch (IOException e) {
				env.writeln("Error while filtering files.");
				return ShellStatus.CONTINUE;
			}

		} else {
			env.writeln("Invalid cmd name.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "massrename";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "massrename {directory1} {directory2} {cmd} {mask} {optional}";
		String desc2 = "Command used for mass rename/transfer of files which are located in directory1.";
		String desc3 = "Files will be transfered to directory2, which can be equal to directory1.";
		String desc4 = "Mask is a regular expression written to match Pattern class requirements.";
		String desc5 = "Mask is used to select files from directory1.";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		lista.add(desc4);
		lista.add(desc5);
		return Collections.unmodifiableList(lista);
	}

	/**
	 * Used for filtering the files in the provided directory.
	 * 
	 * @param dir     - source directory
	 * @param pattern - pattern to be matched
	 * @return result - list of FilterResults
	 * @throws IOException - if there was an error while reading files
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher m;
		List<FilterResult> lista = new ArrayList<>();
		List<Path> pathList = Files.list(dir).collect(Collectors.toList());
		for (var path : pathList) {
			m = p.matcher(path.getFileName().toString());
			if (m.matches() && Files.isRegularFile(path)) {
				FilterResult fr = new FilterResult(path, m);
				lista.add(fr);
			}
		}
		return lista;
	}
}
