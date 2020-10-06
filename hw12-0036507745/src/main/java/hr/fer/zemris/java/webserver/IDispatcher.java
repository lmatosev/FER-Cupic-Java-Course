package hr.fer.zemris.java.webserver;

/**
 * 
 * Interface which represents a model of a dispatcher, used to dispatch
 * requests.
 *
 */

public interface IDispatcher {

	/**
	 * 
	 * Dispatches the request.
	 * 
	 * @param urlPath - path to the file
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;

}
