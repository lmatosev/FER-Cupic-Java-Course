package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Class used for calculating the bounding box of the image.
 * 
 * @author Lovro Matošević
 *
 */

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * minimal x value
	 */
	private int xMin;
	/**
	 * maximal x value
	 */
	private int xMax;
	/**
	 * minimal y value
	 */
	private int yMin;
	/**
	 * maximal y value
	 */
	private int yMax;

	/**
	 * Main constructor which initializes all the values.
	 */
	public GeometricalObjectBBCalculator() {

		this.xMin = 100000;
		this.yMin = 100000;
		this.xMax = 0;
		this.yMax = 0;

	}

	@Override
	public void visit(Line line) {

		int lineXMin = Math.min(line.getStartX(), line.getEndX());
		int lineXMax = Math.max(line.getStartX(), line.getEndX());
		int lineYMin = Math.min(line.getStartY(), line.getEndY());
		int lineYMax = Math.max(line.getStartY(), line.getEndY());

		this.xMin = Math.min(lineXMin, this.xMin);
		this.xMax = Math.max(lineXMax, this.xMax);
		this.yMin = Math.min(lineYMin, this.yMin);
		this.yMax = Math.max(lineYMax, this.yMax);

	}

	@Override
	public void visit(Circle circle) {

		int circleX = circle.getCenterX();
		int circleY = circle.getCenterY();
		int radius = circle.getRadius();

		this.calculateCircle(circleX, circleY, radius);

	}

	@Override
	public void visit(FilledCircle filledCircle) {

		int circleX = filledCircle.getCenterX();
		int circleY = filledCircle.getCenterY();
		int radius = filledCircle.getRadius();

		this.calculateCircle(circleX, circleY, radius);

	}

	/**
	 * Helper method used to calculate circle maximum and minimum values for x and
	 * y.
	 * 
	 * @param circleX - circle center x coordinate
	 * @param circleY - circle center y coordinate
	 * @param radius  - circle radius
	 */
	private void calculateCircle(int circleX, int circleY, int radius) {

		int circleXMin = circleX - radius;
		int circleXMax = circleX + radius;
		int circleYMin = circleY - radius;
		int circleYMax = circleY + radius;

		this.xMin = Math.min(circleXMin, this.xMin);
		this.xMax = Math.max(circleXMax, this.xMax);
		this.yMin = Math.min(circleYMin, this.yMin);
		this.yMax = Math.max(circleYMax, this.yMax);

	}

	/**
	 * Returns the bounding box.
	 */
	public Rectangle getBoundingBox() {

		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);

	}

}
