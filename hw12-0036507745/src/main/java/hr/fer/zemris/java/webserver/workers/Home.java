package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Displays the home page.
 * 
 * @author Lovro Matošević
 *
 */


public class Home implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		String bgColor = context.getPersistentParameter("bgcolor");
		

		if (bgColor != null) {
			context.setTemporaryParameter("background", bgColor);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}

		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");

	}

}
