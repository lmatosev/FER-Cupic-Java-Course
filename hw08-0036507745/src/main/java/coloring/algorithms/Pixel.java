package coloring.algorithms;

/**
 * Class representing a pixel. Stores pixel's coordinates.
 * 
 * @author Lovro Matošević
 *
 */
public class Pixel {
	/**
	 * Pixel's x coordinate
	 */
	private int x;
	/**
	 * Pixel's y coordinate
	 */
	private int y;

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Constructor which accepts pixel's coordinates.
	 * 
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
