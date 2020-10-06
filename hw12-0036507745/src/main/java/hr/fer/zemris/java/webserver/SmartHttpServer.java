package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A simple, but funtional web-server. Constructor accepts the path to the
 * server configuration file.
 * 
 * @author Lovro Matošević
 *
 */
public class SmartHttpServer {

	@SuppressWarnings("unused")
	private String address;
	/**
	 * Server domain name
	 */
	private String domainName;
	/**
	 * Port used
	 */
	private int port;
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	/**
	 * Session timeout time
	 */
	private int sessionTimeout;
	/**
	 * Map containing mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Thread used by the server
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool which is used
	 */
	private ExecutorService threadPool;
	/**
	 * Path to the document root
	 */
	private Path documentRoot;
	/**
	 * Map of workers
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Map of sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * An instance of {@link Random} used for generating a session id.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Thread used for cleaning up all invalid sessions.
	 */
	private Thread sesClnThread;
	/**
	 * Boolean value indicating that server should be stopped.
	 */
	private volatile boolean stop = false;

	/**
	 * Starts the server.
	 * 
	 * @param args - accepts a single argument - path to the server configuration
	 *             file
	 */
	public static void main(String[] args) {
		String configPath = "config/server.properties";
		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Using default properties path.");
		} else {
			configPath = args[0];
		}

		@SuppressWarnings("unused")
		SmartHttpServer server = new SmartHttpServer(configPath);
	}

	/**
	 * 
	 * Class used to store information about sessions.
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session id
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Host name
		 */
		String host;
		/**
		 * Time until this session is valid
		 */
		long validUntil;
		/**
		 * Map used for storing session information.
		 */
		Map<String, String> map;

		/**
		 * Constructor which accepts and sets provided parameters.
		 * 
		 * @param sid        - session id
		 * @param host       - host name
		 * @param validUntil - time until the session is valid
		 * @param map        - map used for storing session information
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * Main constructor which accepts a single argument, path to the configuration
	 * file. Sets all the parameters to the values extracted from the configuration
	 * file.
	 * 
	 * @param configFileName
	 */
	public SmartHttpServer(String configFileName) {

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(Paths.get(configFileName).toFile()));
		} catch (IOException e) {
			System.out.println("Error reading properties file. Closing the server.");
			return;
		}

		this.address = properties.getProperty("server.address");
		this.domainName = properties.getProperty("server.domainName");
		this.port = Integer.parseInt(properties.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
		this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

		this.workersMap = new HashMap<>();

		Path mimePropertiesPath = Paths.get(properties.getProperty("server.mimeConfig"));

		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(new FileInputStream(mimePropertiesPath.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String propertyName : mimeProperties.stringPropertyNames()) {

			String mimeValue = mimeProperties.getProperty(propertyName);
			this.mimeTypes.put(propertyName, mimeValue);

		}

		Path workersPath = Paths.get(properties.getProperty("server.workers"));
		Properties workerProperties = new Properties();

		try {
			workerProperties.load(new FileInputStream(workersPath.toFile()));

			for (String propertyName : workerProperties.stringPropertyNames()) {

				String fqcn = workerProperties.getProperty(propertyName);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				workersMap.put(propertyName, iww);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.serverThread = new ServerThread();
		this.sesClnThread = new SessionCleanerThread();
		this.start();
	}

	/**
	 * Used for starting the server thread.
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
		}
		if (!sesClnThread.isAlive()) {
			sesClnThread.start();
		}
		this.threadPool = Executors.newFixedThreadPool(this.workerThreads);

	}

	/**
	 * Used for stopping the server thread.
	 */
	protected synchronized void stop() {
		this.stop = true;
		this.sesClnThread.interrupt();
		this.threadPool.shutdown();
	}

	/**
	 * 
	 * Thread used to remove invalid session every five minutes.
	 *
	 */
	private class SessionCleanerThread extends Thread {

		/**
		 * {@inheritDoc} Removes all invalid sessions from the session map every five
		 * minutes.
		 */
		@Override
		public void run() {

			while (!stop) {

				for (var key : sessions.keySet()) {
					SessionMapEntry entry = sessions.get(key);
					if (entry.validUntil - Instant.now().getEpochSecond() < 0) {
						sessions.remove(key);
					}
				}

				try {
					Thread.sleep(1000 * 60 * 5);
				} catch (InterruptedException e) {
				}

			}
		}

	}

	/**
	 * 
	 * Thread used by the server.
	 * 
	 * @author Lovro Matošević
	 *
	 */
	protected class ServerThread extends Thread {

		/**
		 * {@inheritDoc} Creates a new server socket and accepts requests until the
		 * thread is interrupted.
		 */
		@Override
		public void run() {

			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (!stop) {
				try {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.execute(cw);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * A worker which processes server requests.
	 * 
	 * @author Lovro Matošević
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * Socket used
		 */
		private Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		/**
		 * Version
		 */
		private String version;
		/**
		 * Method
		 */
		private String method;
		/**
		 * Host name
		 */
		private String host;
		/**
		 * Map of parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Map of temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Map of permanent parameters
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		/**
		 * List of cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session id
		 */
		@SuppressWarnings("unused")
		private String SID;
		/**
		 * Context used by this worker
		 */
		private RequestContext context;

		/**
		 * Constructor which accepts and sets the socket.
		 * 
		 * @param csocket - socket used
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			this.context = null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			try {

				this.istream = new PushbackInputStream(this.csocket.getInputStream());
				this.ostream = this.csocket.getOutputStream();
				List<String> request = readRequest();

				String[] firstLine = request.isEmpty() ? null : request.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				List<String> requestWithoutFirstLine = request;
				requestWithoutFirstLine.remove(0);
				this.analyzeHeaders(request);

				this.checkSession(request);

				String requestedPath = firstLine[1];

				String[] requestedPathSplit = requestedPath.split("\\?");

				if (requestedPathSplit.length == 2) {
					String paramString = requestedPathSplit[1];
					this.parseParameters(paramString);
				}

				String path = requestedPathSplit[0];

				this.internalDispatchRequest(path, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Helper method used to check if headers contain cookies. If not, generates a
		 * new session id and creates a new session.
		 * 
		 * @param headers - list of headers being analyzed
		 */
		private synchronized void checkSession(List<String> headers) {

			String sidCandidate = null;

			for (String line : headers) {

				if (line.startsWith("Cookie:")) {

					String cookie = line.substring(7).trim();
					String[] cookieSplit = cookie.split(";");
					for (String ck : cookieSplit) {

						String[] ckSplit = ck.split("=");
						if (ckSplit[0].equals("sid")) {
							sidCandidate = ckSplit[1];
						}

					}
				}
			}

			if (sidCandidate != null) {
				SessionMapEntry entry = sessions.get(sidCandidate);

				if (entry != null) {
					if (entry.host.equals(this.host)) {
						if (entry.validUntil - Instant.now().getEpochSecond() > 0) {
							entry.validUntil = Instant.now().getEpochSecond() + sessionTimeout;
							this.permParams = entry.map;
							return;
						}
						sessions.remove(sidCandidate);
					}

				}
			}

			StringBuilder sb = new StringBuilder();
			if (sidCandidate == null) {
				for (int i = 0; i < 20; i++) {
					sb.append((char) (sessionRandom.nextInt(26) + 65));
				}
			}
			long validUntil = Instant.now().getEpochSecond() + sessionTimeout;

			String sid = sidCandidate == null ? sb.toString() : sidCandidate;
			Map<String, String> map = new ConcurrentHashMap<>();

			SessionMapEntry entry = new SessionMapEntry(sid, host, validUntil, map);

			this.permParams = map;

			sessions.put(sid, entry);

			RCCookie cookie = new RCCookie("sid", sid, null, host, "/");

			outputCookies.add(cookie);

		}

		/**
		 * Helper method used to determine the mime type
		 * 
		 * @param fileName - file which possibly contains mime type
		 * @return mime - the mime type
		 */
		private String determineMimeType(String fileName) {

			String extension;

			int lastIndex = fileName.lastIndexOf(".");

			if (lastIndex < 0) {

				return "application/octet-stream";

			} else {

				extension = fileName.substring(lastIndex + 1);

			}

			return mimeTypes.get(extension.toString());
		}

		/**
		 * Helper method used to parse the given string and extract all the provided
		 * parameters.
		 * 
		 * @param paramString - string which contains parameters
		 */
		private void parseParameters(String paramString) {

			String[] paramStringSplit = paramString.split("&");

			for (String param : paramStringSplit) {

				String[] paramSplit = param.split("=");
				if (paramSplit.length != 2) {
					continue;
				} else {
					params.put(paramSplit[0], paramSplit[1]);
				}
			}

		}

		/**
		 * Helper method used to analyze headers and extract host name if it was
		 * provided.
		 * 
		 * @param request - request being analyzed
		 */
		private void analyzeHeaders(List<String> request) {

			boolean hostSet = false;

			for (String line : request) {

				if (line.startsWith("Host:")) {

					String hostName = line.substring(5);
					hostName = hostName.trim();
//					if (hostName.matches("[a-zA-Z]+:.*")) {
					this.host = hostName.split(":")[0];
//					} else {
//						this.host = hostName;
//					}
					hostSet = true;
				}

			}

			if (hostSet == false) {
				this.host = domainName;
			}

		}

		/**
		 * Helper method used for reading the request.
		 * 
		 * @return headers - list of headers extract from the request
		 */
		private List<String> readRequest() {
			List<String> returnList = new ArrayList<>();

			byte[] request = null;
			try {
				request = readBytes(istream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (request == null) {
				return returnList;
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);

			String currentLine = null;
			for (String s : requestStr.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						returnList.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				returnList.add(currentLine);
			}

			return returnList;
		}

		/**
		 * Helper method used to read all bytes from the input stream.
		 * 
		 * @param is - input stream
		 * @return bytes - the resulting byte array
		 * @throws IOException - exception thrown if there was an error while writing
		 */
		private byte[] readBytes(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Helper method used to inform the user of the error.
		 * 
		 * @param cos        - output stream
		 * @param statusCode - error status code
		 * @param statusText - error status text
		 * @throws IOException - exception thrown if there is an error while writing
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Dispatches the requests based on the provided path.
		 * 
		 * @param urlPath    - path used for dispatching the request
		 * @param directCall - flag which indicates if the call was direct or not
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (urlPath.startsWith("/private") && directCall == true) {

				this.sendError(ostream, 404, "Page not found.");
				return;

			}

			if (urlPath.matches("/ext/.+(?!/$)")) {
				Class<?> referenceToClass = this.getClass().getClassLoader()
						.loadClass("hr.fer.zemris.java.webserver.workers." + urlPath.split("/")[2]);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				if (context == null) {
					this.context = this.createRequestContext(ostream, params, permParams, outputCookies, tempParams,
							this, "");
				}
				iww.processRequest(context);
				csocket.close();
				return;

			}

			int last = urlPath.lastIndexOf("/");

			if (last != -1) {
				String mapping = urlPath.substring(last);
				IWebWorker worker;
				worker = workersMap.get(mapping);
				if (worker != null) {
					if (context == null) {
						this.context = this.createRequestContext(ostream, params, permParams, outputCookies, tempParams,
								this, "");
					}
					worker.processRequest(context);
					csocket.close();
					return;
				}
			}

			if (urlPath.endsWith("smscr")) {

				String result;
				Path path = documentRoot.resolve(urlPath.substring(1));
				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				result = sb.toString();

				SmartScriptParser parser = new SmartScriptParser(result);
				DocumentNode docBody = parser.getDocumentNode();
				if (context == null) {
					this.context = this.createRequestContext(ostream, params, permParams, outputCookies, tempParams,
							this, "");
				}
				SmartScriptEngine engine = new SmartScriptEngine(docBody, context);
				engine.execute();
			} else {
				Path requestedFile = documentRoot.resolve(urlPath.substring(1));
				if (!Files.isReadable(requestedFile) || !Files.isRegularFile(requestedFile)) {
					sendError(ostream, 403, "Forbidden access");
					return;
				}
				long len = 0;
				try {
					len = Files.size(requestedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String mime = determineMimeType(requestedFile.getFileName().toString());
				if (context == null) {
					this.context = this.createRequestContext(ostream, params, permParams, outputCookies, tempParams,
							this, "");
				}
				context.setMimeType(mime);
				context.setStatusCode(200);
				context.setContentLength(len);
				try (InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))) {

					byte[] buf = new byte[1024];
					while (true) {
						int r = is.read(buf);
						if (r < 1)
							break;
						context.write(buf, 0, r);
					}

					ostream.flush();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			csocket.close();
		}

		/**
		 * Creates a new requests context using the given parameters.
		 * 
		 * @param ostream2       - output stream
		 * @param params2        - map of parameters
		 * @param permParams2    - map of permanent parameters
		 * @param outputCookies2 - list of cookies
		 * @param tempParams2    - map of temporary parameters
		 * @param clientWorker   - client worker used
		 * @param sid            - session id
		 * @return context - the context which was created
		 */
		private RequestContext createRequestContext(OutputStream ostream2, Map<String, String> params2,
				Map<String, String> permParams2, List<RCCookie> outputCookies2, Map<String, String> tempParams2,
				ClientWorker clientWorker, String sid) {
			return new RequestContext(ostream2, params2, permParams2, outputCookies2, tempParams2, clientWorker, sid);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {

			internalDispatchRequest(urlPath, false);

		}

	}

}
