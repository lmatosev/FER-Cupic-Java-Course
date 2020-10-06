package hr.fer.zemris.java.servlets.util;

/**
 * 
 * Class used to stare data about sines and cosines of a certain angle.
 * 
 * @author Lovro Matošević
 *
 */

public class AngleData {

	/**
	 * Current angle
	 */
	private int angle;
	/**
	 * Sine of the given angle
	 */
	private double sin;
	/**
	 * Cosine of the given angle
	 */
	private double cos;

	/**
	 * The main constructor for this class which accepts 3 arguments.
	 * 
	 * @param angle - angle to be set
	 * @param sin - sine of the given angle
	 * @param cos - cosine of the given angle
	 */
	public AngleData(int angle, double sin, double cos) {

		this.angle = angle;
		this.sin = sin;
		this.cos = cos;

	}

	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * @return the sin
	 */
	public double getSin() {
		return sin;
	}

	/**
	 * @return the cos
	 */
	public double getCos() {
		return cos;
	}

}
