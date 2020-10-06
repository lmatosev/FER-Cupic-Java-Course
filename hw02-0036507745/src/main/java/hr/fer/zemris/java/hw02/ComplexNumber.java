package hr.fer.zemris.java.hw02;

/**
 * Class that represents an unmodifiable complex number. Contains some basic
 * methods for complex number arithmetics. All methods which perform
 * modifications return a new instance of a complex number.
 * 
 * @author Lovro Matošević
 *
 */

public class ComplexNumber {

	private double realPart;
	private double imaginaryPart;

	/**
	 * Public constructor which accepts two double arguments.
	 * 
	 * @param real      - the real part of the complex number
	 * @param imaginary - the imaginary part of the complex number
	 */

	public ComplexNumber(double real, double imaginary) {

		this.realPart = real;
		this.imaginaryPart = imaginary;
	}

	/**
	 * Creates a complex number with the specified real part.
	 * 
	 * @param real - the real part of the new number
	 * @return ComplexNumber
	 */

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a complex number with the specified imaginary part.
	 * 
	 * @param imaginary - the imaginary part of the new number
	 * @return ComplexNumber
	 */

	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new complex number from the specified magnitude and angle.
	 * 
	 * @param magnitude - magnitude of the number
	 * @param angle     - angle of the number
	 * @return ComplexNumber
	 */

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude<0) {
			throw new IllegalArgumentException();
		}
		double newRealPart = magnitude * Math.cos(angle);
		double newImaginaryPart = magnitude * Math.sin(angle);
		return new ComplexNumber(newRealPart, newImaginaryPart);
	}

	/**
	 * Creates a new complex number by parsing the user input.
	 * 
	 * @param s - user input used for parsing
	 * @return ComplexNumber
	 */

	public static ComplexNumber parse(String s) {

		if (s.isBlank() == true) {
			throw new IllegalArgumentException("Invalid input.");
		}
		if (s.contains("+-") || s.contains("-+")) {
			throw new IllegalArgumentException("Invalid input.");
		}

		String inputModified = s;
		Double real, imaginary;
		char sign = '+';

		if (s.charAt(0) == '-' || s.charAt(0) == '+') {
			sign = s.charAt(0);
			inputModified = s.substring(1);
		}

		int indexPositive = inputModified.indexOf("+");
		int indexNegative = inputModified.indexOf("-");

		if (indexPositive > 0) {
			String[] inputSplit = inputModified.split("\\+");
			if (sign == '-') {
				real = -1 * Double.parseDouble(inputSplit[0]);
			} else {
				real = Double.parseDouble(inputSplit[0]);
			}
			inputSplit[1] = inputSplit[1].replace("i", "");
			if (inputSplit[1].isBlank()) {
				imaginary = 1.0;
			} else {
				imaginary = Double.parseDouble(inputSplit[1]);
			}

		} else if (indexNegative > 0) {
			String[] inputSplit = inputModified.split("\\-");
			if (sign == '-') {
				real = -1 * Double.parseDouble(inputSplit[0]);
			} else {
				real = Double.parseDouble(inputSplit[0]);
			}
			inputSplit[1] = inputSplit[1].replace("i", "");
			if (inputSplit[1].isBlank()) {
				imaginary = -1.0;
			} else {
				imaginary = -1 * Double.parseDouble(inputSplit[1]);
			}
		} else if (s.contains("i") == false) {
			real = Double.parseDouble(s);
			imaginary = (double) 0;
		} else {
			if (inputModified.equals("i")) {
				if (sign == '+') {
					imaginary = 1.0;
				} else {
					imaginary = -1.0;
				}
			} else {
				if(s.indexOf("i")!=s.length()-1) {
					throw new IllegalArgumentException();
				}
				inputModified=s.replace("i", "");
				imaginary = Double.parseDouble(inputModified);
			}
			real = (double) 0;
		}

		return new ComplexNumber(real, imaginary);

	}

	/**
	 * @return double - returns the real part of the complex number
	 */

	public double getReal() {
		return this.realPart;
	}

	/**
	 * 
	 * @return double - returns the imaginary part of the complex number
	 */

	public double getImaginary() {
		return this.imaginaryPart;
	}

	/**
	 * 
	 * @return double - returns the magnitude of the complex number
	 */

	public double getMagnitude() {
		double x = this.realPart;
		double y = this.imaginaryPart;
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * 
	 * @return double - returns the angle of the complex number
	 */
	public double getAngle() {
		double newAngle = Math.atan2(this.imaginaryPart, this.realPart);
		if (newAngle < 0) {
			return newAngle + 2 * Math.PI;
		} else {
			return newAngle;
		}
	}

	/**
	 * Instance method which adds two complex numbers and returns the result in form
	 * of a new complex number.
	 * 
	 * @param c - complex number that will be added to the current one
	 * @return ComplexNumber - the result of addition
	 */

	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = c.realPart + this.realPart;
		double newImaginaryPart = c.imaginaryPart + this.imaginaryPart;
		return new ComplexNumber(newRealPart, newImaginaryPart);
	}

	/**
	 * Instance method which subtracts two complex numbers and returns the result in
	 * form of a new complex number.
	 * 
	 * @param c complex number that will be subtracted from the current one
	 * @return ComplexNumber - the result of subtraction
	 */

	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = this.realPart - c.realPart;
		double newImaginaryPart = this.imaginaryPart - c.imaginaryPart;
		return new ComplexNumber(newRealPart, newImaginaryPart);
	}

	/**
	 * Instance method which multiplies two complex numbers and returns the result
	 * in form of a new complex number.
	 * 
	 * @param c complex number which the current one will be multiplied with
	 * @return ComplexNumber - the result of multiplication
	 */

	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newRealPart = this.realPart * c.realPart - this.imaginaryPart * c.imaginaryPart;
		double newImaginaryPart = this.realPart * c.imaginaryPart + this.imaginaryPart * c.realPart;
		return new ComplexNumber(newRealPart, newImaginaryPart);
	}

	/**
	 * Instance method which divides two complex numbers and returns the result in
	 * form of a new complex number.
	 * 
	 * @param c complex number that the current one will be divided with
	 * @return ComplexNumber - the result of division
	 */

	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		ComplexNumber temp = this.mul(new ComplexNumber(c.realPart, -c.imaginaryPart));
		double divisor = Math.pow(c.getMagnitude(), 2);
		return new ComplexNumber(temp.realPart / divisor, temp.imaginaryPart / divisor);
	}

	/**
	 * Calculates the nth power of the current complex number.
	 * 
	 * @param n - number that specifies which power should be calculated
	 * @return ComplexNumber - the result
	 * @throws IllegalArgumentException - if the specified power is less than 0
	 */

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double newMagnitude = Math.pow(this.getMagnitude(), n);
		double newAngle = this.getAngle() * n;
		return fromMagnitudeAndAngle(newMagnitude, newAngle);
	}

	/**
	 * Calculates the nth root of the current complex number.
	 * 
	 * @param n - number that specifies which root should be calculated
	 * @return ComplexNumber - the result
	 * @throws IllegalArgumentException - if the specified root provided by the user
	 *                                  is less or equal 0
	 */

	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		ComplexNumber[] returnList = new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			double newMagnitude = Math.pow(this.getMagnitude(), 1.0 / n);
			double newAngle = (this.getAngle() + 2 * i * Math.PI) / n;
			returnList[i] = fromMagnitudeAndAngle(newMagnitude, newAngle);
		}
		return returnList;
	}

	/**
	 * @return String - the string representation of the current number
	 */

	public String toString() {
		if (this.imaginaryPart == 0.0 && this.realPart == 0) {
			return "0";
		} else if (this.imaginaryPart == 0) {
			return Double.toString(this.realPart);
		} else if (this.realPart == 0) {
			return Double.toString(this.imaginaryPart)+"i";
		} else {
			return Double.toString(this.realPart)+Double.toString(this.imaginaryPart)+"i";
		}

	}

	/**
	 * @param o - Object that should be compared to this instance of the complex
	 *          number
	 * @return boolean - returns true if the provided object is equal to the current
	 *         number, and false if it is not.
	 */

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof ComplexNumber)) {
			return false;
		}
		ComplexNumber other = (ComplexNumber) o;
		if (Math.abs(this.realPart - other.realPart) < 1E-6
				&& Math.abs(this.imaginaryPart - other.imaginaryPart) < 1E-6) {
			return true;
		} else {
			return false;
		}
	}

}
