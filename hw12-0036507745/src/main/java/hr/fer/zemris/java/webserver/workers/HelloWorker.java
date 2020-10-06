package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Displays the current
 * time and prints the name if the user provided it as a parameter.
 * 
 * @author Lovro Matošević
 *
 */

public class HelloWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();

		context.setMimeType("text/html");

		String name = context.getParameter("name");
		try {
			context.write("<html><body>");
			context.write("<hl>Hello!!!</hl>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");
			if (name == null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			} else {
				context.write("<p>Your name has " + name.trim().length() + " letter.</p>");
			}
			context.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
