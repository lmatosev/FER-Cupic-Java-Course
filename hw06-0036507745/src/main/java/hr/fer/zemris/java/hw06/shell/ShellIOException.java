package hr.fer.zemris.java.hw06.shell;

/**
 * 
 * Exception used for MyShell when there is an error while reading or writing to
 * user.
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
