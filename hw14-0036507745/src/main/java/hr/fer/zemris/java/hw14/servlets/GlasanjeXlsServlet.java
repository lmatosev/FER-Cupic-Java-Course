package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOptions;

@WebServlet("/servleti/glasanje-xls")

/**
 * 
 * Servlet used to create an xls file containing voting results.
 * 
 * @author Lovro Matošević
 *
 */
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6037088280259593935L;

	/**
	 * Creates an xls file and writes it to output.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("pollID"));
		
		List<PollOptions> options = DAOProvider.getDao().getPollOptions(id);

		@SuppressWarnings("resource")

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow((short) 0);

		rowhead.createCell((short) 0).setCellValue("Opcija");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");

		for (int i = 1; i < options.size() + 1; i++) {

			rowhead = sheet.createRow((short) i);

			PollOptions option = options.get(i - 1);

			rowhead.createCell((short) 0).setCellValue(option.getOptionTitle());
			rowhead.createCell((short) 1).setCellValue(option.getVotesCount());

		}

		OutputStream os = resp.getOutputStream();

		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

		hwb.write(os);

	}

}
