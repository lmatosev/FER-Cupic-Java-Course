package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Sets the background
 * color if it was provided as a parameter. Displays a link to the home page.
 * 
 * @author Lovro Matošević
 *
 */

public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		String bgcolor = context.getParameter("bgcolor");

		context.setMimeType("text/html");

		String message = "Background color was not updated.";

		if (bgcolor != null) {

			if (bgcolor.matches("[0123456789ABCDEFabcdef]*") && bgcolor.length() == 6) {

				message = "Background color was updated.";
				context.setPersistentParameter("bgcolor", bgcolor);

			}

		}

		try {
			context.write("<html><body>");

			context.write("<p>" + message + "</p>");
			context.write("<a href=/index2.html>Home</a>");

			context.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
