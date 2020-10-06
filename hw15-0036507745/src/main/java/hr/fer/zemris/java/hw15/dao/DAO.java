package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * 
 * Interface representing the Data Access Object pattern.
 * 
 */

public interface DAO {

	/**
	 * Returns the blog entry with the given id.
	 * 
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Returns the blog user with the given nickname.
	 * 
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Returns the list of all registered blog users.
	 * 
	 * @throws DAOException
	 */
	public List<BlogUser> getAllUsers() throws DAOException;

	/**
	 * Inserts the given user into the database.
	 * 
	 * @throws DAOException
	 */
	public void insertUser(BlogUser user) throws DAOException;

	/**
	 * Inserts the given blog entry into the database.
	 * 
	 * @throws DAOException
	 */
	public void insertBlogEntry(BlogEntry entry) throws DAOException;

	/**
	 * Inserts the given comment into the database.
	 * 
	 * @throws DAOException
	 */
	public void insertComment(BlogComment comment) throws DAOException;

	/**
	 * Returns the list of all blog entries from the provided author.
	 * 
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntries(BlogUser author) throws DAOException;
}