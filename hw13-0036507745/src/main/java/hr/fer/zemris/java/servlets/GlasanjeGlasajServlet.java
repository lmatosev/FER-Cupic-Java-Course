package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;

import hr.fer.zemris.java.servlets.util.BandData;

@WebServlet("/glasanje-glasaj")

/**
 * 
 * Servlet used to add the vote to the curent total vote count.
 * 
 * @author Lovro Matošević
 *
 */

public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1854830379738735007L;

	/**
	 * {@inheritDoc}
	 * 
	 * Reads from the file where the voting results are located and adds the vote or
	 * creates the file if it does not yet exist,
	 * 
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {

			GlasanjeGlasajServlet.createResultsFile(req, path);

		}

		String id = req.getParameter("id");

		List<String> lines = Files.readAllLines(path);

		for (int i = 0; i < lines.size(); i++) {

			String[] lineSplit = lines.get(i).split("\t");

			if (lineSplit[0].equals(id)) {

				String old = lineSplit[1];
				int oldCount = Integer.parseInt(old);
				oldCount++;
				lineSplit[1] = String.valueOf(oldCount);
				StringBuilder sb = new StringBuilder();
				sb.append(lineSplit[0]).append("\t").append(lineSplit[1]);
				lines.set(i, sb.toString());

			}

		}

		Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Creates the results file. Sets all the votes to 0.
	 * 
	 * @param req  - given request
	 * @param path - path to the results file
	 */

	public static void createResultsFile(HttpServletRequest req, Path path) {

		try {
			Files.createFile(path);
		} catch (IOException e) {
			System.out.println("Error while creating file. File was not created.");
			return;
		}

		StringBuilder sb = new StringBuilder();

		Map<String, BandData> bands = GlasanjeServlet.getBands(req);

		for (var band : bands.values()) {

			sb.append(band.getId()).append("\t").append("0").append("\n");

		}

		try {
			Files.writeString(path, sb.toString(), Charsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Error while creating file. File was not created.");
			return;
		}
		
		System.out.println(path);

	}

}
