package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Visitor used for printing details for each {@link GeometricalObject} so it
 * can be saved.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class GeometricalObjectSaver implements GeometricalObjectVisitor {

	/**
	 * resulting string
	 */
	private String result;

	@Override
	public void visit(Line line) {

		StringBuilder sb = new StringBuilder();

		sb.append("LINE ").append(line.getStartX()).append(" ").append(line.getStartY()).append(" ")
				.append(line.getEndX()).append(" ").append(line.getEndY()).append(" ").append(line.getColor().getRed())
				.append(" ").append(line.getColor().getGreen()).append(" ").append(line.getColor().getBlue())
				.append("\n");

		this.result = sb.toString();

	}

	@Override
	public void visit(Circle circle) {

		StringBuilder sb = new StringBuilder();

		sb.append("CIRCLE ").append(circle.getCenterX()).append(" ").append(circle.getCenterY()).append(" ")
				.append(circle.getRadius()).append(" ").append(circle.getColor().getRed()).append(" ")
				.append(circle.getColor().getGreen()).append(" ").append(circle.getColor().getBlue()).append("\n");

		this.result = sb.toString();

	}

	@Override
	public void visit(FilledCircle filledCircle) {

		StringBuilder sb = new StringBuilder();

		sb.append("FCIRCLE ").append(filledCircle.getCenterX()).append(" ").append(filledCircle.getCenterY())
				.append(" ").append(filledCircle.getRadius()).append(" ").append(filledCircle.getBgColor().getRed())
				.append(" ").append(filledCircle.getBgColor().getGreen()).append(" ")
				.append(filledCircle.getBgColor().getBlue()).append(" ").append(filledCircle.getFgColor().getRed())
				.append(" ").append(filledCircle.getFgColor().getGreen()).append(" ")
				.append(filledCircle.getFgColor().getBlue()).append("\n");

		this.result = sb.toString();

	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

}
