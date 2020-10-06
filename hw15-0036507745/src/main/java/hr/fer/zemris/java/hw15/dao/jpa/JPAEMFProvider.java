package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * Entity manager factory provider.
 *
 */
public class JPAEMFProvider {

	/**
	 * factory which provides {@link EntityManager}
	 */
	public static EntityManagerFactory emf;

	/**
	 * @return emf - stored {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the {@link EntityManagerFactory}
	 * 
	 * @param emf - {@link EntityManagerFactory} to be set
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}