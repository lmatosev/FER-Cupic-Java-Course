package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;

/**
 * 
 * Editor used for {@link FilledCircle} editing.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5092451748506994055L;

	/**
	 * text field used for setting circle center x coordinate
	 */
	private JTextField xCenter;
	/**
	 * text field used for setting circle center y coordinate
	 */
	private JTextField yCenter;
	/**
	 * text field used for setting the radius
	 */
	private JTextField radius;
	/**
	 * {@link JColorArea} used for setting the foreground color
	 */
	private JColorArea fgColorArea;
	/**
	 * {@link JColorArea} used for setting the background color
	 */
	private JColorArea bgColorArea;

	/**
	 * circle being edited
	 */
	private FilledCircle circle;

	/**
	 * Main constructor for this class.
	 * 
	 * @param circle - circle being edited
	 */
	public FilledCircleEditor(FilledCircle circle) {

		this.circle = circle;

		this.xCenter = new JTextField(5);
		this.yCenter = new JTextField(5);
		this.radius = new JTextField(5);
		this.fgColorArea = new JColorArea(Color.red);
		this.bgColorArea = new JColorArea(Color.blue);

		this.setLayout(new GridLayout(5, 2));

		this.add(new JLabel("Center x coordinate: "));
		this.add(xCenter);

		this.add(new JLabel("Center y coordinate: "));
		this.add(yCenter);

		this.add(new JLabel("Radius: "));
		this.add(radius);

		this.add(new JLabel("Circle edge color (click to choose): "));
		this.add(fgColorArea);

		this.add(new JLabel("Circle fill color (click to choose): "));
		this.add(bgColorArea);
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
		Color fgColor = this.fgColorArea.getCurrentColor();
		Color bgColor = this.bgColorArea.getCurrentColor();

		this.circle.setCenterX(centerX);
		this.circle.setCenterY(centerY);
		this.circle.setRadius(radius);
		this.circle.setFgColor(fgColor);
		this.circle.setBgColor(bgColor);

	}

}
