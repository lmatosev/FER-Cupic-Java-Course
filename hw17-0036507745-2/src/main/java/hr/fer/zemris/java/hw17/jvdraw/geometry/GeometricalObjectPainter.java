package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * This class represents a visitor used for painting provided the provided {@link GeometricalObject}.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * graphics used for painting
	 */
	private Graphics2D g2d;

	/**
	 * Main constructor for this class.
	 * 
	 * @param g2d - graphics used for painting
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {

		g2d.setColor(line.getColor());

		g2d.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

	}

	@Override
	public void visit(Circle circle) {

		g2d.setColor(circle.getColor());

		g2d.drawOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(),
				circle.getRadius() * 2, circle.getRadius() * 2);

	}

	@Override
	public void visit(FilledCircle filledCircle) {

		g2d.setColor(filledCircle.getBgColor());

		g2d.fillOval(filledCircle.getCenterX() - filledCircle.getRadius(),
				filledCircle.getCenterY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
				filledCircle.getRadius() * 2);

		g2d.setColor(filledCircle.getFgColor());

		g2d.drawOval(filledCircle.getCenterX() - filledCircle.getRadius(),
				filledCircle.getCenterY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
				filledCircle.getRadius() * 2);

	}

}
