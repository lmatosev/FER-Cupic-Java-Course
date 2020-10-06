package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * An implementation of the {@link IWebWorker} interface. Calculates the sum of
 * two provided parameters ("a" and "b") and based on the number parity sets an
 * image as a temporary parameter.
 * 
 * @author Lovro Matošević
 *
 */

public class SumWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {

		int a = 1;
		int b = 2;

		try {

			String strA = context.getParameter("a");
			if (strA != null) {
				a = Integer.parseInt(strA);
			}

		} catch (NumberFormatException ex) {

		}

		try {

			String strB = context.getParameter("b");
			if (strB != null) {
				b = Integer.parseInt(strB);
			}

		} catch (NumberFormatException ex) {

		}

		String result = String.valueOf(a + b);
		context.setTemporaryParameter("zbroj", result);
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));

		int whichImage = (a + b) % 2;

		context.setTemporaryParameter("imgName", whichImage == 0 ? "image1.png" : "image2.png");

		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");

	}

}
