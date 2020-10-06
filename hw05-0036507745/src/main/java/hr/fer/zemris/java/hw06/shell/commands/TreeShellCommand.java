package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * 
 * Command used for recursively listing contents of directories in a tree-like
 * format.
 * 
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Executes the command in provided environment. Tree command accepts one
	 * argument and it must be a valid path.
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
			if (Files.exists(path) && Files.isDirectory(path)) {
				FileVisitor<Path> visitor = new TreeVisitor(env);
				try {
					Files.walkFileTree(path, visitor);
				} catch (IOException e) {
					env.writeln("Error while executing command.");
					return ShellStatus.CONTINUE;
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
		return "tree";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<String>();
		String desc = "tree {directoryName}";
		String desc2 = "The tree command expects a single argument: directory name and prints a tree (each directory level shifts\n"
				+ "output two charatcers to the right).";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

	/**
	 * 
	 * Used for recursively listing all directories and files.
	 * 
	 */
	private class TreeVisitor implements FileVisitor<Path> {
		private int razina;
		private Environment env;

		public TreeVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			razina--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
			env.writeln(" ".repeat(2 * razina) + arg0.getFileName());
			razina++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
			env.writeln(" ".repeat(2 * (razina + 1)) + arg0.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

}
