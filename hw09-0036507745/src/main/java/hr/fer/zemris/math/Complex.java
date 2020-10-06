package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an unmodifiable complex number. Contains some basic
 * methods for complex number arithmetics. All methods which perform
 * modifications return a new instance of a complex number.
 * 
 * @author Lovro Matošević
 *
 */

public class Complex {
	/**
	 * The real part of the complex number.
	 */
	private double re;
	/**
	 * The imaginary part of the complex number.
	 */
	private double im;
	/**
	 * (0,i0)
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * (1,i0)
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * (-1,i0)
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * (0,i1)
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * (0,-i1.0)
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * The main constructor.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Constructor which accepts two double arguments.
	 * 
	 * @param real      - the real part of the complex number
	 * @param imaginary - the imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * 
	 * @return module - module of this complex number
	 */

	public double module() {
		double x = this.re;
		double y = this.im;
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Multiplies two complex numbers and returns the result in form of a new
	 * complex number.
	 * 
	 * @param c complex number which the current one will be multiplied with
	 * @return complex - the result of multiplication
	 */

	public Complex multiply(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = this.re * c.re - this.im * c.im;
		double newImaginaryPart = this.re * c.im + this.im * c.re;
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * Divides two complex numbers and returns the result in form of a new complex
	 * number.
	 * 
	 * @param c complex number that the current one will be divided with
	 * @return complex - the result of division
	 */

	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		Complex temp = this.multiply(new Complex(c.re, -c.im));
		double divisor = Math.pow(c.module(), 2);
		return new Complex(temp.re / divisor, temp.im / divisor);
	}

	/**
	 * Adds two complex numbers and returns the result in form of a new complex
	 * number.
	 * 
	 * @param c - complex number that will be added to the current one
	 * @return complex - the result of addition
	 */

	public Complex add(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = c.re + this.re;
		double newImaginaryPart = c.im + this.im;
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * Subtracts two complex numbers and returns the result in form of a new complex
	 * number.
	 * 
	 * @param c complex number that will be subtracted from the current one
	 * @return complex - the result of subtraction
	 */

	public Complex sub(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = this.re - c.re;
		double newImaginaryPart = this.im - c.im;
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * Negates the current complex number.
	 * 
	 * @return complex - a new complex number with real and imaginary parts equal to
	 *         the negative parts of the current number
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	/**
	 * Calculates the nth power of the current complex number.
	 * 
	 * @param n - number that specifies which power should be calculated
	 * @return complex - the result
	 * @throws IllegalArgumentException - if the specified power is less than 0
	 */

	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double newMagnitude = Math.pow(this.module(), n);
		double newAngle = this.getAngle() * n;
		return fromMagnitudeAndAngle(newMagnitude, newAngle);
	}

	/**
	 * Calculates the nth root of the current complex number.
	 * 
	 * @param n - number that specifies which root should be calculated
	 * @return complex - the result
	 * @throws IllegalArgumentException - if the specified root provided by the user
	 *                                  is less or equal 0
	 */

	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		List<Complex> returnList = new ArrayList<>(n);

		for (int i = 0; i < n; i++) {
			double newMagnitude = Math.pow(this.module(), 1.0 / n);
			double newAngle = (this.getAngle() + 2 * i * Math.PI) / n;
			returnList.add(fromMagnitudeAndAngle(newMagnitude, newAngle));
		}
		return returnList;
	}

	/**
	 * Returns the angle of the current complex number.
	 * 
	 * @return angle - the angle of this complex number
	 */
	private double getAngle() {
		double newAngle = Math.atan2(this.im, this.re);
		if (newAngle < 0) {
			return newAngle + 2 * Math.PI;
		} else {
			return newAngle;
		}
	}

	/**
	 * Creates a new complex number from the specified magnitude and angle.
	 * 
	 * @param magnitude - magnitude of the number
	 * @param angle     - angle of the number
	 * @return complex - the result
	 */

	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) {
			throw new IllegalArgumentException();
		}
		double newRealPart = magnitude * Math.cos(angle);
		double newImaginaryPart = magnitude * Math.sin(angle);
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(this.re);
		if (this.im >= 0) {
			sb.append("+");
		} else {
			sb.append("-");
		}
		sb.append("i").append(Math.abs(this.im)).append(")");
		return sb.toString();
	}

	/**
	 * @return the real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * @return the imaginary part
	 */
	public double getIm() {
		return im;
	}

}
