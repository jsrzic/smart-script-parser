package hr.fer.oprpp1.custom.collections;

/**
 * Implementation of a stack-liked collection.
 * @author Josip
 *
 */
public class ObjectStack {
	
	/**
	 * Internal collection which actually stores elements and handles element manipulations.
	 */
	private ArrayIndexedCollection adapteeCollection = new ArrayIndexedCollection();
	
	/**
	 * Checks if the stack contains no objects.
	 * @return <code>true</code> if collection contains no objects, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return adapteeCollection.isEmpty();
	}
	
	/**
	 * Returns number of elements currently on the stack.
	 * @return number of elements currently on the stack
	 */
	public int size() {
		return adapteeCollection.size();
	}
	
	/**
	 * Adds a value to the top of the stack.
	 * @param value object which is added to the top of the stack
	 */
	public void push(Object value) {
		adapteeCollection.add(value);
	}
	
	/**
	 * Removes from the stack the element which is currently on top and returns it.
	 * @return element on top of the stack (the removed element)
	 * @throws EmptyStackException if the stack is empty (no elements to be removed)
	 */
	public Object pop() {
		if (this.isEmpty())
			throw new EmptyStackException("The stack is empty. There are no elements to be removed.");
		
		Object lastElement = adapteeCollection.get(this.size()-1);
		adapteeCollection.remove(this.size()-1);
		
		return lastElement;
	}
	
	/**
	 * Returns the element which is currently on top of the stack.
	 * @return element on top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() {
		if (this.isEmpty())
			throw new EmptyStackException("The stack is empty. There are no elements to be removed.");
		
		return adapteeCollection.get(this.size()-1);
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		adapteeCollection.clear();
	}
	
}