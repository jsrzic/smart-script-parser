package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Linked list-backed collection of objects.
 * @author Josip
 *
 */
public class LinkedListIndexedCollection implements List {
	/**
	 * Personalized version of ElementsGetter used for iteration through elements of LinkedListIndexedCollection.
	 */
	private static class LinkedListIndexedElementsGetter implements ElementsGetter {
		/**
		 * Reference to the collection this ElementsGetter is iterating through.
		 */
		private LinkedListIndexedCollection collection;
		
		/**
		 * Reference pointing to the next element in the collection that is to be iterated through.
		 */
		private ListNode elementPointer;
		
		/**
		 * Holds the modification count of the collection which was present at the time this ElementsGetter was created.
		 */
		private long savedModificationCount;
		
		/**
		 * Creates a new LinkedListIndexedElementsGetter.
		 * @param collection reference of the collection this ElementsGetter is iterating through
		 * @param modificationCount number of method calls which added or removed a ListNode from this collection
		 */
		public LinkedListIndexedElementsGetter(LinkedListIndexedCollection collection, long modificationCount) {
			this.collection = collection;
			elementPointer = collection.first;
			this.savedModificationCount = modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			if(collection.modificationCount != this.savedModificationCount)
				throw new ConcurrentModificationException("There has been structural modification on the collection while iterating through it.");
			
			return elementPointer != null;
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
			Object valueToBeReturned = elementPointer.value;
			elementPointer = elementPointer.next;
			return valueToBeReturned;
		}
	}
	
	
	/**
	 * Helper class that represents a node (element) of the linked list.
	 */
	private static class ListNode {
		/**
		 * Reference pointing to the previous node in the linked list, or <code>null</code> if current node is the first element of the list.
		 */
	    private ListNode previous;
		
	    /**
	     * The actual object which is contained inside the node.
	     */
		private Object value;
		
		/**
		 * Reference pointing to the next node in the linked list, or <code>null</code> if current node is the last element of the list.
		 */
		private ListNode next;
		
		/**
		 * Creates a new node.
		 * @param previous reference pointing to the previous node in the linked list, or <code>null</code> if current node is the first element of the list
		 * @param value the actual object which is contained inside the node
		 * @param next reference pointing to the next node in the linked list, or <code>null</code> if current node is the last element of the list
		 */
		public ListNode(ListNode previous, Object value, ListNode next) {
			this.previous = previous;
			this.value = value;
			this.next = next;
		}
	}
	
	
	/**
	 * Number of elements currently stored in the collection.
	 */
	private int size;
	
	/**
	 * Reference pointing to the first node in the linked list, or <code>null</code> if the list is empty.
	 */
	private ListNode first;
	
	/**
	 * Reference pointing to the last node in the linked list, or <code>null</code> if the list is empty.
	 */
	private ListNode last;
	
	/**
	 * Variable which counts number of method calls which added or removed a ListNode from this collection.
	 */
	private long modificationCount = 0;
	
	/**
	 * Creates an empty linked list.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = this.last = null;
	}
	
	/**
	 * Creates a linked list and fills it with elements from collection given by <code>other</code>.
	 * @param other collection from which the elements are copied
	 * @throws NullPointerException if the collection from which the elements are copied is <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) 
			throw new NullPointerException("Other collection must not be null.");
		
		this.first = this.last = null;
		this.addAll(other);
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
	 * Adds the given object into this collection. The complexity is O(1).
	 * @param value object that is added to the collection
	 * @throws NullPointerException if <code>value</code> that is being added is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException("Object that is being added must not be null.");
		
		if (this.size == 0) {
			ListNode newElement = new ListNode(null, value, null);
			this.first = newElement;
			this.last = newElement;
		}
		
		else {
			ListNode newElement = new ListNode(last, value, null);
			last.next = newElement;
			this.last = newElement;
		}
		
		this.modificationCount++;
		this.size++;
	}
	
	/**
	 * Checks if the collection contains the given object (as determined by <code>equals</code> method). 
	 * @param value object which presence in the collection is being checked
	 * @return <code>true</code> if the collection contains given value, <code>false</code> if given value is <code>null</code> or not present in the collection
	 */
	@Override
	public boolean contains(Object value) {
		for (ListNode i = first; i != null; i = i.next) {
			if (i.value.equals(value)) return true;
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
		
		for (ListNode i = first; i != null; i = i.next) {
			if (i.value.equals(value)) {
				if (i.previous == null) {
					i.next.previous = null;
					this.first = i.next;
				}
				else if (i.next == null) {
					i.previous.next = null;
					this.last = i.previous;
				}
				else {
					i.previous.next = i.next;
					i.next.previous = i.previous;
				}
				
				this.size--;
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
		int indexCounter = 0;
		for (ListNode i = first; i != null; i = i.next) {
			newArray[indexCounter++] = i.value; 
		}
		
		return newArray;
	}
		
	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Returns the object that is stored in linked list at position <code>index</code>.
	 * @param index position of the wanted element in the array
	 * @return object at the position <code>index</code>
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index > this.size-1) 
			throw new IndexOutOfBoundsException(String.format("Legal indexes are between %d and %d. Your index was %d.", 0, this.size-1, index));
		
		ListNode it = null;
		
		if (index >= this.size/2) {
			it = this.last;
			for (int counter = 0; counter < this.size()-index-1; counter++) {
				it = it.previous;
			}
		}
		else {
			it = this.first;
			for (int counter = 0; counter < index; counter++) {
				it = it.next;
			}
		}
		
		return it.value;
	}
	
	/**
	 * Inserts the given value at the given position in the linked list. 
	 * If the element is inserted at the beginning or end of the list, the complexity is O(1). Otherwise, the complexity is O(size/2).
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
				
		if (position == 0) {
			ListNode newNode = new ListNode(null, value, this.first);
			this.first.previous = newNode;
			this.first = newNode;
		}
		
		else if (position == this.size) {
			ListNode newNode = new ListNode(this.last, value, null);
			this.last.next = newNode;
			this.last = newNode;
		}
		
		else { 
			//to ensure O(n/2) complexity
			ListNode it = null;
			
			if (position >= this.size/2) {
				it = this.last;
				for (int counter = 0; counter < this.size()-position-1; counter++) {
					it = it.previous;
				}
			}
			else {
				it = this.first;
				for (int counter = 0; counter < position; counter++) {
					it = it.next;
				}
			}
			
			ListNode newNode = new ListNode(it.previous, value, it);
			it.previous.next = newNode;
			it.previous = newNode;
		}
		
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
		
		int indexCounter = 0;
		for (ListNode i = first; i != null; i = i.next) {
			if (i.value.equals(value)) return indexCounter;
			
			indexCounter++;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * If the element getting removed is at the beginning or end of the list, the complexity is O(1). Otherwise, the complexity is O(size/2).
	 * @param index position of the element which is removed
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in the range [0, size-1]
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index > this.size-1) 
			throw new IndexOutOfBoundsException(String.format("Legal indexes are between {} and {}. Your index was {}.", 0, this.size-1, index));
		
		if (index == 0) {
			this.first.next.previous = null;
			this.first = this.first.next;
		}
		
		else if (index == this.size) {
			this.last.previous.next = null;
			this.last = this.last.previous;
		}
		
		else { 
			//to ensure O(n/2) complexity
			ListNode it = null;
			
			if (index >= this.size/2) {
				it = this.last;
				for (int counter = 0; counter < this.size()-index-1; counter++) {
					it = it.previous;
				}
			}
			else {
				it = this.first;
				for (int counter = 0; counter < index; counter++) {
					it = it.next;
				}
			}
			
			it.previous.next = it.next;
			it.next.previous = it.previous;
		}
		
		this.modificationCount++;
		this.size--;
	}

	/**
	 * Creates a new ElementsGetter which is personalized to work with LinkedListIndexedCollection.
	 * @return the new ElementsGetter that contains reference to this collection
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexedElementsGetter(this, modificationCount);
	}

}
