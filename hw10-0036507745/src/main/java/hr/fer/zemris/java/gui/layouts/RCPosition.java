package hr.fer.zemris.java.gui.layouts;

/**
 * 
 * Class which represents a constraint for the CalcLayout.
 * 
 * @author Lovro Matošević
 *
 */
public class RCPosition {
	/**
	 * Row number
	 */
	private int row;
	/**
	 * Column number
	 */
	private int column;
	

	/**
	 * Constructor which accepts the row and column numbers.
	 * 
	 * @param row - row number
	 * @param column - column number
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column number
	 */
	public int getColumn() {
		return column;
	}

}
