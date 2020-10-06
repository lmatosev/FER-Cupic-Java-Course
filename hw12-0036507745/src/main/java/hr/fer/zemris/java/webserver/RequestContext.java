package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * This class represents the context of a certain request to the server. When
 * the write() method is first called, the header is generated. If user tries to
 * set a field which is a part of the header, after the header was generated, a
 * {@link RuntimeException} will be thrown.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class RequestContext {

	/**
	 * Stream used as an output stream
	 */
	private OutputStream outputStream;
	/**
	 * Charset used when writing
	 */
	private Charset charset;
	/**
	 * Encoding used
	 */
	private String encoding;
	/**
	 * Request status code
	 */
	private int statusCode;
	/**
	 * Request status text
	 */
	private String statusText;
	/**
	 * Mime type of the request
	 */
	private String mimeType;
	/**
	 * Length of the content being written
	 */
	private Long contentLength;
	/**
	 * A map of parameters
	 */
	private Map<String, String> parameters;
	/**
	 * A map of temporary parameters
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * A map of persistent parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List of cookies stored
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Flag indicating if the header was generated
	 */
	private boolean headerGenerated;
	/**
	 * Dispatcher which will be used
	 */
	private IDispatcher dispatcher;
	/**
	 * Session id of the request
	 */
	private String sid;

	/**
	 * The default constructor which sets all the fields to their default values.
	 */
	public RequestContext() {
		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.contentLength = null;
		this.headerGenerated = false;
		this.parameters = new HashMap<>();
		this.temporaryParameters = new HashMap<>();
		this.persistentParameters = new HashMap<>();
		this.outputCookies = new ArrayList<>();
	}

	/**
	 * 
	 * Main constructor which accepts a number of arguments.
	 * 
	 * @param outputStream         - output stream which will be used when writing
	 * @param parameters           - map of parameters
	 * @param persistentParameters - map of persistent parameters
	 * @param outputCookies        - list of output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this();
		Objects.requireNonNull(outputStream);

		this.outputStream = outputStream;

		if (parameters != null) {
			this.parameters.putAll(parameters);
		}

		if (persistentParameters != null) {
			this.persistentParameters.putAll(persistentParameters);
		}

		if (outputCookies != null) {
			this.outputCookies.addAll(outputCookies);
		}

		this.parameters = Collections.unmodifiableMap(this.parameters);

	}

	/**
	 * Constructor which includes the {@link IDispatcher} and a session id.
	 * 
	 * @param outputStream         - output stream used when writing
	 * @param parameters           - map of parameters
	 * @param persistentParameters - map of persistent parameters
	 * @param outputCookies        - list of output cookies
	 * @param temporaryParameters  - map of temporary parameters
	 * @param dispatcher           - dispatcher which will be used
	 * @param sid                  - session id
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {

		this(outputStream, parameters, persistentParameters, outputCookies);

		this.persistentParameters = persistentParameters;
		this.dispatcher = dispatcher;
		this.sid = sid;

	}

	/**
	 * Returns the parameter with the provided key.
	 * 
	 * @param name - key of the parameter
	 * @return value - the value of the parameter
	 */
	public String getParameter(String name) {
		String str = this.parameters.get(name);

		return str;
	}

	/**
	 * @return set - a set of all parameter names
	 */
	public Set<String> getParameterNames() {

		Set<String> names = new HashSet<>();

		for (String name : this.parameters.keySet()) {
			names.add(name);
		}

		return Collections.unmodifiableSet(names);
	}

	/**
	 * Returns the persistent parameter with the provided key.
	 * 
	 * @param name - key of the parameter
	 * @return value - the value of the parameter
	 */
	public String getPersistentParameter(String name) {
		String str = this.persistentParameters.get(name);

		return str;
	}

	/**
	 * @return set - a set of all persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {

		Set<String> names = new HashSet<>();

		for (String name : this.persistentParameters.values()) {
			names.add(name);
		}

		return Collections.unmodifiableSet(names);
	}

	/**
	 * Creates an entry based on the provided name and value and adds it to the map
	 * of persistent parameters.
	 * 
	 * @param name  - map key
	 * @param value - map value
	 */
	public void setPersistentParameter(String name, String value) {
		this.persistentParameters.put(name, value);
	}

	/**
	 * Removes the entry with the provided key from the map of persistent
	 * parameters.
	 * 
	 * @param name - map key
	 */
	public void removePersistentParameter(String name) {
		this.persistentParameters.remove(name);
	}

	/**
	 * Returns the temporary parameter with the provided key.
	 * 
	 * @param name - key of the parameter
	 * @return value - the value of the parameter
	 */
	public String getTemporaryParameter(String name) {
		String str = this.temporaryParameters.get(name);

		return str;
	}

	/**
	 * @return set - a set of all temporary parameter namess
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> names = new HashSet<>();

		for (String name : this.temporaryParameters.values()) {
			names.add(name);
		}

		return Collections.unmodifiableSet(names);
	}

	/**
	 * @return id - session id
	 */
	public String getSessionID() {
		return this.sid;
	}

	/**
	 * Creates an entry based on the provided name and value and adds it to the map
	 * of temporary parameters.
	 * 
	 * @param name  - map key
	 * @param value - map value
	 */
	public void setTemporaryParameter(String name, String value) {
		this.temporaryParameters.put(name, value);
	}

	/**
	 * Removes the entry with the provided key from the map of temporary parameters.
	 * 
	 * @param name - map key
	 */
	public void removeTemporaryParameter(String name) {
		this.temporaryParameters.remove(name);
	}

	/**
	 * Writes the provided byte[] to the output stream.
	 * 
	 * @param data - byte array which will be written to the output stream
	 * @return context - current context
	 * @throws IOException - exception thrown if there is an error while writing
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!this.headerGenerated) {
			this.createHeader();
		}

		this.outputStream.write(data);

		return this;
	}

	/**
	 * Writes the provided byte[] to the output stream with the provided offset and
	 * length.
	 * 
	 * @param data   - byte array which will be written to the output stream
	 * @param offset - data offset
	 * @param len    - data length
	 * @return context - current context
	 * @throws IOException - exception thrown if there is an error while writing
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!this.headerGenerated) {
			this.createHeader();
		}
		this.outputStream.write(data, offset, len);

		return this;
	}

	/**
	 * Writes the provided string to the output stream.
	 * 
	 * @param text - text that will be written to output
	 * @return context - current context
	 * @throws IOException - exception thrown if there is an error while writing
	 */
	public RequestContext write(String text) throws IOException {
		if (!this.headerGenerated) {
			this.createHeader();
		}

		this.outputStream.write(text.getBytes(this.charset));

		return this;
	}

	/**
	 * Helper method used to create and write the header.
	 */
	private void createHeader() {
		this.headerGenerated = true;
		this.charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(this.statusCode).append(" ").append(this.statusText).append("\r\n");

		sb.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=").append(this.encoding);
		}
		sb.append("\r\n");
		if (this.contentLength != null) {
			sb.append("Content-Length:").append(this.contentLength).append("\r\n");
		}

		for (RCCookie cookie : this.outputCookies) {
			sb.append("Set-Cookie: ").append(cookie.name).append("=").append(cookie.value);
			if (cookie.domain != null) {
				sb.append("; Domain=").append(cookie.domain);
			}
			if (cookie.path != null) {
				sb.append("; Path=").append(cookie.path);
			}
			if (cookie.maxAge != null) {
				sb.append("; Max-Age=").append(cookie.maxAge);
			}
			sb.append("; HttpOnly");
			sb.append("\r\n");
		}
		sb.append("\r\n");

		String header = sb.toString();
		byte[] bytes = header.getBytes(StandardCharsets.ISO_8859_1);

		try {
			this.outputStream.write(bytes);
		} catch (IOException e) {
			System.out.println("Error while writing. Header was not generated.");
			return;
		}

	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.encoding = encoding;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 */
	public void setContentLength(Long contentLength) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.contentLength = contentLength;
	}

	/**
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * 
	 * Class representing a cookie used by the {@link SmartHttpServer}.
	 *
	 */
	public static class RCCookie {

		/**
		 * Cookie name
		 */
		private String name;
		/**
		 * Cookie value
		 */
		private String value;
		/**
		 * Cookie domain
		 */
		private String domain;
		/**
		 * Cookie path
		 */
		private String path;
		/**
		 * Maximum age of the current cookie
		 */
		private Integer maxAge;

		/**
		 * Constructor which accepts and sets a number of different arguments.
		 * 
		 * @param name   - name of the cookie
		 * @param value  - cookie value
		 * @param maxAge - maximal age of this cookie
		 * @param domain - cookie domain
		 * @param path   - cookie path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}

	/**
	 * Adds the provided {@link RCCookie} to the internal list of cookies.
	 * 
	 * @param rcCookie - cookie to be added
	 * @throws RuntimeException - exception thrown if the header was generated
	 *                          before calling this method
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (this.headerGenerated) {
			throw new RuntimeException("Cannot set the parameter after already generating the header.");
		}
		this.outputCookies.add(rcCookie);
	}

}
