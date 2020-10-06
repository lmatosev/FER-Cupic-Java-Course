package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;

public class FilledCircleTool implements Tool {

	/**
	 * Model in which the object will be stored after drawing
	 */
	private DrawingModel model;
	/**
	 * Color provider used to get the object background color
	 */
	private IColorProvider bgProvider;
	/**
	 * Color provider used to get the object foreground color
	 */
	private IColorProvider fgProvider;

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
	private FilledCircle filledCircle;
	/**
	 * value indicating if there already was a click
	 */
	private boolean pressed;

	/**
	 * Main constructor for this class.
	 * 
	 * @param model      - {@link DrawingModel} used
	 * @param bgProvider - {@link IColorProvider} used for background color
	 * @param fgProvider - {@link IColorProvider} used for foreground color
	 * @param canvas     - canvas where the circle will be drawn
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider bgProvider, IColorProvider fgProvider,
			JDrawingCanvas canvas) {

		this.model = model;
		this.bgProvider = bgProvider;
		this.fgProvider = fgProvider;
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
			this.filledCircle = new FilledCircle(centerX, centerY, radius, this.fgProvider.getCurrentColor(),
					this.bgProvider.getCurrentColor());
		} else {

			pressed = false;
			this.radius = this.calculateDistance(centerX, centerY, e.getX(), e.getY());

			if (this.model.indexOf(filledCircle) != -1) {
				this.model.remove(filledCircle);
			}

			this.filledCircle.setRadius(this.radius);
			this.filledCircle.setFgColor(this.fgProvider.getCurrentColor());
			this.filledCircle.setBgColor(this.bgProvider.getCurrentColor());
			this.model.add(filledCircle);

			this.filledCircle = null;
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

			this.filledCircle.setRadius(this.calculateDistance(centerX, centerY, e.getX(), e.getY()));
			this.canvas.repaint();

		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (pressed == true) {

			this.filledCircle.setRadius(this.calculateDistance(centerX, centerY, e.getX(), e.getY()));
			this.canvas.repaint();

		}

	}

	@Override
	public void paint(Graphics2D g2d) {

		if (this.filledCircle != null) {

			this.painter = new GeometricalObjectPainter(g2d);

			this.painter.visit(this.filledCircle);
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

		return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

	}
}
