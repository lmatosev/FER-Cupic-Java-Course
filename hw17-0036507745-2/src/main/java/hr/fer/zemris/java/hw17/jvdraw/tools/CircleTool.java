package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;

public class CircleTool implements Tool {

	/**
	 * Model in which the object will be stored after drawing
	 */
	private DrawingModel model;
	/**
	 * Color provider used to get the object color
	 */
	private IColorProvider provider;
	/**
	 * Canvas in which the object will be shown after drawing
	 */
	private JDrawingCanvas canvas;
	/**
	 * Used for the drawing itself
	 */
	private GeometricalObjectPainter painter;

	/**
	 * x coordinate of the circle center
	 */
	private int centerX;
	/**
	 * y coordinate of the circle center
	 */
	private int centerY;
	/**
	 * circle radius
	 */
	private int radius;
	/**
	 * circle being drawn
	 */
	private Circle circle;
	/**
	 * value indicating if there was already a click
	 */
	private boolean pressed;

	/**
	 * Main constructor for this class
	 * 
	 * @param model    - {@link DrawingModel} used
	 * @param provider - {@link IColorProvider} used for color
	 * @param canvas   - {@link JDrawingCanvas} where the circle will be drawn
	 */
	public CircleTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {

		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
		this.pressed = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (pressed == false) {
			pressed = true;
			this.centerX = e.getX();
			this.centerY = e.getY();
			this.radius = 0;
			this.circle = new Circle(centerX, centerY, radius, this.provider.getCurrentColor());
		} else {

			pressed = false;
			this.radius = this.calculateDistance(centerX, centerY, e.getX(), e.getY());

			if (this.model.indexOf(circle) != -1) {
				this.model.remove(circle);
			}

			this.circle.setRadius(this.radius);
			this.circle.setColor(this.provider.getCurrentColor());
			this.model.add(circle);
			this.circle = null;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (pressed == true) {

			this.circle.setRadius(this.calculateDistance(centerX, centerY, e.getX(), e.getY()));
			this.canvas.repaint();

		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (pressed == true) {

			this.circle.setRadius(this.calculateDistance(centerX, centerY, e.getX(), e.getY()));
			this.canvas.repaint();

		}

	}

	@Override
	public void paint(Graphics2D g2d) {

		if (this.circle != null) {

			this.painter = new GeometricalObjectPainter(g2d);

			this.painter.visit(this.circle);
		}

	}

	/**
	 * Method used to calculate distance between two points.
	 * 
	 * @param x1 - x coordinate of first point
	 * @param y1 - y coordinate of first point
	 * @param x2 - x coordinate of second point
	 * @param y2 - y coordinate of second point
	 * @return
	 */
	private int calculateDistance(int x1, int y1, int x2, int y2) {

		return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y2, 2));

	}
}
