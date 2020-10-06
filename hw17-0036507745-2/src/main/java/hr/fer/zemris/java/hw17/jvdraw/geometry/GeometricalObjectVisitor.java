package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Class representing a visitor for {@link GeometricalObject}.
 * 
 * @author Lovro Matošević
 *
 */

public interface GeometricalObjectVisitor {

	/**
	 * Visits the provided line.
	 * 
	 * @param line - line being visited
	 */
	public abstract void visit(Line line);

	/**
	 * Visits the provided circle
	 * 
	 * @param circle - circle being visited
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visits the provided filled circle
	 * 
	 * @param filledCircle - circle being visited
	 */
	public abstract void visit(FilledCircle filledCircle);

}
