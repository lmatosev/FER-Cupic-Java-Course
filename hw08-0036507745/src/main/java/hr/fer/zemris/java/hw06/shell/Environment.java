package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * 
 * Environment used for implementing a shell.
 *
 */
public interface Environment {
	/**
	 * Reads a user input line.
	 * 
	 * @return line - line that was inputted
	 * @throws ShellIOException - if there was an error while reading
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes a string to user.
	 * 
	 * @param text - string to be written to user
	 * @throws ShellIOException - if there was an error while writing
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes a string to user and adds a newline character to the end.
	 * 
	 * @param text - string to be written to user
	 * @throws ShellIOException - if there was an error while writing
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns a map of commands.
	 * 
	 * @return commands - a sorted map containing commands as values and their names
	 *         as keys
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns the current multiline symbol.
	 * 
	 * @return symbol - current multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol.
	 * 
	 * @param symbol - symbol to be set
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns the current prompt symbol.
	 * 
	 * @return symbol - current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 * 
	 * @param symbol - symbol to be set
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns the current morelines symbol.
	 * 
	 * @return symbol - current morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the morelines symbol.
	 * 
	 * @param symbol - symbol to be set
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Returns the path to current directory.
	 * 
	 * @return path - path to current directory
	 */
	Path getCurrentDirectory();

	/**
	 * Sets the current directory to user provided path.
	 * 
	 * @param path - path to be set as current directory
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Returns the shared data.
	 * 
	 * @param key - key for shared data
	 * @return object - data stored for the provided key
	 */
	Object getSharedData(String key);

	/**
	 * Sets the shared data.
	 * 
	 * @param key   - key to be used
	 * @param value - value to be set
	 */
	void setSharedData(String key, Object value);
}
