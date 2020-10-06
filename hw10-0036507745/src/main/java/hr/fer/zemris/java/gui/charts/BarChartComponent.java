package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;

/**
 * 
 * A representation of a simple bar chart. Extends the JComponent class.
 * 
 * @author Lovro Matošević
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3092155870400966674L;

	/**
	 * Fixed offset from the top of the window
	 */
	private static int TOP_OFFSET = 65;
	/**
	 * Fixed offset from the botton of the window
	 */
	private static int BOTTOM_OFFSET = 65;
	/**
	 * Calculated offset from the left side of the window
	 */
	private int LEFT_OFFSET;
	/**
	 * Fixed offset from the right side of the window
	 */
	private static int RIGHT_OFFSET = 65;

	/**
	 * Chart which is being drawn
	 */
	private BarChart chart;
	/**
	 * Height of the chart
	 */
	private int chartHeight;
	/**
	 * Width of the chart
	 */
	private int chartWidth;
	/**
	 * Width of a single bar
	 */
	private int singleBarWidth;
	/**
	 * Height of a unitary bar
	 */
	private double singleBarUnitHeight;
	/**
	 * List of XYValues
	 */
	private List<XYValue> list;
	/**
	 * Maximal y value
	 */
	private int yMax;
	/**
	 * Minimal y value
	 */
	private int yMin;
	/**
	 * Count of x values
	 */
	private int xCount;
	/**
	 * Count of y values
	 */
	private int yCount;
	/**
	 * Difference between y values
	 */
	private int difference;

	/**
	 * Constructor which accepts a BarChart which is going to be drawn.
	 * 
	 * @param chart - BarChart to be drawn
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
		list = chart.getObjects();
		this.yMax = chart.getyMax();
		this.yMin = chart.getyMin();
		this.difference = chart.getDifference();
		this.xCount = list.size();
		this.yCount = (yMax - yMin) / difference;

		int maxDigitNumber = String.valueOf(yMax).length();
		this.LEFT_OFFSET = maxDigitNumber + 60;
	}

	/**
	 * Draws the BarChart.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		chartHeight = this.getHeight() - TOP_OFFSET - BOTTOM_OFFSET;
		chartWidth = this.getWidth() - LEFT_OFFSET - RIGHT_OFFSET;
		this.singleBarWidth = chartWidth / xCount;
		this.singleBarUnitHeight = (double) chartHeight / yCount;

		this.drawDescriptions(g);
		this.drawNumbers(g);
		this.drawGrid(g);
		this.drawBars(g);
		this.drawArrows(g);

		g.drawLine(LEFT_OFFSET, TOP_OFFSET - 5, LEFT_OFFSET, chartHeight + TOP_OFFSET + 4);
		g.drawLine(LEFT_OFFSET - 4, chartHeight + TOP_OFFSET, LEFT_OFFSET + chartWidth + 4, TOP_OFFSET + chartHeight);
	}

	/**
	 * Helper method used to draw arrows on the x-axis and y-axis.
	 */
	private void drawArrows(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		Polygon arrowHead1 = new Polygon();
		arrowHead1.addPoint(LEFT_OFFSET + chartWidth + 3, TOP_OFFSET + chartHeight - 5);
		arrowHead1.addPoint(LEFT_OFFSET + chartWidth + 3, TOP_OFFSET + chartHeight + 5);
		arrowHead1.addPoint(LEFT_OFFSET + chartWidth + 9, TOP_OFFSET + chartHeight);

		Polygon arrowHead2 = new Polygon();
		arrowHead2.addPoint(LEFT_OFFSET - 5, TOP_OFFSET - 4);
		arrowHead2.addPoint(LEFT_OFFSET + 5, TOP_OFFSET - 4);
		arrowHead2.addPoint(LEFT_OFFSET, TOP_OFFSET - 12);
		g2.fill(arrowHead1);
		g2.fill(arrowHead2);

	}

	/**
	 * Helper method used to draw the grid.
	 */
	private void drawGrid(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 1; i <= xCount; i++) {
			g2.setColor(Color.ORANGE);
			g2.drawLine(LEFT_OFFSET + i * singleBarWidth, TOP_OFFSET + chartHeight, LEFT_OFFSET + i * singleBarWidth,
					TOP_OFFSET);
			g2.setColor(Color.BLACK);
			g2.drawLine(LEFT_OFFSET + i * singleBarWidth, TOP_OFFSET + chartHeight + 4,
					LEFT_OFFSET + i * singleBarWidth, TOP_OFFSET + chartHeight);
		}

		for (int i = 0; i < (yMax - yMin) / difference; i++) {
			g2.setColor(Color.ORANGE);
			g2.drawLine(LEFT_OFFSET, TOP_OFFSET + i * ((int) singleBarUnitHeight), LEFT_OFFSET + chartWidth,
					(int) (TOP_OFFSET + i * (singleBarUnitHeight)));
			g2.setColor(Color.BLACK);
			Line2D line = new Line2D.Double(LEFT_OFFSET - 4, TOP_OFFSET + i * (singleBarUnitHeight), LEFT_OFFSET,
					(TOP_OFFSET + i * (singleBarUnitHeight)));
			g2.draw(line);
		}

	}

	/**
	 * Helper method which draws the descriptions for the x-axis and y-axis
	 */
	private void drawDescriptions(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		String xDesc = chart.getxDesc();
		String yDesc = chart.getyDesc();

		g2.drawString(xDesc, (chartWidth / 2) - xDesc.length(), this.getHeight() - 5);

		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		AffineTransform old = g2.getTransform();
		g2.transform(at);
		g2.drawString(yDesc, (-chartHeight / 2) - TOP_OFFSET - yDesc.length(), 15);
		g2.setTransform(old);
	}

	/**
	 * Helper method used to draw the bars.
	 */
	private void drawBars(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int difference = chart.getDifference();
		for (XYValue value : this.list) {
			int x = value.getX();
			int y = value.getY();
			y = y > yMax ? yMax : y;
			double rectangleX = (x - 1) * singleBarWidth + LEFT_OFFSET;
			double rectangleY = TOP_OFFSET + chartHeight - ((double) (y - yMin) / difference) * singleBarUnitHeight;
			double rectangleWidth = singleBarWidth;
			double rectangleHeight = ((double) (y - yMin) / difference) * singleBarUnitHeight;
			g2.setColor(new Color(245, 120, 45));
			Rectangle2D rect = new Rectangle2D.Double(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
			g2.fill(rect);
			g2.setColor(Color.WHITE);
			g2.draw(rect);
			g2.setColor(Color.BLACK);

		}
	}

	/**
	 * Helper method used to draw the numbers next to the axis.
	 */
	private void drawNumbers(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < xCount; i++) {
			g2.drawString(String.valueOf(i + 1), LEFT_OFFSET + i * singleBarWidth + singleBarWidth / 2,
					TOP_OFFSET + chartHeight + 20);
		}
		int digitLength = String.valueOf(this.yMax).length();
		String format = "%" + digitLength + "d";
		for (int i = 0; i <= yCount; i++) {
			g2.drawString(String.format(format, i * difference), LEFT_OFFSET - 25,
					(int) (TOP_OFFSET + chartHeight - i * singleBarUnitHeight));
		}

	}
}
