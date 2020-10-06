package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/reportImage")

/**
 * 
 * Servlet used to create a pie chart showing the OS usage in the world.
 * 
 * @author Lovro Matošević
 *
 */
public class ReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4380183756824636472L;

	/**
	 * {@inheritDoc}
	 * 
	 * Creates the resulting chart and sends it to output stream.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");

		OutputStream os = resp.getOutputStream();

		JFreeChart chart = generateChart();

		ChartUtils.writeChartAsPNG(os, chart, 500, 300);
	}

	/**
	 * Generates the resulting chart.
	 * 
	 * @return chart - chart representing the OS usage in the world
	 */
	private JFreeChart generateChart() {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 4.16);
		dataset.setValue("Mac", 8.4);
		dataset.setValue("Windows", 87.44);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
