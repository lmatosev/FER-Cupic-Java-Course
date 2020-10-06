package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.BandData;

@WebServlet("/glasanje-rezultati")

/**
 * 
 * Servlet used to determine the winners and collect results.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3042154876504834204L;

	/**
	 * Creates the list of band results and list of winner bands.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {

			GlasanjeGlasajServlet.createResultsFile(req, path);

		}

		Map<String, BandData> bandMap;

		bandMap = GlasanjeServlet.getBands(req);

		List<BandData> bandResults = GlasanjeRezultatiServlet.getVotingResults(req, bandMap);
		List<BandData> bandWinners = new ArrayList<>();

		int mostVotes = 0;

		for (var band : bandResults) {

			if (band.getVotes() > mostVotes) {

				bandWinners.clear();
				bandWinners.add(band);
				mostVotes = band.getVotes();

			} else if (band.getVotes() == mostVotes) {

				bandWinners.add(band);

			}

		}

		req.setAttribute("bandResults", bandResults);
		req.setAttribute("bandWinners", bandWinners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

	/**
	 * Static method used to add the votes to the list of band data.
	 * 
	 * @param req - given request
	 * @param bandMap - map containing bands, keys are band ids
	 * @return bands - list of bands where the results are added
	 */
	public static List<BandData> getVotingResults(HttpServletRequest req, Map<String, BandData> bandMap) {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		

		List<BandData> votingResults = new ArrayList<>();

		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));

			for (int i = 0; i < lines.size(); i++) {

				String[] lineSplit = lines.get(i).split("\t");
				int voteCount = Integer.parseInt(lineSplit[1]);
				String id = lineSplit[0];
				BandData band = bandMap.get(id);
				band.setVotes(voteCount);
				votingResults.add(band);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return votingResults;
	}

}
