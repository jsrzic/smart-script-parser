package hr.fer.oprpp1.custom.collections;
/**
 * Interface representing some general collection of objects.
 * @author Josip
 *
 */
public interface Collection {
	/**
	 * Calls processor.process(...) for each element of this collection
	 * @param processor processor used to process each element of the collection
	 */
	default void forEach(Processor processor) {
		ElementsGetter eg = this.createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	};
	
	/**
	 * Adds into this collection all elements from some other collection that are accepted by the given tester.
	 * @param col some other collection from which the elements are being copied
	 * @param tester tester used for accepting or rejecting elements from the other collection
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter eg = col.createElementsGetter();
		while(eg.hasNextElement()) {
			Object nextElement = eg.getNextElement();
			if (tester.test(nextElement)) {
				this.add(nextElement);
			}
		}
	}
	/**
	 * Checks if the collection contains no objects by comparing its size to 0.
	 * @return <code>true</code> if collection contains no objects, <code>false</code> otherwise
	 */
	default boolean isEmpty() {
		return size() == 0; 
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection remains unchanged.
	 * @param other collection whose elements are added into the current collection
	 */
	default void addAll(Collection other) {		
		other.forEach(this::add);
	}
	
	/**
	 * Returns size of the collection.
	 * @return number of currently stored elements in this collection
	 */
	int size();
	
	/**
	 * Adds the given object into this collection. 
	 * @param value object that is added to the collection
	 */
	void add(Object value);
	
	/**
	 * Checks if the collection contains the given object (as determined by <code>equals</code> method). 
	 * @param value object which presence in the collection is being checked
	 * @return <code>true</code> if the collection contains given value, <code>false</code> if given value is <code>null</code> or not present in the collection
	 */
	boolean contains(Object value);
	
	/**
	 * Removes one occurrence of the given value from the collection (determined by <code>equals</code> method) if the value is present, otherwise it does nothing. 
	 * @param value value of the object which is being removed 
	 * @return <code>true</code> if the collection contains given value as determined by <code>equals</code> method, otherwise <code>false</code>
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and returns the array
	 * @return newly allocated array with the same collection content
	 */
	Object[] toArray();
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear();
	
	/**
	 * Creates a new ElementsGetter.
	 * @return new ElementsGetter used for iterating through the collection
	 */
	ElementsGetter createElementsGetter();
}
