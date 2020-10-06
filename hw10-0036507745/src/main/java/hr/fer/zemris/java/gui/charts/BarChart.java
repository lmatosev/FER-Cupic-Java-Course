package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * 
 * Class which represents a model of a bar chart. Contains a list of XYValues.
 * 
 * @author Lovro Matošević
 *
 */
public class BarChart {
	/**
	 * List of values which are stored.
	 */
	private List<XYValue> objects;
	/**
	 * The description of the x-axis
	 */
	private String xDesc;
	/**
	 * The description of the y-axis
	 */
	private String yDesc;
	/**
	 * Minimal y value
	 */
	private int yMin;
	/**
	 * Maximal y value
	 */
	private int yMax;
	/**
	 * Difference between two y-s
	 */
	private int difference;

	/**
	 * Constructor which accepts all the parameters which define the chart.
	 * 
	 * @param objects    - list of XYValues
	 * @param xDesc      - description of the x axis
	 * @param yDesc      - description of the y axis
	 * @param yMin       - minimal y value
	 * @param yMax       - maximal y value
	 * @param difference - difference between two y-s
	 * 
	 * @throws IllegalArgumentException - thrown if the minimal y value is negative,
	 *                                  if the maximal value is less or equal to
	 *                                  minimal y value and if there are XYValues
	 *                                  which have the y parameter smaller than the
	 *                                  provided minimal y.
	 */
	public BarChart(List<XYValue> objects, String xDesc, String yDesc, int yMin, int yMax, int difference) {

		if (yMin < 0 || yMax <= yMin) {
			throw new IllegalArgumentException("Invalid argument provided");
		}

		for (var value : objects) {
			if (value.getY() < yMin) {
				throw new IllegalArgumentException("Provided Y values must not be smaller than minimal Y");
			}
		}

		this.objects = objects;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		this.difference = difference;

	}

	/**
	 * @return the objects
	 */
	public List<XYValue> getObjects() {
		return objects;
	}

	/**
	 * @return the xDesc
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * @return the yDesc
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the difference
	 */
	public int getDifference() {
		return difference;
	}

}
