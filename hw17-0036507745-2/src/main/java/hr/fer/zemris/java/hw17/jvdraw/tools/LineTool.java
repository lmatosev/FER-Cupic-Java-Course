package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Class used for drawing lines.
 * 
 * @author Lovro Matošević
 *
 */
public class LineTool implements Tool {

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
	 * 
	 */
	private Line line;

	/**
	 * starting x coordinate
	 */
	private int startX;
	/**
	 * starting y coordinate
	 */
	private int startY;
	/**
	 * ending x coordinate
	 */
	private int endX;
	/**
	 * ending y coordinate
	 */
	private int endY;

	/**
	 * value indicating if there was already a click
	 */
	private boolean pressed;

	/**
	 * Main constructor for this class.
	 * 
	 * @param model - {@link DrawingModel} used
	 * @param provider - {@link IColorProvider} used for coloring the line
	 * @param canvas - {@link JDrawingCanvas} where the line will be drawn
	 */
	public LineTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {

		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
		this.pressed = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (pressed == false) {
			pressed = true;
			this.startX = e.getX();
			this.startY = e.getY();
			this.line = new Line(startX, startY, startX, startY, this.provider.getCurrentColor());

		} else {

			pressed = false;
			this.endX = e.getX();
			this.endY = e.getY();

			if (this.model.indexOf(line) != -1) {
				this.model.remove(line);
			}

			this.line.setEndX(this.endX);
			this.line.setEndY(this.endY);
			this.line.setColor(this.provider.getCurrentColor());
			this.model.add(line);
			
			this.line=null;

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

			this.line.setEndX(e.getX());
			this.line.setEndY(e.getY());
			this.canvas.repaint();

		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (pressed == true) {

			this.line.setEndX(e.getX());
			this.line.setEndY(e.getY());
			this.canvas.repaint();

		}
		
	}

	@Override
	public void paint(Graphics2D g2d) {

		if (this.line != null) {

			this.painter = new GeometricalObjectPainter(g2d);

			this.painter.visit(this.line);
		}

	}

}
