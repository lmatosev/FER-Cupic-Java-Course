package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * 
 * Action used for exiting the program.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class ExitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941958177481603269L;

	/**
	 * jvdraw used
	 */
	private JVDraw jvdraw;
	/**
	 * drawing model used
	 */
	private DrawingModel model;

	/**
	 * Main constructor for this class.
	 * 
	 * @param jvdraw - jvdraw being saved
	 * @param model  - model used for saving
	 */

	public ExitAction(JVDraw jvdraw, DrawingModel model) {

		this.jvdraw = jvdraw;
		this.model = model;
		this.putValue(NAME, "Exit");
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt X"));

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (this.model.isModified()) {

			String[] options = { "Yes", "No", "Cancel" };

			int option = JOptionPane.showOptionDialog(this.jvdraw, "Save changes before exiting?", "Save",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (option == 0) {
				SaveAction save = new SaveAction(jvdraw, model);
				save.actionPerformed(new ActionEvent(this.jvdraw, ActionEvent.ACTION_PERFORMED, null));
			} else if (option == 2) {
				return;
			}

		}

		this.jvdraw.dispose();

	}

}
