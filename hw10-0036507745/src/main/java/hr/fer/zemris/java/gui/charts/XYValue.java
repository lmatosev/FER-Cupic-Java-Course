package hr.fer.zemris.java.gui.charts;

/**
 * 
 * Class used to store pairs of X-Y values.
 *
 */
public class XYValue {
	/**
	 * The x value stored
	 */
	private int x;
	/**
	 * The y value stored
	 */
	private int y;

	/**
	 * Constructor which accepts the x and y values.
	 * 
	 * @param x - x value to be stored
	 * @param y - y value to be stored
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
