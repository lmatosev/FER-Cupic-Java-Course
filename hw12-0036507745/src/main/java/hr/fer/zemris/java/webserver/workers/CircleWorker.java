package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Draws a circle with
 * fixed size.
 * 
 * @author Lovro Matošević
 *
 */

public class CircleWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = bim.createGraphics();

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		context.setMimeType("image/png");
		try {
			ImageIO.write(bim, "png", bos);
			byte[] bytes = bos.toByteArray();
			context.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
