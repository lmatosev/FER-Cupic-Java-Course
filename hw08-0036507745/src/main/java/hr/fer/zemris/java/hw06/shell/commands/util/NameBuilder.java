package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * 
 * Used for building names from the FilterResult.
 *
 */
public interface NameBuilder {

	/**
	 * Executes the current name builder.
	 * 
	 * @param result - FilterResult to be used
	 * @param sb     - string builder to be used
	 */
	void execute(FilterResult result, StringBuilder sb);

}
