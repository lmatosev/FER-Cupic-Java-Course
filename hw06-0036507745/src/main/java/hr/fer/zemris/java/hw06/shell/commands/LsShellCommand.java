package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * 
 * Command used for non-recursively listing directory contents.
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. Accepts only one argument
	 * and it must be an existing directory path.
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
				try {
					Files.list(path).forEach(new printAttributes(env));
				} catch (IOException e) {
					env.writeln("Unable to list files.");
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
		return "ls";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "ls {directoryName}";
		String desc2 = "Command ls takes a single argument – directory – and writes a directory listing (not recursive).";
		lista.add(desc);
		lista.add(desc2);
		return Collections.unmodifiableList(lista);
	}

	/**
	 * 
	 * Helper class used as a consumer to accept paths and print file's name and
	 * some basic file attributes.
	 *
	 */

	private class printAttributes implements Consumer<Path> {
		Environment env;

		public printAttributes(Environment env) {
			this.env = env;
		}

		@Override
		public void accept(Path path) {
			StringBuilder sb = new StringBuilder();
			sb.append(Files.isDirectory(path) ? "d" : "-");
			sb.append(Files.isReadable(path) ? "r" : "-");
			sb.append(Files.isWritable(path) ? "w" : "-");
			sb.append(Files.isExecutable(path) ? "x" : "-");
			sb.append(" ");
			String fileName = path.getFileName().toString();
			double fileSize = 0;
			try {
				fileSize = Files.size(path);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				String size = String.format("%10.0f", fileSize);
				sb.append(size + " ");
				sb.append(formattedDateTime + " ");
				sb.append(fileName);

				env.writeln(sb.toString());
			} catch (IOException e) {
				env.writeln("Error while listing files.");
			}
		}

	}

}
