package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * Class representing a model of a blog user.
 * 
 * @author Lovro Matošević
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/**
	 * user id
	 */
	private long id;
	/**
	 * user's first name
	 */
	private String firstName;
	/**
	 * user's last name
	 */
	private String lastName;
	/**
	 * user's nickname
	 */
	private String nick;
	/**
	 * user's email
	 */
	private String email;
	/**
	 * user's hashed password
	 */
	private String passwordHash;
	/**
	 * list of all blog entries from the user
	 */
	private List<BlogEntry> entries;

	/**
	 * @return the entries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	@Column(length = 50, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	@Column(length = 100, nullable = true, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the email
	 */
	@Column(length = 100, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	@Column(length = 200, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
