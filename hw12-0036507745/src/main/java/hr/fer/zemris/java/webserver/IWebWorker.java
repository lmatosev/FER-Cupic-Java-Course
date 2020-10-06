package hr.fer.zemris.java.webserver;

/**
 * 
 * Model of a web worker which is able to process requests.
 *
 */

public interface IWebWorker {

	/**
	 * Processes the provided request.
	 * 
	 * @param context - context which is used for processing the request
	 * @throws Exception 
	 */
	public void processRequest(RequestContext context) throws Exception;

}
