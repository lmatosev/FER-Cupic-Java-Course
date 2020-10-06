package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * 
 * Action used for opening saved .jvd files.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class OpenAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8648835424325469093L;

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
	public OpenAction(JVDraw jvdraw, DrawingModel model) {

		this.putValue(NAME, "Open");
		this.jvdraw = jvdraw;
		this.model = model;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Open");

		FileNameExtensionFilter jvdFilter = new FileNameExtensionFilter("jvd files (*.jvd)", "jvd");

		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(jvdFilter);

		if (jfc.showOpenDialog(this.jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path path = jfc.getSelectedFile().toPath();

		if (!Files.isReadable(path)) {

			String[] options = { "Ok" };

			JOptionPane.showOptionDialog(this.jvdraw, "Error", "mau", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()));) {

			String line = "";

			this.model.clear();

			while ((line = br.readLine()) != null) {

				String[] lineSplit = line.split("\\s+");

				GeometricalObject obj;

				if (lineSplit[0].equals("LINE")) {

					obj = parseLine(lineSplit);

				} else if (lineSplit[0].equals("CIRCLE")) {

					obj = parseCircle(lineSplit);

				} else {

					obj = parseFilledCircle(lineSplit);

				}

				this.model.add(obj);

			}

			this.jvdraw.setSavedPath(path);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private GeometricalObject parseCircle(String[] lineSplit) {

		int x0 = Integer.parseInt(lineSplit[1]);
		int y0 = Integer.parseInt(lineSplit[2]);
		int radius = Integer.parseInt(lineSplit[3]);

		int red = Integer.parseInt(lineSplit[4]);
		int green = Integer.parseInt(lineSplit[5]);
		int blue = Integer.parseInt(lineSplit[6]);

		Color color = new Color(red, green, blue);

		return new Circle(x0, y0, radius, color);
	}

	private GeometricalObject parseFilledCircle(String[] lineSplit) {

		int x0 = Integer.parseInt(lineSplit[1]);
		int y0 = Integer.parseInt(lineSplit[2]);
		int radius = Integer.parseInt(lineSplit[3]);

		int redBg = Integer.parseInt(lineSplit[4]);
		int greenBg = Integer.parseInt(lineSplit[5]);
		int blueBg = Integer.parseInt(lineSplit[6]);

		int redFg = Integer.parseInt(lineSplit[7]);
		int greenFg = Integer.parseInt(lineSplit[8]);
		int blueFg = Integer.parseInt(lineSplit[9]);

		Color colorBg = new Color(redBg, greenBg, blueBg);

		Color colorFg = new Color(redFg, greenFg, blueFg);

		return new FilledCircle(x0, y0, radius, colorFg, colorBg);
	}

	private GeometricalObject parseLine(String[] lineSplit) {

		int x0 = Integer.parseInt(lineSplit[1]);
		int y0 = Integer.parseInt(lineSplit[2]);
		int x1 = Integer.parseInt(lineSplit[3]);
		int y1 = Integer.parseInt(lineSplit[4]);

		int red = Integer.parseInt(lineSplit[5]);
		int green = Integer.parseInt(lineSplit[6]);
		int blue = Integer.parseInt(lineSplit[7]);

		Color color = new Color(red, green, blue);

		return new Line(x0, y0, x1, y1, color);
	}

}
