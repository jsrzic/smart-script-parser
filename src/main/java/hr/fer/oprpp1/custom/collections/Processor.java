package hr.fer.oprpp1.custom.collections;
/**
 * Interface representing an object capable of performing some operation on the passed object.
 * @author Josip
 *
 */
public interface Processor {
	/**
	 * Abstract method which does a certain operation on the passed object.
	 * @param value object which is being processed
	 */
	void process(Object value);
}
