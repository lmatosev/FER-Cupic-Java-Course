package hr.fer.zemris.java.hw14.model;

/**
 * 
 * Class used to store Poll data.
 * 
 * @author Lovro Matošević
 *
 */
public class Poll {

	/**
	 * poll id
	 */
	private int id;
	/**
	 * poll title
	 */
	private String title;
	/**
	 * poll message
	 */
	private String message;

	/**
	 * Default constructor which sets the fields to the default values.
	 */
	public Poll() {
		this.id = 0;
		this.title = "";
		this.message = "";
	}

	/**
	 * Main constructor which accepts 3 parameters, poll id, title and message.
	 * 
	 * @param id - poll id
	 * @param title - poll's title
	 * @param message - poll message
	 */
	public Poll(int id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
