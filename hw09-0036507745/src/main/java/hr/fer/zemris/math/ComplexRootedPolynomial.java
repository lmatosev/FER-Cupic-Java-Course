package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a polynomial with complex roots.
 * 
 * @author Lovro Matošević
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * List containing the constant and complex roots.
	 */
	private List<Complex> compList;

	/**
	 * The constructor which accepts a complex constant and complex roots.
	 * 
	 * @param constant - constant for the polynomial
	 * @param roots    - polynomial roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.compList = new ArrayList<>();
		this.compList.add(constant);
		for (var root : roots) {
			this.compList.add(root);
		}

	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z - the point in which the calculation is done
	 * @return complex - the complex number that was computed
	 */
	public Complex apply(Complex z) {
		Complex result = compList.get(0);
		for (int i = 1; i < compList.size(); i++) {
			result = result.multiply(z.sub(this.compList.get(i)));
		}
		return result;
	}

	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return ComplexPolynomial - the resulting polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(this.compList.get(1).negate(), Complex.ONE);

		for (int i = 2; i < compList.size(); i++) {
			result = result.multiply(new ComplexPolynomial(this.compList.get(i).negate(), Complex.ONE));
		}

		return result.multiply(new ComplexPolynomial(this.compList.get(0)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(compList.get(0));

		for (int i = 1; i < compList.size(); i++) {
			sb.append("*(z-").append(compList.get(i)).append(")");
		}

		return sb.toString();
	}

	/**
	 * Finds the index of the closest root for given complex number z that is within
	 * the provided treshold. If there is no such root, returns -1.
	 * 
	 * @param z        - complex number that is being checked
	 * @param treshold - treshold that is used
	 * @return index - the index which was found
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int min = -1;
		double minDist = treshold + 1;
		for (int i = 1; i < this.compList.size(); i++) {
			double dist = this.compList.get(i).sub(z).module();
			if (dist <= treshold && dist <= minDist) {
				minDist = dist;
				min = i - 1;
			}
		}

		return min;
	}

}
