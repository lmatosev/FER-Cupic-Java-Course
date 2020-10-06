package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * 
 * This class represents a component on which the {@link JVDraw} will draw all
 * geometrical objects.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373753751523181853L;

	/**
	 * supplier used to get the current tool
	 */
	private Supplier<Tool> tools;
	/**
	 * model used
	 */
	private DrawingModel model;

	/**
	 * Main constructor for this class
	 * 
	 * @param tools - supplier used for getting the current active tool
	 * @param model - model used
	 */
	public JDrawingCanvas(Supplier<Tool> tools, DrawingModel model) {

		this.tools = tools;
		this.model = model;

		this.model.addDrawingModelListener(this);

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				JDrawingCanvas.this.tools.get().mouseMoved(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				JDrawingCanvas.this.tools.get().mouseDragged(e);
			}
		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JDrawingCanvas.this.tools.get().mouseReleased(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JDrawingCanvas.this.tools.get().mousePressed(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JDrawingCanvas.this.tools.get().mouseClicked(e);
			}
		});
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 500);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);

		Insets ins = getInsets();

		g2d.setColor(Color.white);

		g2d.fillRect(ins.left, ins.top, this.getWidth(), this.getHeight());

		for (int i = 0; i < this.model.getSize(); i++) {

			GeometricalObject obj = this.model.getObject(i);
			obj.accept(painter);

		}

		this.tools.get().paint(g2d);
	}

}
