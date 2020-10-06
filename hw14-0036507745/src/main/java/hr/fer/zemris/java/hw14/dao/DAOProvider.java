package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * 
 * Singleton class which knows what to return as access service provider to the
 * subsystem for data persistence.
 *
 */
public class DAOProvider {

	/**
	 * Dao singleton.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Returns the singleton instance of {@link DAO}.
	 * 
	 * @return dao - object which encapsulates the access to the data persistence
	 *         layer
	 */
	public static DAO getDao() {
		return dao;
	}

}