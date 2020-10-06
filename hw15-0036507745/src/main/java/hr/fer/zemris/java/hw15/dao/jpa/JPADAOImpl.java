package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * 
 * Implementation of the {@link DAO} interface.
 * 
 * @author Lovro Matošević
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {

		BlogUser user = null;
		try {
			user = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("select b from BlogUser as b where b.nick=:nickname").setParameter("nickname", nick)
					.getSingleResult();
		} catch (NoResultException ex) {
		}

		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser as b").getResultList();

		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertUser(BlogUser user) throws DAOException {

		JPAEMProvider.getEntityManager().persist(user);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser author) throws DAOException {

		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogEntry as b where b.creator=:author").setParameter("author", author)
				.getResultList();

		return entries;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}