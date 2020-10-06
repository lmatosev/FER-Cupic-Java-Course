package hr.fer.zemris.java.hw15.dao;

/**
 * 
 * Exception thrown if there are any errors in {@link DAO}.
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Accepts a message and cause of the exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Accepts a message.
	 */
	public DAOException(String message) {
		super(message);
	}
}