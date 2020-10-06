package hr.fer.zemris.java.hw14.dao;

/**
 * 
 * Exception thrown if there are any errors concerning the DAO.
 *
 */

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public DAOException() {
	}

	/**
	 * Constructor accepting 4 parameters.
	 * 
	 * @param message            - message to be printed
	 * @param cause              - case of the exception
	 * @param enableSuppression  - value which indicates whether to enable
	 *                           suppression or not
	 * @param writableStackTrace - value indicating if the stack trace is writable
	 *                           or not
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor accepting 2 parameters, a message and the cause.
	 * 
	 * @param message - message to be printed
	 * @param cause   - cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor which accepts a message.
	 * 
	 * @param message - message to be printed
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor which accepts the cause of the exception
	 * 
	 * @param cause - cause of the exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}