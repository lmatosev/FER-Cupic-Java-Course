package searching.algorithms;

/**
 * 
 * A node which stores a reference to the current state, the parent node and the
 * cost to get to the current node.
 *
 */
public class Node<S> {
	/**
	 * Stored state.
	 */
	private S state;
	/**
	 * Current node's parent.
	 */
	private Node<S> parent;
	/**
	 * Cost to get to the current state.
	 */
	private double cost;

	/**
	 * Constructor which accepts a parent node, a state and the cost.
	 * 
	 * @param parent
	 * @param state
	 * @param cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}

	/**
	 * 
	 * @return state - returns the stored state
	 */
	public S getState() {
		return this.state;
	}

	/**
	 * 
	 * @return cost -returns the stored cost
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * 
	 * @return parent - current node's parent
	 */
	public Node<S> getParent() {
		return this.parent;
	}
}
