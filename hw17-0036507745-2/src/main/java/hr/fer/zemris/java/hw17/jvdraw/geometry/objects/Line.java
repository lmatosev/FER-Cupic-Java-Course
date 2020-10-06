package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.LineEditor;

/**
 * 
 * Class representing a model of a line.
 * 
 * @author Lovro Matošević
 *
 */

public class Line extends GeometricalObject {

	/**
	 * starting x coordinate
	 */
	private int startX;
	/**
	 * starting y coordinate
	 */
	private int startY;
	/**
	 * ending x coordinate
	 */
	private int endX;
	/**
	 * ending y coordinate
	 */
	private int endY;
	/**
	 * line color
	 */
	private Color color;

	/**
	 * Main constructor for this class.
	 * 
	 * @param startX - starting x coordinate
	 * @param startY - starting y coordinate
	 * @param endX   - ending x coordinate
	 * @param endY   - ending y coordinate
	 * @param color  - line color
	 */
	public Line(int startX, int startY, int endX, int endY, Color color) {

		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;

	}

	
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Line (").append(startX).append(",").append(startY).append(")-(").append(endX).append(",")
				.append(endY).append(")");

		return sb.toString();
	}

	/**
	 * @return the startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @param startX the startX to set
	 */
	public void setStartX(int startX) {
		this.startX = startX;
		this.fire();
	}

	/**
	 * @return the startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @param startY the startY to set
	 */
	public void setStartY(int startY) {
		this.startY = startY;
		this.fire();
	}

	/**
	 * @return the endX
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * @param endX the endX to set
	 */
	public void setEndX(int endX) {
		this.endX = endX;
		this.fire();
	}

	/**
	 * @return the endY
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * @param endY the endY to set
	 */
	public void setEndY(int endY) {
		this.endY = endY;
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
