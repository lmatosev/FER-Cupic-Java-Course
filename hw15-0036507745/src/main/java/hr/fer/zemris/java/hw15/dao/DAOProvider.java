package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * 
 * Class used as a {@link DAO} provider.
 *
 */
public class DAOProvider {

	/**
	 * {@link DAO} used by this application.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Returns the singleton instance of {@link DAO}.
	 */
	public static DAO getDAO() {
		return dao;
	}

}