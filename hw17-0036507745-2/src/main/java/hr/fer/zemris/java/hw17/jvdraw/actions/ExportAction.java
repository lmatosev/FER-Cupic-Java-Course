package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;

/**
 * 
 * Action used for exporting the jvd as an image of chosen format.
 * 
 * 
 * 
 * @author Lovro Matošević
 *
 */
public class ExportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1364559229036326258L;

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
	public ExportAction(JVDraw jvdraw, DrawingModel model) {

		this.putValue(NAME, "Export");
		this.jvdraw = jvdraw;
		this.model = model;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Export");

		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("jpg files (*.jpg)", "jpg");
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png files (*.png)", "png");
		FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("gif files (*.gif)", "gif");

		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(jpgFilter);
		jfc.addChoosableFileFilter(pngFilter);
		jfc.addChoosableFileFilter(gifFilter);

		if (jfc.showSaveDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			String[] options = { "Ok" };
			JOptionPane.showOptionDialog(jvdraw, "Save cancelled", "Cancel", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			return;
		}

		Path path = jfc.getSelectedFile().toPath();

		String format = "";

		if (path.toString().contains(".")) {

			String[] pathSplit = path.toString().split(".");
			format = pathSplit[pathSplit.length - 1];

		} else {

			if (jfc.getFileFilter() == jpgFilter) {

				path = Paths.get(path.toString() + ".jpg");
				format = "jpg";

			} else if (jfc.getFileFilter() == pngFilter) {

				path = Paths.get(path.toString() + ".png");
				format = "png";

			} else {

				path = Paths.get(path.toString() + ".gif");
				format = "gif";

			}

		}

		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();

		for (int i = 0; i < this.model.getSize(); i++) {

			GeometricalObject obj = this.model.getObject(i);

			obj.accept(bbcalc);

		}

		Rectangle box = bbcalc.getBoundingBox();

		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = (Graphics2D) image.getGraphics();

		g.translate(-box.x, -box.y);

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

		for (int i = 0; i < this.model.getSize(); i++) {

			GeometricalObject obj = this.model.getObject(i);

			obj.accept(painter);

		}

		g.dispose();

		File file = path.toFile();

		try {
			ImageIO.write(image, format, file);
			String[] options = { "Ok" };
			JOptionPane.showOptionDialog(jvdraw, "Export finished", "Export", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
