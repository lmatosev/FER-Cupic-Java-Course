package hr.fer.zemris.math;

/**
 * 
 * Represents a simple model of a 2-dimensional vector and contains some of the
 * basic vector operations, such as translation, rotation and scaling.
 * 
 * @author Lovro Matošević
 *
 */

public class Vector2D {
	private double x;
	private double y;

	/**
	 * A basic constructor for this class.
	 * 
	 * @param x - the x coordinate of the vector
	 * @param y - the y coordinate of the vector
	 */

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return double - the x coordinate of this vector
	 */

	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return double - the y coordinate of this vector
	 */

	public double getY() {
		return this.y;
	}

	/**
	 * Translates this vector by the given vector.
	 * 
	 * @param offset
	 */

	public void translate(Vector2D offset) {
		this.x = this.x + offset.getX();
		this.y = this.y + offset.getY();
	}

	/**
	 * Creates and returns a new vector which is the result of translation of the
	 * current vector and the given one.
	 * 
	 * @param offset
	 * @return Vector2D
	 */

	public Vector2D translated(Vector2D offset) {
		double xNew = this.x + offset.getX();
		double yNew = this.y + offset.getY();

		return new Vector2D(xNew, yNew);
	}

	/**
	 * Rotates this vector by the given angle.
	 * 
	 * @param angle - the angle of rotation
	 */
	public void rotate(double angle) {
		double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		this.x = newX;
		this.y = newY;
	}

	/**
	 * Creates and returns a new vector which is the result of rotation of this
	 * vector by the given angle.
	 * 
	 * @param angle
	 * @return Vector2D
	 */

	public Vector2D rotated(double angle) {
		double xNew = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double yNew = this.x * Math.sin(angle) + this.y * Math.cos(angle);

		return new Vector2D(xNew, yNew);
	}

	/**
	 * Scales the current vector by the given scaler.
	 * 
	 * @param scaler - the scaler to be used
	 */

	public void scale(double scaler) {
		this.x = this.x * scaler;
		this.y = this.y * scaler;
	}

	/**
	 * 
	 * Creates and returns a new vector which is the result of scaling the current
	 * vector by the given scaler.
	 * 
	 * @param scaler
	 * @return Vector2D
	 */

	public Vector2D scaled(double scaler) {
		double xNew = this.x * scaler;
		double yNew = this.y * scaler;

		return new Vector2D(xNew, yNew);
	}

	/**
	 * Returns a new vector with same attributes as the current one.
	 * 
	 * @return Vector2D
	 */

	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

}
