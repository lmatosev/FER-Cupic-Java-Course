package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * 
 * The environment used in MyShell. All commands which MyShell supports are
 * executed in an instance of ShellEnvironment.
 * 
 * @author Lovro Matošević
 *
 */
public class ShellEnvironment implements Environment {
	/**
	 * Character used when the input consists of multiple lines.
	 */
	private Character multiLineSymbol;
	/**
	 * Character used for prompt.
	 */
	private Character promptSymbol;
	/**
	 * Character used to signal the shell that the input will consist of multiple
	 * lines.
	 */
	private Character moreLines;
	/**
	 * Map containing all the commands which the shell supports.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Scanner used for reading user input.
	 */
	private Scanner sc;

	/**
	 * Current working directory
	 */
	private Path currentDir;
	/**
	 * Data shared between commands
	 */
	private Map<String, Object> sharedData;

	/**
	 * The default constructor for the ShellEnvironment. Initializes all the fields
	 * to their default values and also initializes the command map.
	 */
	public ShellEnvironment() {
		this.multiLineSymbol = '|';
		this.promptSymbol = '>';
		this.moreLines = '\\';
		this.sc = new Scanner(System.in);
		this.currentDir = Paths.get(".").normalize().toAbsolutePath();
		this.sharedData = new HashMap<>();
		initializeCommands();
	}

	/**
	 * Initializes the commands and puts them in the commands map.
	 */
	private void initializeCommands() {
		commands = new TreeMap<>();
		commands.put("symbol", new SymbolShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			write(this.promptSymbol + " ");
			line = sc.nextLine();
			if (line.endsWith(String.valueOf(this.moreLines))) {
				StringBuilder sb = new StringBuilder();
				sb.append(line.substring(0, line.length() - 1));
				String newLine;
				while (true) {
					write(this.multiLineSymbol + " ");
					newLine = sc.nextLine();
					if (newLine.endsWith(String.valueOf(this.moreLines))) {
						sb.append(" " + newLine.substring(0, newLine.length() - 1));
					} else {
						sb.append(" " + newLine);
						break;
					}
				}
				line = sb.toString();
			}
		} catch (Exception e) {
			throw new ShellIOException("Error while reading.");
		}

		return line;
	}

	/**
	 * Writes the input text to the user.
	 * 
	 * @throws ShellIOException - when there is an error while writing
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException("Error while writing.");
		}

	}

	/**
	 * Writes the input text to the user and puts a new line character at the end.
	 * 
	 * @throws ShellIOException - when there is an error while writing
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException("Error while writing.");
		}
	}

	/**
	 * @return the list of commands contained in the environment
	 */

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(this.commands);
	}

	/**
	 * @return the current multi line symbol
	 */

	@Override
	public Character getMultilineSymbol() {
		return this.multiLineSymbol;
	}

	/**
	 * @param symbol - sets the multi line symbol to the provided symbol
	 */

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol = symbol;
	}

	/**
	 * @return the current prompt symbol
	 */

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	/**
	 * @param symbol - sets the prompt symbol to the provided symbol
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	/**
	 * @return the current more lines symbol
	 */

	@Override
	public Character getMorelinesSymbol() {
		return this.moreLines;
	}

	/**
	 * @param symbol - sets the more lines symbol to the provided symbol
	 */

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLines = symbol;
	}

	/**
	 * Returns the current directory
	 */
	@Override
	public Path getCurrentDirectory() {
		return this.currentDir;
	}

	/**
	 * Sets the current directory
	 * 
	 * @param path - the path to the directory which is going to be set as current
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		this.currentDir = path;
	}

	/**
	 * Returns shared data. Returns null if there is no shared data for key
	 * 
	 * @param key - key to return shared data
	 */
	@Override
	public Object getSharedData(String key) {
		if (this.sharedData.containsKey(key)) {
			return this.sharedData.get(key);
		}
		return null;
	}

	/**
	 * Sets shared data.
	 * 
	 * @param key   - key to set shared data
	 * @param value - value to be set
	 */
	@Override
	public void setSharedData(String key, Object value) {
		this.sharedData.put(key, value);
	}

}
