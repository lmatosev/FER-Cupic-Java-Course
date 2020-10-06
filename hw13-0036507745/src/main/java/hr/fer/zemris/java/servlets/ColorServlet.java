package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * Servlet used to set the background color.
 * 
 * @author Lovro Matošević
 *
 */

@WebServlet("/setcolor")
public class ColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -698930090327892934L;

	/**
	 * {@inheritDoc}
	 * 
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = req.getParameter("color");
		
		HttpSession session = req.getSession();
	
		session.setAttribute("pickedBgCol", color);
		
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
