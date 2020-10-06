package hr.fer.zemris.java.hw16.rest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.images.GalleryDB;
import hr.fer.zemris.java.hw16.images.GalleryImage;

/**
 * 
 * Class representing a RESTful web-service for the gallery web-application.
 * 
 * @author Lovro Matošević
 *
 */

@Path("/gallery")
public class Gallery {

	/**
	 * instance of {@link GalleryDB} used by this class
	 */
	private GalleryDB gallery;

	/**
	 * servlet context
	 */
	@Context
	private ServletContext servContext;

	/**
	 * Returns a list of tags
	 * 
	 * @return tags - a list of tags
	 */
	@GET
	@Produces("application/json")
	public Response getTagList() {

		if (this.gallery == null) {
			this.initializeGalleryDB();
		}

		List<String> tags = gallery.getAllTags();

		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * Returns a list of {@link GalleryImage} objects representing thumbnails for
	 * the given tag.
	 * 
	 * @param index - tag of the thumbnail
	 * @return thumbnails - a list of {@link GalleryImage} objects
	 */
	@Path("/{index}")
	@GET
	@Produces("application/json")
	public Response getThumbnailsForTag(@PathParam("index") String index) {

		if (this.gallery == null) {
			this.initializeGalleryDB();
		}

		List<GalleryImage> images = this.gallery.getImagesByTag(index);

		java.nio.file.Path thumbnailPath = Paths.get(this.gallery.getWebRoot() + "/thumbnails");

		if (!Files.isDirectory(thumbnailPath)) {
			this.createThumbnailFolder(thumbnailPath);
		}

		List<String> thumbnailURLS = this.processThumbnails(images);

		Gson gson = new Gson();
		String jsonText = gson.toJson(thumbnailURLS);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * Helper method used to create the /thumbnails folder
	 * 
	 * @param thumbnailPath - folder path
	 */
	private void createThumbnailFolder(java.nio.file.Path thumbnailPath) {
		
		try {
			Files.createDirectory(thumbnailPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns the {@link GalleryImage} located on the provided path.
	 * 
	 * @param index
	 * @return
	 */
	@Path("/img/{path}")
	@GET
	@Produces("application/json")
	public Response getGalleryImageFromPath(@PathParam("path") String index) {

		if (this.gallery == null) {
			this.initializeGalleryDB();
		}

		java.nio.file.Path path = Paths.get(index);

		java.nio.file.Path imagePath = this.gallery.getImagePathForName(path.getFileName().toString());

		String imageName = path.getFileName().toString();

		String description = this.gallery.getDescriptionForName(imageName);

		List<String> arr = new ArrayList<>();

		arr.add(imageName);
		arr.add(description);
		arr.add(imagePath.toString());

		Gson gson = new Gson();
		String jsonText = gson.toJson(arr);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * Used to create thumbnails if they don't already exist.
	 * 
	 * @param images - list of images
	 * @return thumbnails - list of URLs pointing to thumbnails
	 */
	private List<String> processThumbnails(List<GalleryImage> images) {

		List<String> thumbnailURLs = new ArrayList<>();

		for (var image : images) {

			String name = image.getName();

			java.nio.file.Path thumbnailPath = this.gallery.getThumbnailPathForName(name);

			if (!Files.exists(thumbnailPath)) {

				BufferedImage resized = this.resizeImage(image.getImage());

				try {
					ImageIO.write(resized, "jpg", thumbnailPath.toFile());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			thumbnailURLs.add(thumbnailPath.toString());
			thumbnailURLs.add(name);

		}

		return thumbnailURLs;
	}

	/**
	 * Helper method used to resize the given image to a thumbnail.
	 * 
	 * @param image - image to be resized
	 * @return thumbnail - thumbnail of the provided image
	 */
	private BufferedImage resizeImage(Image image) {
		BufferedImage tThumbImage = new BufferedImage(150, 150, BufferedImage.SCALE_DEFAULT);
		Graphics2D tGraphics2D = tThumbImage.createGraphics();
		tGraphics2D.fillRect(0, 0, 150, 150);
		tGraphics2D.drawImage(image, 0, 0, 150, 150, null);
		return tThumbImage;

	}

	/**
	 * Helper method used to initialize {@link GalleryDB}
	 */
	private void initializeGalleryDB() {

		java.nio.file.Path path = Paths.get(this.servContext.getRealPath(".") + "/WEB-INF/");

		this.gallery = new GalleryDB(path);

	}
}
