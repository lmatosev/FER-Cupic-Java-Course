package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import javax.swing.JLabel;

/**
 * 
 * Label used to track and show current foreground and background colors.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class ColorLabel extends JLabel implements ColorChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2901336777355177954L;

	/**
	 * provides the foreground color
	 */
	private IColorProvider fgColorProvider;
	/**
	 * provides the background color
	 */
	private IColorProvider bgColorProvider;
	/**
	 * foreground color
	 */
	private Color fgColor;
	/**
	 * background color
	 */
	private Color bgColor;

	/**
	 * Main constructor for this class.
	 * 
	 * 
	 * @param fgColorProvider - provider for foreground color
	 * @param bgColorProvider - provider for background color
	 */
	public ColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		this.fgColor = fgColorProvider.getCurrentColor();
		this.bgColor = bgColorProvider.getCurrentColor();

		this.fgColorProvider.addColorChangeListener(this);
		this.bgColorProvider.addColorChangeListener(this);

		this.adaptText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {

		if (source == fgColorProvider) {
			this.fgColor = newColor;
		} else {
			this.bgColor = newColor;
		}

		this.adaptText();

	}

	/**
	 * Updates the text so it shows the current selected colors.
	 */
	private void adaptText() {

		StringBuilder sb = new StringBuilder();

		sb.append("Foreground color: (").append(this.fgColor.getRed()).append(", ").append(this.fgColor.getGreen())
				.append(", ").append(this.fgColor.getBlue()).append("), background color: (")
				.append(this.bgColor.getRed()).append(", ").append(this.bgColor.getGreen()).append(", ")
				.append(this.bgColor.getBlue()).append(").");

		this.setText(sb.toString());

	}

}
