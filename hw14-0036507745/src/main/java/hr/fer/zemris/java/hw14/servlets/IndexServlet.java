package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * 
 * Servlet used to get the list of available polls and show them in a separate
 * page.
 * 
 * @author Lovro Matošević
 *
 */
@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9217418812401202796L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DAO dao = DAOProvider.getDao();

		List<Poll> polls = dao.getPolls();

		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}

}
