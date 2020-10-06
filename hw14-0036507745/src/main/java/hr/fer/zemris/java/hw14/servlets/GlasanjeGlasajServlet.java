package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

@WebServlet("/servleti/glasanje-glasaj")

/**
 * 
 * Servlet used to add the vote to the curent total vote count.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1854830379738735007L;

	/**
	 * {@inheritDoc}
	 * 
	 * Reads from the file where the voting results are located and adds the vote or
	 * creates the file if it does not yet exist,
	 * 
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String idStr = req.getParameter("id");

		Integer id = Integer.parseInt(idStr);

		DAO dao = DAOProvider.getDao();

		dao.addVote(id);


		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+req.getParameter("pollID"));
	}

}
