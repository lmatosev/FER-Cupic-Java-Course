package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * A custom layout which places components in a grid consisting of 5 rows and 7
 * columns. First row can contain only 3 components.
 * 
 * @author Lovro Matošević
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * The spacing between components
	 */
	private int spacing;
	/**
	 * Map which maps positions to components
	 */
	private Map<RCPosition, Component> map;

	/**
	 * The default constructor. Sets the spacing to the default value which is 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor which accepts an integer representing the desired spacing.
	 * 
	 * @param spacing - spacing which should be used
	 */
	public CalcLayout(int spacing) {
		super();
		this.spacing = spacing;
		this.map = new HashMap<>();
	}

	/**
	 * This method is not supported in this class.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addLayoutComponent(String str, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Draws all the components to the provided container.
	 */
	@Override
	public void layoutContainer(Container container) {
		int width = container.getWidth();
		int height = container.getHeight();

		double widthUnit = width / 7;
		double heightUnit = height / 5;

		for (RCPosition constraint : this.map.keySet()) {
			int row = constraint.getRow();
			int column = constraint.getColumn();
			Component comp = this.map.get(constraint);
			if (column == 1 && row == 1) {
				comp.setBounds(0, 0, (int) widthUnit * 5 + this.spacing * 4, (int) heightUnit);
			} else {
				comp.setBounds((column - 1) * (this.spacing + (int) widthUnit),
						(row - 1) * (this.spacing + (int) heightUnit), (int) widthUnit, (int) heightUnit);
			}
		}

	}

	/**
	 * Returns the minimum layout size.
	 * 
	 * @return dimension - the minimum layout size
	 */
	@Override
	public Dimension minimumLayoutSize(Container container) {
		Dimension dim = new Dimension(0, 0);

		for (Component comp : this.map.values()) {
			double compHeight = comp.getMinimumSize().getHeight();
			double compWidth = comp.getMinimumSize().getWidth();
			double currentHeight = dim.getHeight();
			double currentWidth = dim.getWidth();

			if (compHeight > currentHeight) {
				dim.setSize(currentWidth, compHeight);
				currentHeight = compHeight;
			}
			if (compWidth > currentWidth) {
				dim.setSize(compWidth, currentHeight);
			}
		}

		double dimHeight = 4 * this.spacing + 5 * dim.getHeight();
		double dimWidth = 6 * this.spacing + 7 * dim.getWidth();

		dim.setSize(dimWidth, dimHeight);
		return dim;
	}

	/**
	 * Returns the maximum layout size.
	 * 
	 * @return dimension - maximum layout size
	 */
	@Override
	public Dimension maximumLayoutSize(Container container) {
		Dimension dim = new Dimension(0, 0);

		for (Component comp : this.map.values()) {
			double compHeight = comp.getMaximumSize().getHeight();
			double compWidth = comp.getMaximumSize().getWidth();
			double currentHeight = dim.getHeight();
			double currentWidth = dim.getWidth();

			if (compHeight > currentHeight) {
				dim.setSize(currentWidth, compHeight);
				currentHeight = compHeight;
			}
			if (compWidth > currentWidth) {
				dim.setSize(compWidth, currentHeight);
			}
		}

		double dimHeight = 4 * this.spacing + 5 * dim.getHeight();
		double dimWidth = 6 * this.spacing + 7 * dim.getWidth();

		dim.setSize(dimWidth, dimHeight);
		return dim;
	}

	/**
	 * Returns the preferred layout size.
	 * 
	 * @return dimension - preferred layout size
	 */
	@Override
	public Dimension preferredLayoutSize(Container container) {
		Dimension dim = new Dimension(0, 0);

		for (RCPosition constraint : this.map.keySet()) {
			Component comp = this.map.get(constraint);
			double compHeight = comp.getPreferredSize().getHeight();
			double compWidth = comp.getPreferredSize().getWidth();
			double currentHeight = dim.getHeight();
			double currentWidth = dim.getWidth();

			if (constraint.getColumn() == 1 && constraint.getRow() == 1) {
				compWidth = (compWidth - this.spacing * 4) / 5;
			}

			if (compHeight > currentHeight) {
				dim.setSize(currentWidth, compHeight);
				currentHeight = compHeight;
			}
			if (compWidth > currentWidth) {
				dim.setSize(compWidth, currentHeight);
			}
		}

		double dimHeight = 4 * this.spacing + 5 * dim.getHeight();
		double dimWidth = 6 * this.spacing + 7 * dim.getWidth();

		dim.setSize(dimWidth, dimHeight);
		return dim;
	}

	/**
	 * Removes the provided component from the layout.
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void removeLayoutComponent(Component comp) {
		this.map.remove(comp);
	}

	/**
	 * Adds a component to the layout using the provided constraint. Constraint can
	 * be an instance of RCPosition or a String.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition constraint;
		if (constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			String text = (String) constraints;
			String[] textSplit = text.split(",");
			if (textSplit.length != 2) {
				throw new CalcLayoutException("Invalid constraint.");
			}
			int num1;
			int num2;
			try {
				num1 = Integer.parseInt(textSplit[0]);
				num2 = Integer.parseInt(textSplit[1]);
			} catch (NumberFormatException ex) {
				throw new CalcLayoutException("Invalid constraint.");
			}
			constraint = new RCPosition(num1, num2);
		} else {
			throw new CalcLayoutException("Invalid constraint.");
		}

		if (constraint.getRow() < 1 || constraint.getRow() > 5) {
			throw new CalcLayoutException("Invalid row number.");
		}
		if (constraint.getColumn() < 1 || constraint.getColumn() > 7) {
			throw new CalcLayoutException("Invalid column number.");
		}

		if (constraint.getRow() == 1 && (constraint.getColumn() < 6 && constraint.getColumn() != 1)) {
			throw new CalcLayoutException("Invalid row and column number.");
		}

		for (RCPosition checkConstraint : this.map.keySet()) {
			if (checkConstraint.getColumn() == constraint.getColumn()
					&& checkConstraint.getRow() == constraint.getRow()) {
				throw new CalcLayoutException("Position is not empty.");
			}
		}

		this.map.put(constraint, comp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container container) {
	}

}
