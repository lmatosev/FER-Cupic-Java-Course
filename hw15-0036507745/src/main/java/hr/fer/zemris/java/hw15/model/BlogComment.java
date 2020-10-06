package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * Class used as a model of a blog comment.
 * 
 * @author Lovro Matošević
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * comment id
	 */
	private Long id;
	/**
	 * corresponding blog entry
	 */
	private BlogEntry blogEntry;
	/**
	 * email of the user who wrote the comment
	 */
	private String usersEMail;
	/**
	 * comment message
	 */
	private String message;
	/**
	 * date which the comment was posted on
	 */
	private Date postedOn;

	/**
	 * @return id - comment id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the comment's id.
	 * 
	 * @param id - id to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return entry - comment's parent blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the comment's parent blog entry
	 * 
	 * @param entry - entry to be set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return email - email of the user who wrote the comment
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the email of the user who wrote the comment.
	 * 
	 * @param usersEMail - email to be set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * @return message - comment's message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the comment's message
	 * 
	 * @param message - message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return date - date on which the comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date which the comment was posted on
	 * 
	 * @param postedOn - date to be set
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}