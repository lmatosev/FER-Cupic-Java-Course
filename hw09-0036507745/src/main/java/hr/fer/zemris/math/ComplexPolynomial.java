package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class representing a complex polynomial expression.
 * 
 * @author Lovro Matošević
 *
 */
public class ComplexPolynomial {
	/**
	 * Complex polynomial factors.
	 */
	private List<Complex> factors;

	/**
	 * The main constructor which accepts multiple instances of Complex which
	 * represent polynomial factors.
	 * 
	 * @param factors - polynomial factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = new ArrayList<>();
		for (var factor : factors) {
			this.factors.add(factor);
		}
	}

	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return order - polynomial order
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Multiplies the current polynomial with the given one.
	 * 
	 * @param p - the polynomial to be multiplied by
	 * @return polynomial - the resulting polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {

		Complex[] arr = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.size(); i++) {
			for (int j = 0; j < p.factors.size(); j++) {
				arr[i + j] = arr[i + j].add(this.factors.get(i).multiply(p.factors.get(j)));
			}
		}
		return new ComplexPolynomial(arr);
	}

	/**
	 * Derives the current polynomial.
	 * 
	 * @return polynomial - polynomial which is the result of derivation
	 */
	public ComplexPolynomial derive() {

		Complex[] arr = new Complex[this.order()];

		for (int i = 1; i < this.factors.size(); i++) {
			Complex comp = this.factors.get(i);
			arr[i - 1] = new Complex(comp.getRe() * i, comp.getIm() * i);
		}

		return new ComplexPolynomial(arr);
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @return complex - the resulting complex number
	 */
	public Complex apply(Complex z) {

		Complex result = this.factors.get(0);

		for (int i = 1; i < this.factors.size(); i++) {
			result = result.add(this.factors.get(i).multiply(z.power(i)));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = factors.size() - 1; i > 0; i--) {
			sb.append(factors.get(i)).append("*z^").append(i).append("+");
		}

		sb.append(this.factors.get(0));
		return sb.toString();
	}
}
