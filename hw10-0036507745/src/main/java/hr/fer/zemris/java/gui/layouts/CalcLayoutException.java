package hr.fer.zemris.java.gui.layouts;

/**
 * 
 * 
 * Exception that is thrown if there is an error in CalcLayout.
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructor which accepts a text.
	 * 
	 * @param text - text which is printed
	 */
	public CalcLayoutException(String text) {
		super(text);
	}

}
