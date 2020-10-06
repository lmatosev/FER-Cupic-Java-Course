package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectSaver;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Action used for saving the jvd file.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class SaveAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7236252903442892880L;

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
	
	public SaveAction(JVDraw jvdraw, DrawingModel model) {

		this.putValue(NAME, "Save");
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
		this.jvdraw = jvdraw;
		this.model = model;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Path path = this.jvdraw.getSavedPath();

		if (path == null) {

			SaveAsAction action = new SaveAsAction(this.jvdraw, this.model);
			action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

		} else {

			StringBuilder sb = new StringBuilder();

			GeometricalObjectSaver saver = new GeometricalObjectSaver();

			for (int i = 0; i < this.model.getSize(); i++) {

				GeometricalObject obj = this.model.getObject(i);
				obj.accept(saver);
				sb.append(saver.getResult());

			}

			try {
				Files.writeString(path, sb.toString());
				this.model.clearModifiedFlag();
				this.jvdraw.setSavedPath(path);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
