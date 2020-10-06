package hr.fer.zemris.java.hw14.model;

/**
 * 
 * Class used to store data about poll options.
 * 
 * @author Lovro Matošević
 *
 */
public class PollOptions {

	/**
	 * option id
	 */
	private int id;
	/**
	 * name of the option
	 */
	private String optionTitle;
	/**
	 * link of the option
	 */
	private String optionLink;
	/**
	 * id of the appropriate poll
	 */
	private int pollId;
	/**
	 * option vote count
	 */
	private int votesCount;

	/**
	 * Default constructor which sets fields to default values.
	 */
	public PollOptions() {

		this.id = 0;
		this.optionTitle = "";
		this.optionLink = "";
		this.pollId = 0;
		this.votesCount = 0;

	}

	/**
	 * Main constructor which accepts 5 arguments, poll option id, title, link, id
	 * of the parent poll and number of votes.
	 * 
	 * @param id          - poll option id
	 * @param optionTitle - option's title
	 * @param optionLink  - option link
	 * @param pollId      - id of the parent poll
	 * @param votesCount  - number of votes
	 */
	public PollOptions(int id, String optionTitle, String optionLink, int pollId, int votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollId = pollId;
		this.votesCount = votesCount;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return the pollId
	 */
	public int getPollId() {
		return pollId;
	}

	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	/**
	 * @return the votesCount
	 */
	public int getVotesCount() {
		return votesCount;
	}

	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}

}
