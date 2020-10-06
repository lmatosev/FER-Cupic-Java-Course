package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * 
 * Interface toward the subsystem for data persistence.
 * 
 */
public interface DAO {

	/**
	 * Returns the list of all available polls.
	 * 
	 * @throws DAOException - in case of error
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Sets the vote for the poll option with the given id
	 * 
	 * @param id - id of the option
	 */
	public void addVote(int id);

	/**
	 * Returns a list of {@link PollOptions}.
	 * @param id 
	 * 
	 * @return options - list of {@link PollOptions}
	 */
	public List<PollOptions> getPollOptions(int id);

}