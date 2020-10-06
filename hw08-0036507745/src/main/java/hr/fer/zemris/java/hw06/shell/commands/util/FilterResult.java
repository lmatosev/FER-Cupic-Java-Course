package hr.fer.zemris.java.hw06.shell.commands.util;

import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * Class used for storing the filter results.
 * 
 * @author Lovro Matošević
 *
 */
public class FilterResult {
	/**
	 * Path to the file which satisfies the matcher.
	 */
	private Path path;
	/**
	 * Matcher that was used.
	 */
	private Matcher matcher;

	/**
	 * The main constructor which accepts a path and a matcher.
	 * 
	 * @param path - path to the file which satisfies the matcher
	 * @param m    - matcher which was used
	 */
	public FilterResult(Path path, Matcher m) {
		this.path = path;
		this.matcher = m;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return path.getFileName().toString();
	}

	/**
	 * Returns the number of matched groups.
	 * 
	 * @return groups - number of matched groups
	 */
	public int numberOfGroups() {
		return this.matcher.groupCount();
	}

	/**
	 * Returns the group with the provided index.
	 * 
	 * @param index - index to be used for searching the groups
	 * @return string - group at the provided index
	 */
	public String group(int index) {
		return this.matcher.group(index);
	}

}
