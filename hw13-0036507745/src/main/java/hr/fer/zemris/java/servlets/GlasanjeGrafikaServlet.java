package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.servlets.util.BandData;

@WebServlet("/glasanje-grafika")

/**
 * 
 * Servlet used to create the pie chart which represents the total vote count.
 * 
 * @author Lovro Matoševič
 *
 */

public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3049853843704255917L;

	/**
	 * Creates the chart and writes it to response output stream.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");

		OutputStream os = resp.getOutputStream();

		Map<String, BandData> bandMap = GlasanjeServlet.getBands(req);

		List<BandData> bands = GlasanjeRezultatiServlet.getVotingResults(req, bandMap);

		JFreeChart chart = generateChart(bands);

		ChartUtils.writeChartAsPNG(os, chart, 400, 490);
	}

	/**
	 * Creates a chart based on the given band data.
	 * 
	 * @param bands - list containing data about bands
	 * @return chart - the resulting chart
	 */
	private JFreeChart generateChart(List<BandData> bands) {

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (var band : bands) {

			dataset.setValue(band.getName(), band.getVotes());

		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Voting results", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
