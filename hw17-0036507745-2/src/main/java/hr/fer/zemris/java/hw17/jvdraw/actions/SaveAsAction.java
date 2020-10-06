package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectSaver;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Action used for saving the jvd file to the user chosen location.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class SaveAsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7153371315126446203L;

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
	public SaveAsAction(JVDraw jvdraw, DrawingModel model) {

		this.putValue(NAME, "Save as");
		this.jvdraw = jvdraw;
		this.model = model;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt S"));

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save as");

		FileNameExtensionFilter jvdFilter = new FileNameExtensionFilter("jvd files (*.jvd)", "jvd");

		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(jvdFilter);

		if (jfc.showSaveDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			String[] options = { "Ok" };
			JOptionPane.showOptionDialog(jvdraw, "Save cancelled", "Cancel", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			return;
		}

		Path path = jfc.getSelectedFile().toPath();

		if (!path.toString().endsWith(".jvd")) {

			path = Paths.get(path.toString() + ".jvd");

		}

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
