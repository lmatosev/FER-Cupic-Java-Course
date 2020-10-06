package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;

/**
 * 
 * Editor used for {@link Circle} editing.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577323691682324028L;

	/**
	 * text field used for setting circle center x coordinate
	 */
	private JTextField xCenter;
	/**
	 * text field used for setting circle center y coordinate
	 */
	private JTextField yCenter;
	/**
	 * text field used for setting circle radius
	 */
	private JTextField radius;
	/**
	 * {@link JColorArea} used for setting the circle color
	 */
	private JColorArea colorArea;

	/**
	 * circle being edited
	 */
	private Circle circle;

	/**
	 * Main constructor for this class.
	 * 
	 * @param circle - circle being edited
	 */
	public CircleEditor(Circle circle) {

		this.circle = circle;

		JColorArea colorArea = new JColorArea(Color.red);

		this.xCenter = new JTextField(5);
		this.yCenter = new JTextField(5);
		this.radius = new JTextField(5);
		this.colorArea = colorArea;

		this.setLayout(new GridLayout(4, 2));

		this.add(new JLabel("Center x coordinate: "));
		this.add(xCenter);

		this.add(new JLabel("Center y coordinate: "));
		this.add(yCenter);

		this.add(new JLabel("Radius: "));
		this.add(radius);

		this.add(new JLabel("Circle color (click to choose): "));
		this.add(colorArea);
	}

	@Override
	public void checkEditing() {

		try {

			int centerX = Integer.parseInt(this.xCenter.getText());
			int centerY = Integer.parseInt(this.yCenter.getText());
			int radius = Integer.parseInt(this.radius.getText());

			if (centerX < 0 || centerX > 1000 || centerY < 0 || centerY > 1000 || radius > 400) {

				throw new IllegalArgumentException(
						"Input should be a valid coordinate in range (0,1000) for coordinates and less than 400 for radius.");

			}

		} catch (NumberFormatException ex) {

			throw new NumberFormatException("Input should be a valid number.");

		}

	}

	@Override
	public void acceptEditing() {

		int centerX = Integer.parseInt(this.xCenter.getText());
		int centerY = Integer.parseInt(this.yCenter.getText());
		int radius = Integer.parseInt(this.radius.getText());
		Color color = this.colorArea.getCurrentColor();

		this.circle.setCenterX(centerX);
		this.circle.setCenterY(centerY);
		this.circle.setRadius(radius);
		this.circle.setColor(color);

	}

}
