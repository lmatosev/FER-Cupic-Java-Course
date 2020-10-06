package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Editor used for {@link Line} editing.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class LineEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -584157283536272743L;

	/**
	 * text field used for setting the starting x coordinate
	 */
	private JTextField xStart;
	/**
	 * text field used for setting the starting y coordinate
	 */
	private JTextField yStart;
	/**
	 * text field used for setting the end x coordinate
	 */
	private JTextField xEnd;
	/**
	 * text field used for setting the end y coordinate
	 */
	private JTextField yEnd;
	/**
	 * {@link JColorArea} used for setting the color
	 */
	private JColorArea colorArea;

	/**
	 * Line being edited.
	 */
	private Line line;

	/**
	 * 
	 * Main constructor for this class.
	 * 
	 * @param line - line being edited
	 */
	public LineEditor(Line line) {

		this.line = line;

		JTextField xStart = new JTextField(5);
		JTextField yStart = new JTextField(5);
		JTextField xEnd = new JTextField(5);
		JTextField yEnd = new JTextField(5);
		JColorArea colorArea = new JColorArea(Color.red);

		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.colorArea = colorArea;

		this.setLayout(new GridLayout(5, 2));

		this.add(new JLabel("Starting x coordinate: "));
		this.add(xStart);

		this.add(new JLabel("Starting y coordinate: "));
		this.add(yStart);

		this.add(new JLabel("End x coordinate: "));
		this.add(xEnd);

		this.add(new JLabel("End y coordinate: "));
		this.add(yEnd);

		this.add(new JLabel("Line color (click to choose): "));
		this.add(colorArea);
	}

	@Override
	public void checkEditing() {

		try {

			int startingX = Integer.parseInt(this.xStart.getText());
			int startingY = Integer.parseInt(this.yStart.getText());
			int endingX = Integer.parseInt(this.xEnd.getText());
			int endingY = Integer.parseInt(this.yEnd.getText());

			if (startingX < 0 || startingX > 1000 || startingY < 0 || startingY > 1000 || endingX < 0 || endingX > 1000
					|| endingY < 0 || endingY > 1000) {

				throw new IllegalArgumentException("Input should be a valid coordinate in range (0,1000)");

			}

		} catch (NumberFormatException ex) {

			throw new NumberFormatException("Input should be a valid number.");

		}

	}

	@Override
	public void acceptEditing() {

		int startingX = Integer.parseInt(this.xStart.getText());
		int startingY = Integer.parseInt(this.yStart.getText());
		int endingX = Integer.parseInt(this.xEnd.getText());
		int endingY = Integer.parseInt(this.yEnd.getText());
		Color color = this.colorArea.getCurrentColor();

		this.line.setStartX(startingX);
		this.line.setStartY(startingY);
		this.line.setEndX(endingX);
		this.line.setEndY(endingY);
		this.line.setColor(color);

	}

}
