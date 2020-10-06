package hr.fer.zemris.java.custom.collections;

/**
 * This class represents an exception that will be thrown if a specific stack function was 
 * called on an empty stack.
 * 
 * @author Lovro Matošević
 *
 */

public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default constructor
	 */
	
	public EmptyStackException() {
		super();
	}
	
	/**
	 * The constructor which accepts a string.
	 * 
	 * @param message - string that should be printed
	 */
	
	public EmptyStackException(String message) {
		super(message);
	}
	
}
