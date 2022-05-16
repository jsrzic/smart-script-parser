package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Interface which allows user to iterate through elements of some collection.
 * @author Josip
 *
 */
public interface ElementsGetter {
	/**
	 * Processes all remaining (unvisited by this ElementsGetter) elements using the given Processor.
	 * @param p Processor used for processing the remaining elements
	 */
	default void processRemaining(Processor p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
	
	/**
	 * Tells if there are any unvisited elements left in the collection.
	 * @return <code>true</code> if there are any unvisited elements in the collection, <code>false</code> otherwise
	 * @throws ConcurrentModificationException if there were any structural modifications on the collection while iterating through it
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next unvisited element in the collection. Throws an exception if there are no unvisited elements left.
	 * @return next element in the collection
	 * @throws NoSuchElementException if there are no unvisited elements left when this method is called
	 * @throws ConcurrentModificationException if there were any structural modifications on the collection while iterating through it
	 */
	Object getNextElement();
}
