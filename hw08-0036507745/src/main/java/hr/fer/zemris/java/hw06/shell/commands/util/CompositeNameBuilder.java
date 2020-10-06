package hr.fer.zemris.java.hw06.shell.commands.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A name builder which stores multiple name builders and creates a string which
 * is the result of all the stored name builders.
 *
 */
public class CompositeNameBuilder implements NameBuilder {
	/**
	 * List of name builders which are stored.
	 */
	private List<NameBuilder> lista;

	/**
	 * Constructor for the name builder.
	 */
	public CompositeNameBuilder() {
		lista = new ArrayList<>();
	}

	/**
	 * Adds a name builder to the list of builders.
	 * 
	 * @param builder - name builder to be added
	 */
	public void addNameBuilder(NameBuilder builder) {
		lista.add(builder);
	}

	/**
	 * Executes the name builder. Effectively it executes all the builders which are
	 * stored.
	 */
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for (var builder : lista) {
			builder.execute(result, sb);
		}

	}

}
