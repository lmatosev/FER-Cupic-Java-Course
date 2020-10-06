package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * 
 * Servlet used to process various requests concerning blog entries.
 * 
 * @author Lovro Matošević
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorEntriesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2042586141459295508L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getPathInfo();

		String[] pathSplit = pathInfo.split("/");

		String nick = pathSplit[1];

		BlogUser author = DAOProvider.getDAO().getBlogUser(nick);

		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);

		req.setAttribute("entries", entries);
		req.setAttribute("author", author);

		if (pathSplit.length == 3) {

			String action = pathSplit[2];

			if (action.equals("new")) {

				this.newAction(req, resp);
				return;

			} else if (action.equals("edit")) {

				this.editAction(req, resp);
				return;

			} else {

				this.showEntry(req, resp, action);
				return;
			}

		}
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(req, resp);

	}

	/**
	 * Method used to show the {@link BlogEntry} with the provided id.
	 * 
	 * @param req  - servlet request
	 * @param resp - servlet response
	 * @param id   - entry id
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showEntry(HttpServletRequest req, HttpServletResponse resp, String id)
			throws ServletException, IOException {

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));

		req.setAttribute("entry", entry);

		req.getRequestDispatcher("/WEB-INF/pages/ShowEntryPage.jsp").forward(req, resp);

	}

	/**
	 * Action used to create a new {@link BlogEntry}.
	 * 
	 * @param req  - servlet request
	 * @param resp - servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/pages/EditEntryPage.jsp").forward(req, resp);

	}

	/**
	 * Action used for editing existing {@link BlogEntry}.
	 * 
	 * @param req  - serlvet request
	 * @param resp - servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");

		BlogEntry entry = (BlogEntry) DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));

		req.setAttribute("entry", entry);

		req.getRequestDispatcher("/WEB-INF/pages/EditEntryPage.jsp").forward(req, resp);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getPathInfo();

		req.setCharacterEncoding("UTF-8");

		String[] pathSplit = pathInfo.split("/");

		if (pathSplit.length == 3) {

			String action = pathSplit[2];

			if (action.equals("new")) {

				this.newActionPost(req, resp);
				return;

			} else if (action.equals("edit")) {

				this.editActionPost(req, resp);
				return;

			} else if (action.equals("comments")) {

				this.commentsPost(req, resp);
				return;
			}

		}

	}

	/**
	 * Method used to create a new comment from the parameters provided by the form.
	 * 
	 * @param req  - servlet request
	 * @param resp - servlet response
	 * @throws IOException
	 */
	private void commentsPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String message = req.getParameter("message");

		String eid = req.getParameter("entryId");

		String nick = req.getParameter("nick");

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(eid));

		BlogComment comment = new BlogComment();

		comment.setMessage(message);
		comment.setPostedOn(new Date());
		comment.setBlogEntry(entry);

		if (nick == null) {

			String email = req.getParameter("email");

			if (email == null || email.isEmpty() || !email.contains("@")) {

				req.getSession().setAttribute("message", "Email must be provided to leave a comment if you are not logged in!");
				resp.sendRedirect(
						req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + eid);
				return;
			}

			comment.setUsersEMail(email);

		} else {

			BlogUser user = DAOProvider.getDAO().getBlogUser(nick);

			comment.setUsersEMail(user.getEmail());

		}

		DAOProvider.getDAO().insertComment(comment);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + eid);

	}

	/**
	 * Method used to create a new {@link BlogEntry} from parameters provided in the
	 * form.
	 * 
	 * @param req  - servlet request
	 * @param resp - servlet response
	 * @throws IOException
	 */
	private void newActionPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String title = req.getParameter("title");

		String text = req.getParameter("text");

		String nick = req.getParameter("nick");

		BlogEntry entry = new BlogEntry();

		entry.setTitle(title);

		entry.setText(text);

		entry.setCreatedAt(new Date());

		BlogUser author = DAOProvider.getDAO().getBlogUser((String) req.getSession().getAttribute("current.user.nick"));

		entry.setCreator(author);

		DAOProvider.getDAO().insertBlogEntry(entry);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);

	}

	/**
	 * Method used to edit an existing {@link BlogEntry} with the parameters
	 * provided in the form.
	 * 
	 * @param req  - servlet request
	 * @param resp - servlet response
	 * @throws IOException
	 */
	private void editActionPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String id = req.getParameter("id");

		String nick = req.getParameter("nick");

		BlogEntry entry = (BlogEntry) DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));

		String title = req.getParameter("title");

		String text = req.getParameter("text");

		entry.setTitle(title);

		entry.setText(text);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick + "/" + id);

	}

}
