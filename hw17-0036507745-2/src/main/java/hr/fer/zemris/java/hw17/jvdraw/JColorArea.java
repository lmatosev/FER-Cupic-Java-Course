package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * 
 * Class which represents a choosable color area.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * current color
	 */
	private Color selectedColor;

	/**
	 * list of registered listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Main constructor for this class.
	 * 
	 * @param selected - color to be set
	 */
	public JColorArea(Color selected) {
		this.selectedColor = selected;

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(JColorArea.this, "Choose a color", Color.red);

				JColorArea.this.setSelectedColor(color);
			}
		});
	}

	/**
	 * @param selectedColor the selectedColor to set
	 */
	public void setSelectedColor(Color selectedColor) {
		this.notifyListeners(this.selectedColor, selectedColor);
		this.selectedColor = selectedColor;
		this.repaint();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 372880729289895706L;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		this.listeners.remove(l);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Insets insets = getInsets();

		g2d.setColor(this.selectedColor);
		g2d.fillRect(insets.left, insets.top, this.getWidth(), this.getHeight());
	}

	/**
	 * Helper method used to notify all the listener that the current color was
	 * changed.
	 * 
	 * @param oldColor - old color
	 * @param newColor - new color
	 */
	private void notifyListeners(Color oldColor, Color newColor) {

		for (var listener : listeners) {

			listener.newColorSelected(this, oldColor, newColor);

		}

	}

}
