package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.AngleData;

@WebServlet("/trigonometric")

/**
 * 
 * Servlet used to show all the sine and cosine values in the range defined by
 * "a" and "b" parameters.
 * 
 * @author Lovro Matošević
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7006478935354904350L;

	/**
	 * Creates a list of {@link AngleData} and dispatches the request.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String a = req.getParameter("a");
		String b = req.getParameter("b");

		Integer varA = 0;
		Integer varB = 360;

		try {
			if (a != null) {
				varA = Integer.valueOf(a);
			}
		} catch (Exception e) {
		}

		try {
			if (b != null) {
				varB = Integer.valueOf(b);
			}
		} catch (Exception e) {
		}

		if (varA > varB) {
			Integer temp = varA;
			varA = varB;
			varB = temp;
		}

		if (varB > varA + 720) {
			varB = varA + 720;
		}

		List<AngleData> data = new ArrayList<>();

		for (int i = varA; i <= varB; i++) {

			double sin = Math.sin(i);
			double cos = Math.cos(i);
			AngleData angData = new AngleData(i, sin, cos);
			data.add(angData);

		}

		req.setAttribute("angleData", data);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

	}

}
