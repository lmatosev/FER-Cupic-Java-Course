package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet used to redirect the request to the appropriate url.
 * 
 * @author Lovro Matošević
 *
 */
@WebServlet("/index.html")
public class IndexRedirectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7481793196545629403L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.sendRedirect("servleti/index.html");

	}
}
