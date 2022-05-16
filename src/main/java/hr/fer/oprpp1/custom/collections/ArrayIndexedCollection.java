package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Resizable array-backed collection of objects.
 * @author Josip
 *
 */
public class ArrayIndexedCollection implements List {
	/**
	 * Personalized version of ElementsGetter used for iteration through elements of ArrayIndexedCollection.
	 */
	private static class ArrayIndexedElementsGetter implements ElementsGetter {
		/**
		 * Reference to the collection this ElementsGetter is iterating through.
		 */
		private ArrayIndexedCollection collection;
		
		/**
		 * Index in the underlying array of the next element that is to be iterated through.
		 */
		private int pointerPosition = 0;
		
		/**
		 * Holds the modification count of the collection which was present at the time this ElementsGetter was created.
		 */
		private long savedModificationCount;
		
		/**
		 * Creates a new ArrayIndexedElementsGetter.
		 * @param collection reference of the collection this ElementsGetter is iterating through
		 * @param modificationCount number of method calls which reallocated or shifted the elements in the array of this collection 
		 */
		public ArrayIndexedElementsGetter(ArrayIndexedCollection collection, long modificationCount) {
			this.collection = collection;
			this.savedModificationCount = modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			if(collection.modificationCount != this.savedModificationCount)
				throw new ConcurrentModificationException("There has been structural modification on the collection while iterating through it.");
			
			return pointerPosition < collection.size();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getNextElement() {
			if(collection.modificationCount != this.savedModificationCount)
				throw new ConcurrentModificationException("There has been structural modification on the collection while iterating through it.");
			
			if(!this.hasNextElement()) {
				throw new NoSuchElementException("There are no more elements left to get.");
			}
			
			return collection.elements[this.pointerPosition++];
		}
	}
	
	/**
	 * Number of elements currently stored in the collection.
	 */
	private int size;
	
	/**
	 * Actual elements that are currently stored in the collection.
	 */
	private Object[] elements;
	
	/**
	 * Variable which counts number of method calls which reallocated or shifted the elements in the array of this collection.
	 */
	private long modificationCount = 0;
	
	/**
	 * Creates an empty collection with specified initial capacity.
	 * @param initialCapacity initial capacity of the collection, must be larger or equal to 1
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) 
			throw new IllegalArgumentException("You set inital capacity to " + initialCapacity + " but initial capacity must be larger or equal to 1.");
		
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates an empty collection with initial capacity set to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	} 
	
	/**
	 * Creates a collection with specified initial capacity and fills it with elements of some other collection.
	 * @param other collection from which the elements are copied
	 * @param initialCapacity initial capacity of the collection, if less than size of the <code>other</code> collection then it is set to that size
	 * @throws NullPointerException if the collection from which the elements are copied is <code>null</code>
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) 
			throw new NullPointerException("Other collection must not be null.");
		
		if (initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		this.elements = new Object[initialCapacity];
		this.addAll(other);
	}
	
	/**
	 * Creates a collection with initial capacity set to 16 and fills it with copies of elements of some other collection.
	 * @param other collection from which the elements are copied
	 */
    public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
    
      
    /**
	 * Returns size of the collection.
	 * @return number of currently stored elements in this collection
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the given object into this collection. If the current capacity is full, it reallocates the elements with doubled capacity.
	 * @param value object that is added to the collection
	 * @throws NullPointerException if <code>value</code> that is being added is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException("Object that is being added must not be null.");
		
		if (this.size == this.elements.length) {
			Object[] reallocatedElements = new Object[2*this.elements.length];
			for(int i = 0; i < this.size(); i++) {
				reallocatedElements[i] = this.elements[i];
			}
			this.elements = reallocatedElements;
		}
		
		this.modificationCount++;
		this.elements[this.size++] = value;
	}
	
	/**
	 * Checks if the collection contains the given object (as determined by <code>equals</code> method). 
	 * @param value object which presence in the collection is being checked
	 * @return <code>true</code> if the collection contains given value, <code>false</code> if given value is <code>null</code> or not present in the collection
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		
		for (int i = 0; i < this.size(); i++) {
			if (this.elements[i].equals(value)) return true;
		}
		
		return false;
	}
	
	/**
	 * Removes first occurrence of the given value from the collection (determined by <code>equals</code> method) if the value is present, otherwise it does nothing. 
	 * @param value value of the object which is being removed 
	 * @return <code>true</code> if the collection contains given value as determined by <code>equals</code> method, otherwise <code>false</code>
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) return false;
		
		for (int i = 0; i < this.size(); i++) {
			if (this.elements[i].equals(value)) {
				for (int j = i; j < this.size() - 1; j++) {
					this.elements[j] = this.elements[j+1];
				}
				this.elements[--this.size] = null;
				this.modificationCount++;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Allocates new array with size equal to the size of this collections, fills it with collection content and returns the array.
	 * @return newly allocated array with the same collection content
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for (int i = 0; i < this.size(); i++) {
			newArray[i] = this.elements[i];
		}
		
		return newArray;
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			this.elements[i] = null;
		}
		this.modificationCount++;
		this.size = 0;
	}
    
	/**
	 * Returns the object that is stored in backing array at position <code>index</code>.
	 * @param index position of the wanted element in the array
	 * @return object at the position <code>index</code>
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index > this.size-1) 
			throw new IndexOutOfBoundsException(String.format("Legal indexes are between %d and %d. Your index was %d.", 0, this.size-1, index));
		
		return this.elements[index];
	}
	
	/**
	 * Inserts the given value at the given position in array. Shifts elements after the given position accordingly. 
	 * If the current capacity is full, it reallocates the elements with doubled capacity.
	 * @param value object that is being inserted
	 * @param position index which will belong to the object after it's inserted
	 * @throws IndexOutOfBoundsException if <code>position</code> is not in the range [0, size]
	 * @throws NullPointerException if <code>value</code> that is being added is <code>null</code>
	 */
	@Override
	public void insert(Object value, int position) {
		if (value == null)
			throw new NullPointerException("Object that is being added must not be null.");
		
		if (position < 0 || position > this.size) 
			throw new IndexOutOfBoundsException(String.format("Legal positions are between %d and %d. Your position was %d.", 0, this.size, position));
				
		if (this.size == this.elements.length) {
			Object[] reallocatedElements = new Object[2*this.elements.length];
			for(int i = 0; i < this.size(); i++) {
				reallocatedElements[i] = this.elements[i];
			}
			this.elements = reallocatedElements;
		}
			
		for (int j = this.size(); j > position; j--) {
			this.elements[j] = this.elements[j-1];
		}
		
		this.elements[position] = value;
		this.modificationCount++;
		this.size++;
	}
	
	/**
	 * Searches through the collection and returns the index of the first occurrence of the given value or -1 if the value is not found.
	 * Method <code>equals</code> is used for comparison.
	 * @param value object which is searched for
	 * @return index of the first occurrence of the given value if the object is present, <code>-1</code> otherwise
	 */
	@Override
	public int indexOf(Object value) {
		if (value == null) return -1;
		
		for (int i = 0; i < this.size(); i++) {
			if (this.elements[i].equals(value)) return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Shifts elements accordingly.
	 * @param index position of the element which is removed
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index > this.size-1) 
			throw new IndexOutOfBoundsException(String.format("Legal indexes are between {} and {}. Your index was {}.", 0, this.size-1, index));
		
		for (int i = index; i < this.size() - 1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		
		this.elements[--this.size] = null;
		this.modificationCount++;
	}
	
	/**
	 * Creates a new ElementsGetter which is personalized to work with ArrayIndexedCollection.
	 * @return the new ElementsGetter that contains reference to this collection
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayIndexedElementsGetter(this, modificationCount);
	}
	
}
