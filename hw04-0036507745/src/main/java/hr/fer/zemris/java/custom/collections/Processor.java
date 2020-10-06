package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Interface which describes a simple processor. Extending classes are expected to override method process.
 * 
 * @author Lovro Matošević
 *
 */

public interface Processor<T> {
	
	/**
	 * 
	 * @param value - value to be processed
	 */

	abstract public void process(T value);

}
