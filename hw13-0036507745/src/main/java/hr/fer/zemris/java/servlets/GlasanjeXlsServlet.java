package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.util.BandData;

@WebServlet("/glasanje-xls")

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

		Map<String, BandData> bandMap = GlasanjeServlet.getBands(req);

		List<BandData> votingResults = GlasanjeRezultatiServlet.getVotingResults(req, bandMap);

		@SuppressWarnings("resource")

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow((short) 0);

		rowhead.createCell((short) 0).setCellValue("Bend");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");

		for (int i = 1; i < votingResults.size() + 1; i++) {

			rowhead = sheet.createRow((short) i);
			
			BandData band = votingResults.get(i-1);
			
			rowhead.createCell((short) 0).setCellValue(band.getName());
			rowhead.createCell((short) 1).setCellValue(band.getVotes());

			
		}

		OutputStream os = resp.getOutputStream();

		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

		hwb.write(os);

	}

}
