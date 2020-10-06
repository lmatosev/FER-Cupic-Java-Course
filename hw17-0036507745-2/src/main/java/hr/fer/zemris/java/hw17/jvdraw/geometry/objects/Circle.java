package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * 
 * Class representing a model of a circle.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class Circle extends GeometricalObject {

	/**
	 * x coordinate of circle center
	 */
	private int centerX;
	/**
	 * y coordinate of circle center
	 */
	private int centerY;
	/**
	 * circle radius
	 */
	private int radius;
	/**
	 * circle color
	 */
	private Color color;

	/**
	 * Main constructor for this class.
	 * 
	 * 
	 * @param centerX - x coordinate of circle center
	 * @param centerY - y coordinate of circle center
	 * @param radius  - circle radius
	 * @param color   - circle color
	 */
	public Circle(int centerX, int centerY, int radius, Color color) {

		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);

	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Circle (").append(this.centerX).append(",").append(this.centerY).append("), ").append(this.radius);

		return sb.toString();
	}

	/**
	 * @return the centerX
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * @param centerX the centerX to set
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
		this.fire();

	}

	/**
	 * @return the centerY
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * @param centerY the centerY to set
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
		this.fire();

	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		this.fire();

	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
		this.fire();

	}

}
