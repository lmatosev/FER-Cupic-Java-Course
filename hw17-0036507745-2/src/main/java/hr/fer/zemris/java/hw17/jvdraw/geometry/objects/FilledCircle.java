package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * 
 * Class representing a model of a circle with filled background.
 * 
 * 
 * @author Lovro Matošević
 *
 */
public class FilledCircle extends GeometricalObject {

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
	 * foreground color
	 */
	private Color fgColor;
	/**
	 * background color
	 */
	private Color bgColor;

	/**
	 * Main constructor for this class
	 * 
	 * @param centerX - x coordinate of center
	 * @param centerY - y coordinate of center
	 * @param radius  - circle radius
	 * @param fgColor - foreground color
	 * @param bgColor - background color
	 */
	public FilledCircle(int centerX, int centerY, int radius, Color fgColor, Color bgColor) {

		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.fgColor = fgColor;
		this.bgColor = bgColor;

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		String rgb = Integer.toHexString(this.bgColor.getRGB());
		String hex = rgb.substring(2, rgb.length()).toUpperCase();

		sb.append("Filled circle (").append(this.centerX).append(",").append(this.centerY).append("), ")
				.append(this.radius).append(", #").append(hex);
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
	 * @return the fgColor
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * @param fgColor the fgColor to set
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		this.fire();

	}

	/**
	 * @return the bgColor
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor the bgColor to set
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		this.fire();

	}

}
