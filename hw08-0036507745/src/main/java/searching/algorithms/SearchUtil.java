package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class containing bfs and bfvs search algorithms.
 * 
 * @author Lovro Matošević
 *
 */
public class SearchUtil {

	/**
	 * An implementation of the bfs searching algorithm.
	 * 
	 * @param s0         - starting state
	 * @param process    - used for accepting a state
	 * @param succ       - used for getting a list of neighbors
	 * @param acceptable - used to test if the state is acceptable
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));

		while (!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			for (var v : succ.apply(ni.getState())) {
				S sj = v.getState();
				double cj = v.getCost();
				zaIstraziti.addLast(new Node<S>(ni, sj, ni.getCost() + cj));
			}

		}
		return null;
	}

	/**
	 * An implementation of the bfvs search.
	 * 
	 * @param s0         - starting state
	 * @param process    - used for accepting a state
	 * @param succ       - used for getting a list of neighbors
	 * @param acceptable - used to test if the state is acceptable
	 */
	public static <S> Node<S> bfvs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));

		Set<S> posjeceno = new HashSet<>();
		posjeceno.add(s0.get());
		while (!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> djeca = succ.apply(ni.getState());
			for (var v : djeca) {
				if (!posjeceno.contains(v.getState())) {
					S sj = v.getState();
					double cj = v.getCost();
					zaIstraziti.addLast(new Node<S>(ni, sj, ni.getCost() + cj));
					posjeceno.add(sj);
				}
			}

		}
		return null;
	}
}
