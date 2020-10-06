package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Used as a collection of prime numbers. Does not use multiple elements
 * storage. The next prime is calculated only when needed.
 * 
 * @author Lovro Matošević
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Number of consecutive primes in collection
	 */
	private int num;

	/**
	 * Constructor which accepts a number of consecutive primes.
	 * 
	 * @param num - number of consecutive primes
	 */
	public PrimesCollection(int num) {
		this.num = num;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator<Integer>();
	}

	/**
	 * An iterator used for iterating over primes.
	 * 
	 * @author Lovro Matošević
	 *
	 */
	private class PrimeIterator<T> implements Iterator<Integer> {
		/**
		 * Current prime
		 */
		private int current = 1;

		/**
		 * Returns true if there are elements remaining and false else.
		 */
		@Override
		public boolean hasNext() {
			return current <= num;
		}

		/**
		 * Returns the next prime.
		 */
		@Override
		public Integer next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			int prime = 2;
			int returnPrime = 2;
			for (int i = 0; i < current; i++) {
				for (int j = prime; j < prime * 2; j++) {
					if (this.isPrime(j)) {
						prime = j + 1;
						returnPrime = j;
						break;
					}
				}
			}

			current++;
			return returnPrime;
		}

		/**
		 * Method used to determine if a number is prime.
		 * 
		 * @param num - number being examined
		 * @return true if the number is prime and false else
		 */
		private boolean isPrime(int num) {
			for (int i = 2; i * i <= num; i++) {
				if (num % i == 0)
					return false;
			}
			return true;
		}

	}

}
