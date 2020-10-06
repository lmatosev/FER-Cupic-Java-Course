package hr.fer.zemris.java.hw16.images;

import java.awt.Image;
import java.nio.file.Path;
import java.util.List;

/**
 * 
 * Class representing a model of a gallery image.
 * 
 * @author Lovro Matošević
 *
 */
public class GalleryImage {

	/**
	 * image description
	 */
	private String description;

	/**
	 * image tags
	 */
	private List<String> tags;

	/**
	 * image itself
	 */
	private Image image;

	/**
	 * image name
	 */
	private String name;

	/**
	 * image path
	 */
	private Path path;

	/**
	 * Default constructor
	 */
	public GalleryImage() {
		super();
	}

	/**
	 * Constructor which accepts and sets 4 arguments.
	 * 
	 * @param description - image description
	 * @param tags        - image tags
	 * @param image       - image itself
	 * @param name        - image name
	 */
	public GalleryImage(String description, List<String> tags, Image image, String name) {
		this.description = description;
		this.tags = tags;
		this.image = image;
		this.name = name;

	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		GalleryImage other = (GalleryImage) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

}
