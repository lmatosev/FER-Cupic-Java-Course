package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * 
 * Demonstrational program for the BarChartComponent class. Accepts a single
 * argument, the path to a file which contains bar chart informations. Draws the
 * bar chart and displays the path to file on top of the window.
 * 
 * @author Lovro Matošević
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8001783544740936988L;

	/**
	 * Constructor which accepts a path and a bar chart.
	 * 
	 * @param path  - path to be shown on the window
	 * @param chart - chart to be drawn on the window
	 */
	public BarChartDemo(Path path, BarChart chart) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		initGUI(path, chart);
	}

	/**
	 * Helper method used to initialize the GUI.
	 * 
	 * @param path  - path to be shown on the window as a label
	 * @param chart - chart which is going to be drawn
	 */
	private void initGUI(Path path, BarChart chart) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel label = new JLabel(path.toString());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		BarChartComponent barChart = new BarChartComponent(chart);
		cp.add(label, BorderLayout.PAGE_START);
		cp.add(barChart);
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Exiting");
			return;
		}

		Path path = Paths.get(args[0]);
		BarChart chart;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
			String line;
			String xDesc = br.readLine();
			String yDesc = br.readLine();
			String values = br.readLine();
			List<XYValue> list = new ArrayList<>();
			for (var pair : values.split("\\s+")) {
				String[] pairSplit = pair.split(",");
				int x = Integer.parseInt(pairSplit[0]);
				int y = Integer.parseInt(pairSplit[1]);
				XYValue value = new XYValue(x, y);
				list.add(value);
			}
			line = br.readLine();
			int yMin = Integer.parseInt(line);
			line = br.readLine();
			int yMax = Integer.parseInt(line);
			line = br.readLine();
			int difference = Integer.parseInt(line);

			chart = new BarChart(list, xDesc, yDesc, yMin, yMax, difference);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while reading file. Exiting.");
			return;
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(path, chart).setVisible(true);
			;
		});

	}
}
