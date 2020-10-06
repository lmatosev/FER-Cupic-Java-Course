package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * 
 * Command used for displaying files in a hexadecimal-like format.
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. Accepts only one argument
	 * which must be an existing file path.
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
			Path path = ShellUtil.validizePath(env, strPath.get(0));

			if (Files.exists(path) && Files.isRegularFile(path)) {
				try (InputStream in = new FileInputStream(path.toFile())) {
					int r;
					int i = 0;
					byte[] b = new byte[16];
					while (true) {
						r = in.read(b);
						if (r < 1) {
							break;
						}
						StringBuilder sb1 = new StringBuilder();
						StringBuilder sb2 = new StringBuilder();
						StringBuilder sb3 = new StringBuilder("| ");
						sb1.append(String.format("%08x", i * 16) + " ");
						for (int j = 0; j < 16; j++) {
							sb2.append(String.format("%02X", b[j]) + " ");
							int byteInt = (int) b[j];
							if (byteInt < 32 || byteInt > 127) {
								sb3.append(".");
							} else {
								sb3.append(String.valueOf((char) byteInt));
							}
						}
						i++;
						sb2.replace(23, 24, "|");
						env.writeln(sb1.toString() + sb2.toString() + sb3.toString());
					}
				} catch (IOException e) {
					env.writeln("Error while reading file.");
					return ShellStatus.CONTINUE;
				}
			} else {
				env.writeln("Provided argument should be an existing file.");
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
		return "hexdump";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "hexdump {fileName}";
		String desc2 = "Hexdump command expects a single argument: file name, and produces hex-output";
		String desc3 = "On the right side of the image only a standard subset of characters is shown; for all other characters a\n '.' is printed instead";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		return Collections.unmodifiableList(lista);
	}

}
