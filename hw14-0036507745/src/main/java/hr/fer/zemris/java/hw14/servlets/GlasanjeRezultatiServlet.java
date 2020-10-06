package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOptions;

@WebServlet("/servleti/glasanje-rezultati")

/**
 * 
 * Servlet used to determine the winners and collect results.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3042154876504834204L;

	/**
	 * Creates the list of band results and list of winner bands.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DAO dao = DAOProvider.getDao();

		int pollID = Integer.parseInt(req.getParameter("pollID"));

		List<PollOptions> options = dao.getPollOptions(pollID);
		List<PollOptions> winners = new ArrayList<>();

		int mostVotes = 0;

		for (var option : options) {

			if (option.getVotesCount() > mostVotes) {

				winners.clear();
				winners.add(option);
				mostVotes = option.getVotesCount();

			} else if (option.getVotesCount() == mostVotes) {

				winners.add(option);

			}

		}

		req.setAttribute("optionResults", options);
		req.setAttribute("optionWinners", winners);
		req.setAttribute("pollID", pollID);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp?pollID=" + pollID).forward(req, resp);

	}

}
