package searching.algorithms;

/**
 * 
 * Model of a transition. Stores a state and the cost it took to get to this
 * state.
 * 
 */
public class Transition<S> {
	/**
	 * Current state
	 */
	private S state;
	/**
	 * Current cost
	 */
	private double cost;

	/**
	 * Constructor which accepts a state and the cost.
	 * 
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return the state
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

}
