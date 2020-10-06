package hr.fer.zemris.java.hw16.images;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * Class representing a gallery database. Contains some basic methods required
 * for the gallery to work.
 * 
 * @author Lovro Matošević
 *
 */

public class GalleryDB {

	/**
	 * list of gallery images stored in this database
	 */
	private List<GalleryImage> images;

	private Path webRoot;

	/**
	 * Main constructor which initializes the database.
	 */
	public GalleryDB(Path webRoot) {
		this.webRoot=webRoot;
		this.images = new ArrayList<>();
		this.loadDB();
	}

	/**
	 * Helper method used to load the images into the database.
	 */
	private void loadDB() {

		Path descriptorPath = Paths.get(this.webRoot+"/opisnik.txt");

		List<String> lines = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(descriptorPath.toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); i += 3) {

			String name = lines.get(i);
			String description = lines.get(i + 1);
			String tags = lines.get(i + 2);

			String[] tagsSplit = tags.split(",");

			List<String> tagList = new ArrayList<>();

			for (var tag : tagsSplit) {

				tagList.add(tag.trim());

			}

			BufferedImage picture = null;

			Path imagePath = this.getImagePathForName(name);
			try {
				picture = ImageIO.read(imagePath.toFile());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			GalleryImage image = new GalleryImage(description, tagList, picture, name);

			this.images.add(image);

		}

	}

	/**
	 * Returns the number of images stored.
	 * 
	 * @return size - number of stored images
	 */
	public int numberOfImages() {

		return this.images.size();

	}

	/**
	 * Returns a list of {@link GalleryImage} with the given tag.
	 * 
	 * @param tag - provided tag
	 * @return images - list of images containing the provided tag
	 */
	public List<GalleryImage> getImagesByTag(String tag) {

		List<GalleryImage> returnList = new ArrayList<>();

		for (var image : images) {

			if (image.getTags().contains(tag)) {
				returnList.add(image);
			}

		}

		return returnList;
	}

	/**
	 * Returns a list of all tags contained in the database.
	 * 
	 * @return tags - list of all tags
	 */
	public List<String> getAllTags() {

		List<String> resultTags = new ArrayList<>();

		for (var image : images) {

			for (var tag : image.getTags()) {

				if (!resultTags.contains(tag)) {
					resultTags.add(tag);
				}

			}

		}

		return resultTags;
	}

	/**
	 * Returns the description for the image with the provided name. Name, of course
	 * is a unique property of an image.
	 * 
	 * @param name - image name
	 * @return description - description of the image with the provided name or null
	 *         if there is no image with the given name
	 */
	public String getDescriptionForName(String name) {

		for (var image : this.images) {

			if (image.getName().equals(name)) {
				return image.getDescription();
			}

		}

		return null;

	}

	/**
	 * Returns the path of the thumbnail of the image with the given name.
	 * 
	 * @param name - image name
	 * @return path - path to the thumbnail of the image with the provided name
	 */
	public Path getThumbnailPathForName(String name) {

		return Paths.get(this.webRoot+"/thumbnails/" + name);

	}

	/**
	 * Returns the path of the image with the given name.
	 * 
	 * @param name - image name
	 * @return path - path to the image
	 */
	public Path getImagePathForName(String name) {

		return Paths.get(this.webRoot+"/slike/" + name);

	}

	public Path getWebRoot() {
		return this.webRoot;
	}

	public void setWebRoot(Path newWebRoot) {
		this.webRoot = newWebRoot;
	}

}
