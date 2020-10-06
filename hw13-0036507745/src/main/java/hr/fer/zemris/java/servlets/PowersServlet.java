package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet("/powers")

/**
 * 
 * Servlet used to create an xls file containing powers of numbers depending on
 * the defined parameters.
 * 
 * @author Lovro Matošević
 *
 */
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7738727532186064606L;

	/**
	 * {@inheritDoc}
	 * 
	 * Creates an xls file depending on the given parameters.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");

		if (a == null || b == null || n == null) {
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		int varA = 0;
		int varB = 0;
		int varN = 0;

		try {

			varA = Integer.valueOf(a);
			varB = Integer.valueOf(b);
			varN = Integer.valueOf(n);

		} catch (Exception e) {
		}

		if (varA < -100 || varA > 100 || varB < -100 || varB > 100 || varN < 1 || varN > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}


		@SuppressWarnings("resource")
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 0; i < varN; i++) {

			HSSFSheet sheet = hwb.createSheet("new sheet " + i);

			for (int j = varA; j <= varB; j++) {

				HSSFRow rowhead = sheet.createRow((short) j);

				rowhead.createCell((short) 0).setCellValue(j);
				rowhead.createCell((short) 1).setCellValue(Math.pow(j, i));

			}

		}

		OutputStream os = resp.getOutputStream();

		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

		hwb.write(os);
	}

}
