package hr.fer.zemris.math;

import java.text.DecimalFormat;

/**
 * 
 * Represents a simple model of a 3-dimensional vector and contains some of the
 * basic vector operations.
 * 
 * @author Lovro Matošević
 *
 */
public class Vector3 {
	/**
	 * The x value of the vector
	 */
	private double x;
	/**
	 * The y value of the vector
	 */
	private double y;
	/**
	 * The z value of the vector
	 */
	private double z;

	/**
	 * A basic constructor for this class.
	 * 
	 * @param x - the x coordinate of the vector
	 * @param y - the y coordinate of the vector
	 * @param z - the z coordinate of the vector
	 */

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the norm of the current vector.
	 * 
	 * @return norm - vector's norm value
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns a normalized version of this vector.
	 * 
	 * @return vector - the resulting vector
	 */
	public Vector3 normalized() {
		double norm = this.norm();
		double xNew = this.x / norm;
		double yNew = this.y / norm;
		double zNew = this.z / norm;
		return new Vector3(xNew, yNew, zNew);
	}

	/**
	 * Subtracts the provided vector from the current one.
	 * 
	 * @param other
	 * @return vector - the resulting vector
	 */
	public Vector3 sub(Vector3 other) {
		double xNew = this.x - other.x;
		double yNew = this.y - other.y;
		double zNew = this.z - other.z;
		return new Vector3(xNew, yNew, zNew);
	}

	/**
	 * Calculates and returns the dot product of this and the provided vector.
	 * 
	 * @param other
	 * @return number - the result of the dot product operation
	 */
	public double dot(Vector3 other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Calculates and returns the cross product of this and the provided vector.
	 * 
	 * @param other
	 * @return vector - the result of the cross product
	 */
	public Vector3 cross(Vector3 other) {
		double xNew = this.y * other.z - this.z * other.y;
		double yNew = this.z * other.x - this.x * other.z;
		double zNew = this.x * other.y - this.y * other.x;
		return new Vector3(xNew, yNew, zNew);
	}

	/**
	 * Computes and returns the cosine of the angle between this and the provided
	 * vector.
	 * 
	 * @param other
	 * @return cos - the resulting cosine
	 */
	public double cosAngle(Vector3 other) {
		double cos = (this.x * other.x + this.y * other.y + this.z * other.z) / (this.norm() * other.norm());
		return cos;
	}

	/**
	 * Creates and returns a new vector which is the result of translation of the
	 * current vector and the given one.
	 * 
	 * @param offset
	 * @return vector - a new Vector3
	 */

	public Vector3 add(Vector3 other) {
		double xNew = this.x + other.getX();
		double yNew = this.y + other.getY();
		double zNew = this.z + other.getZ();

		return new Vector3(xNew, yNew, zNew);
	}

	/**
	 * 
	 * Creates and returns a new vector which is the result of scaling the current
	 * vector by the given scaler.
	 * 
	 * @param scaler - factor to be scaled by
	 * @return Vector3 - result of scaling
	 */

	public Vector3 scale(double scaler) {
		double xNew = this.x * scaler;
		double yNew = this.y * scaler;
		double zNew = this.z * scaler;

		return new Vector3(xNew, yNew, zNew);
	}

	/**
	 * 
	 * @return x - the x coordinate of this vector
	 */

	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return y - the y coordinate of this vector
	 */

	public double getY() {
		return this.y;
	}

	/**
	 * 
	 * @return z - the z coordinate of this vector
	 */

	public double getZ() {
		return this.z;
	}

	/**
	 * Converts the vector to a double array with size of 3 elements, the x,y and z
	 * components.
	 * 
	 * @return array
	 */
	public double[] toArray() {
		double[] arr = new double[3];
		arr[0] = this.x;
		arr[1] = this.y;
		arr[2] = this.z;
		return arr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		DecimalFormat f=new DecimalFormat("#0.000000");
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(f.format(this.x)).append(", ");
		sb.append(f.format(this.y)).append(", ");
		sb.append(f.format(this.z)).append(")");
		return sb.toString();
	}

}
