package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Displays the obtained
 * parameters.
 * 
 * @author Lovro Matošević
 *
 */

public class EchoParams implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		context.setMimeType("text/html");

		try {
			context.write("<table>");
			context.write("<thead>");
			context.write("<tr><th>Name</th><th>Value</th></tr>");
			context.write("</thead><tbody>");

			for (String parameter : context.getParameterNames()) {

				context.write("<tr><td>" + parameter + "</td><td>" + context.getParameter(parameter) + "</td></tr>");
			}

			context.write("</tbody></table>");
			context.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
