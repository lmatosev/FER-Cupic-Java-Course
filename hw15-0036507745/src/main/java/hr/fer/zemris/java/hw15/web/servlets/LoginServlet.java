package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.util.Hashing;

/**
 * 
 * Servlet used to process the login form.
 * 
 * @author Lovro Matošević
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7660108602987278469L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nickname = req.getParameter("nick");

		String password = req.getParameter("password");

		BlogUser user = DAOProvider.getDAO().getBlogUser(nickname);

		if (password == null) {
			req.getSession().setAttribute("message", "Password doesn't exist.");
		}

		else if (nickname == null) {
			req.getSession().setAttribute("message", "Nickname doesn't exist.");
		}

		else if (user == null) {
			req.getSession().setAttribute("message", "Nickname doesn't exist.");
		}

		else if (!Hashing.validatePassword(password, user.getPasswordHash())) {
			req.getSession().setAttribute("message", "Invalid password!");
			req.getSession().setAttribute("username", nickname);

		} else {

			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.firstName", user.getFirstName());
			req.getSession().setAttribute("current.user.lastName", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());

			req.getSession().setAttribute("message", "Successfully logged in!");
		}
		resp.sendRedirect("main");

	}
}
