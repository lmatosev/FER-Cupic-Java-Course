package searching.slagalica;

import java.util.Arrays;

/**
 * Class representing a puzzle configuration. Used to store a configuration.
 * 
 * @author Lovro Matošević
 *
 */
public class KonfiguracijaSlagalice {
	/**
	 * The field configuration stored.
	 */
	private int[] polje;

	/**
	 * Constructor which accepts an int[] representing the current puzzle
	 * configuration.
	 * 
	 * @param polje - puzzle configuration
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}

	/**
	 * @return polje
	 */
	public int[] getPolje() {
		return polje;
	}

	/**
	 * Searches and returns the index of 0 in the stored configuration.
	 * 
	 * @return index- index of 0
	 */
	public int indexOfSpace() {
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Returns a string representation of the current configuration.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (polje[i * 3 + j] == 0) {
					sb.append("*" + " ");
				} else {
					sb.append(polje[i * 3 + j] + " ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
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
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(polje, other.polje))
			return false;
		return true;
	}

}
