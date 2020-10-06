package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * Enum containing possible NameBuilderLexer states.
 * 
 * @author Lovro Matošević
 *
 */
public enum NameBuilderLexerState {
	/**
	 * The regular state, used outside groups.
	 */
	REGULAR,
	/**
	 * State used when in groups.
	 */
	GROUP
}
