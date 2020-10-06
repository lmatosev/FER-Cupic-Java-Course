package hr.fer.zemris.java.servlets.util;

/**
 * 
 * Class used to store band data.
 * 
 * @author Lovro Matošević
 *
 */

public class BandData {

	/**
	 * Id of the band
	 */
	private String id;
	/**
	 * Band name
	 */
	private String name;
	/**
	 * URL to the band's representative song
	 */
	private String link;
	/**
	 * Number of votes the band has
	 */
	private int votes;

	/**
	 * The main constructor which accepts id, name and a link.
	 * 
	 * @param id - band id
	 * @param name - band name
	 * @param link - link to the representative song
	 */
	public BandData(String id, String name, String link) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.votes=0;
	}

	/**
	 * Constructor which accepts id, name, link and number of votes
	 * 
	 * @param id . band id
	 * @param name - band name
	 * @param link - link to the representative song
	 * @param votes - number of votes this band has
	 */
	public BandData(String id, String name, String link, int votes) {
		this(id, name, link);
		this.votes = votes;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	

}
