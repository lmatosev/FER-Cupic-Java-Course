package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * 
 * Tool used for painting certain objects when a mouse action occurs.
 * 
 * @author Lovro Matošević
 *
 */
public interface Tool {

	/**
	 * Execute this method when the mouse was pressed.
	 * 
	 * @param e - mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Execute this method when the mouse was released.
	 * 
	 * @param e - mouse event
	 */
	public void mouseReleased(MouseEvent e);

	public void mouseClicked(MouseEvent e);

	/**
	 * Execute this method when the mouse was moved.
	 * 
	 * @param e - mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Execute this method when the mouse was dragged.
	 * 
	 * @param e - mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method used for painting
	 * 
	 * @param g2d - graphics
	 */
	public void paint(Graphics2D g2d);

}
