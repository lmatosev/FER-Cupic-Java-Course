package hr.fer.zemris.java.gui.calc;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * 
 * Class which represents a number-labeled button. Adds a digit to the provided
 * calculator after being activated.
 * 
 * @author Lovro Matošević
 *
 */
public class NumberButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2029757113077151120L;

	/**
	 * Constructor which initializes the button.
	 * 
	 * @param text - text to be shown on the button, should be a number
	 * @param calc - calculator which is being operated
	 */
	public NumberButton(String text, CalcModelImpl calc) {
		this.setText(text);
		this.setFont(this.getFont().deriveFont(30f));
		this.setPreferredSize(new Dimension(100, 50));
		this.addActionListener((l) -> {
			calc.insertDigit(Integer.parseInt(this.getText()));
		});
	}

}
