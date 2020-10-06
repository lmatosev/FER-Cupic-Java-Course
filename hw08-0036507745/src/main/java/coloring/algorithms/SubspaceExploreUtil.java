package coloring.algorithms;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class which contains bfs, dfs and bfvs search methods.
 * 
 * @author Lovro Matošević
 *
 */
public class SubspaceExploreUtil {

	/**
	 * An implementation of the bfs search.
	 * 
	 * @param <S>        - state
	 * @param s0         - starting state
	 * @param process    - used for accepting a state
	 * @param succ       - used for getting a list of neighbors
	 * @param acceptable - used to test if the state is acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			for (var e : succ.apply(si)) {
				zaIstraziti.addLast(e);
			}
		}
	}

	/**
	 * An implementation of the dfs search.
	 * 
	 * @param <S>        - state
	 * @param s0         - starting state
	 * @param process    - used for accepting a state
	 * @param succ       - used for getting a list of neighbors
	 * @param acceptable - used to test if the state is acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			zaIstraziti.addAll(0, succ.apply(si));
		}

	}

	/**
	 * An implementation of the bfsv search.
	 * 
	 * @param <S>        - state
	 * @param s0         - starting state
	 * @param process    - used for accepting a state
	 * @param succ       - used for getting a list of neighbors
	 * @param acceptable - used to test if the state is acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());
		Set<S> posjeceno = new HashSet<>();
		posjeceno.add(s0.get());

		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			List<S> djeca = succ.apply(si);
			djeca.removeAll(posjeceno);
			for (var e : djeca) {
				zaIstraziti.addLast(e);
			}
			posjeceno.addAll(djeca);
		}

	}
}
