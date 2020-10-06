package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.BandData;

@WebServlet("/glasanje")

/**
 * 
 * Servlet used to create a list of all defined bands.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086931568597427727L;

	/**
	 * Creates a list of bands.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

		List<BandData> bands = new ArrayList<>();

		Map<String, BandData> bandMap = GlasanjeServlet.getBands(req);

		for (var band : bandMap.values()) {
			bands.add(band);
		}

		Comparator<BandData> idComparator = (b1, b2) -> b1.getId().compareTo(b2.getId());

		bands.sort(idComparator);

		reader.close();

		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Static method used to create a map of all the defined bands where the keys
	 * are band id-s and values are band data.
	 *
	 * @param req - given request
	 * @return bands - map where keys are band id-s and values instances of
	 *         {@link BandData}
	 */
	public static Map<String, BandData> getBands(HttpServletRequest req) {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		BufferedReader reader;

		HashMap<String, BandData> bands = new HashMap<>();

		try {

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			String line;

			while ((line = reader.readLine()) != null) {

				String[] lineSplit = line.split("\t");

				BandData band = new BandData(lineSplit[0], lineSplit[1], lineSplit[2]);

				bands.put(lineSplit[0], band);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bands;
	}
}
