package hr.fer.oprpp1.custom.collections;

/**
 * Interface inheriting Collection interface and expanding it with a couple of methods which enable list-like element manipulation.
 * @author Josip
 */
public interface List extends Collection {
	/**
	 * Returns element from the collection at the given index.
	 * @param index position of the element we want to get
	 * @return the wanted element
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	Object get(int index);
	
	/**
	 * Inserts the given object so that its new index is the one given by position.
	 * @param value the object being inserted
	 * @param position collection index that will belong to the inserted object after insertion
	 * @throws IndexOutOfBoundsException if <code>position</code> is not in the range [0, size]
	 */
	void insert(Object value, int position);
	
	/**
	 * Returns index of the wanted object inside the collection.
	 * @param value object which index is being searched for 
	 * @return index of the wanted object
	 */
	int indexOf(Object value);
	
	/**
	 * Removes element at specified index from collection. 
	 * @param index position of the element which is removed
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	void remove(int index);

}
