package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;

@WebServlet("/servleti/glasanje")

/**
 * 
 * Servlet used to vote.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086931568597427727L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("pollID"));

		req.setAttribute("pollID", id);

		List<PollOptions> options = DAOProvider.getDao().getPollOptions(id);

		List<Poll> polls = DAOProvider.getDao().getPolls();

		for (var poll : polls) {

			if(poll.getId()==id){
				req.setAttribute("poll", poll);
			}
			
		}

		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
