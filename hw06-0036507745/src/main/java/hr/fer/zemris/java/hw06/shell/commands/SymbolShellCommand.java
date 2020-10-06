package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * 
 * Command used to print out current symbol characters or change them to
 * something else.
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Executes the command in the provided environment. The symbol command accepts
	 * one or two arguments. The first argument is mandatory and it should be a
	 * valid symbol type, while the second argument can be any character which the
	 * provided symbol is going to be changed to.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentSplit = arguments.split("\\s+");
		if (argumentSplit.length == 1) {
			String symbolName = argumentSplit[0];
			if (symbolName.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if (symbolName.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else if (symbolName.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else {
				env.writeln("Invalid symbol name!");
			}
		} else if (argumentSplit.length == 2) {
			String symbolName = argumentSplit[0];
			String symbolCharacter = argumentSplit[1];
			if (symbolCharacter.length() > 1) {
				env.writeln("Invalid character symbol!");
				return ShellStatus.CONTINUE;
			}
			if (symbolName.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "'" + " to '" + symbolCharacter
						+ "'");
				env.setPromptSymbol(symbolCharacter.charAt(0));
			} else if (symbolName.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "'" + " to '"
						+ symbolCharacter + "'");
				env.setMorelinesSymbol(symbolCharacter.charAt(0));
			} else if (symbolName.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "'" + " to '"
						+ symbolCharacter + "'");
				env.setMultilineSymbol(symbolCharacter.charAt(0));
			} else {
				env.writeln("Invalid symbol name!");
			}

		} else {
			env.writeln("Invalid command arguments!");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return command's name
	 */

	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * Returns a list of string containing the command's description.
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		String desc = "Command used to change symbols or print characters currently associated to symbols.";
		String desc2 = "Use like ->   symbol {symbolName} {symbolCharacter} . Changes the symbol with name symbolName to symbolCharacter.";
		String desc3 = "Valid symbol names are PROMPT, MULTILINE and MORELINES.";
		String desc4 = "If symbolCharacter argument is left out, the information on the character currently associated to the symbol with symbolName will be printed.";
		lista.add(desc);
		lista.add(desc2);
		lista.add(desc3);
		lista.add(desc4);
		return Collections.unmodifiableList(lista);
	}

}
