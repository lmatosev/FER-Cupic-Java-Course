package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * Class used as a model of a blog entry.
 * 
 * @author Lovro Matošević
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * blog entry's id
	 */
	private Long id;
	/**
	 * list of comments which are written on this entry
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * date which the entry was created on
	 */
	private Date createdAt;
	/**
	 * date which the entry was last modified on
	 */
	private Date lastModifiedAt;
	/**
	 * entry's title
	 */
	private String title;
	/**
	 * entry text
	 */
	private String text;
	/**
	 * blog user which created the entry
	 */
	private BlogUser creator;

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
	 * @return comments - list of all comments located on this blog entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments on this blog entry
	 * 
	 * @param comments - list of comments to be set
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return date - date on which the entry was created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return date - date on which the comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the date on which the entry was last modified on.
	 * 
	 * @param lastModifiedAt - date to be set
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * @return title - title of the entry
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the blog entry.
	 * 
	 * @param title - title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return text - text written in this entry
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the blog entry.
	 * 
	 * @param text - text to be set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the creator
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * @param creator - the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Sets the date which the entry was created at.
	 * 
	 * @param createdAt - date to be set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}