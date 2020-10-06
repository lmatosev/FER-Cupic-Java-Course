package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * 
 * Class used to store connection towards the database to the
 * {@link ThreadLocal} object.
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connection for the current thread, or erases the entry from the map
	 * if the argument is <code>null</code>).
	 * 
	 * @param connection - connection towards the database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Returns the connection which the user can then use.
	 * 
	 * @return connection - connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}