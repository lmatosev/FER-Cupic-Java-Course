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
 * Servlet used to process the register form.
 * 
 * @author Lovro Matošević
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431425937754721735L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String firstName = req.getParameter("firstName");

		String lastName = req.getParameter("lastName");

		String email = req.getParameter("email");

		String nickname = req.getParameter("nick");

		String password = req.getParameter("password");

		if (isValid(firstName, lastName, email, nickname, password, req)) {

			BlogUser author = new BlogUser();
			author.setFirstName(firstName);
			author.setLastName(lastName);
			author.setEmail(email);
			author.setNick(nickname);

			String passHashed = Hashing.generateHashedPassword(password);

			author.setPasswordHash(passHashed);

			DAOProvider.getDAO().insertUser(author);

			req.setAttribute("message", "Successfully registered.");

			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);

		} else {

			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);

		}

	}

	/**
	 * Method used to check if the provided form parameters are valid.
	 * 
	 * 
	 * @param firstName - user's first name
	 * @param lastName  - user's last name
	 * @param email     - user's email
	 * @param nickname  - user's nickname
	 * @param password  - user's password
	 * @param req       - servlet request
	 * @return result - true if the form is valid and false else
	 */
	private boolean isValid(String firstName, String lastName, String email, String nickname, String password,
			HttpServletRequest req) {

		if (firstName == null || lastName == null || email == null || nickname == null || password == null) {
			req.setAttribute("message", "Invalid input.");
			return false;
		}

		if (firstName.isEmpty()) {

			req.setAttribute("message", "Name must not be empty.");
			return false;

		} else if (lastName.isEmpty()) {
			req.setAttribute("message", "Last name must not be empty.");
			return false;
		} else if (email.isEmpty()) {
			req.setAttribute("message", "Email must not be empty.");
			return false;
		} else if (nickname.isEmpty()) {
			req.setAttribute("message", "Nickname must not be empty.");
			return false;
		}

		if (DAOProvider.getDAO().getBlogUser(nickname) != null) {
			req.setAttribute("message", "Nickname is taken.");
			return false;
		}

		return true;
	}

}
