package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	private LinkedListIndexedCollection withElementsCollection = new LinkedListIndexedCollection();
	private LinkedListIndexedCollection cleanCollection = new LinkedListIndexedCollection();
	
	@BeforeEach
	void setUp() {
		withElementsCollection.add("a");
		withElementsCollection.add("b");
		withElementsCollection.add("c");
		withElementsCollection.add("d");
	}
	
	@AfterEach
	void cleanUp() {
		cleanCollection = new LinkedListIndexedCollection();
	}
	
	@Test
	public void testSimpleConstructorSize() {
		assertEquals(cleanCollection.size(), 0);
	}
	
	@Test
	public void testConstructorOtherCollectionIsNull() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testConstructorFillElementsFromOtherCollection() {
		ArrayIndexedCollection dummyCollection = new ArrayIndexedCollection(withElementsCollection);
		assertArrayEquals(dummyCollection.toArray(), withElementsCollection.toArray());
	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(cleanCollection.isEmpty());
	}
	
	@Test
	public void testSize() {
		assertEquals(withElementsCollection.size(), 4);
	}
	
	@Test
	public void testAddNull() {
		assertThrows(NullPointerException.class, () -> cleanCollection.add(null));
	}
	
	@Test
	public void testAdd() {
		cleanCollection.add("a");
		assertEquals(cleanCollection.toArray()[0], "a");
	}
	
	@Test
	public void testContainsNull() {
		assertFalse(withElementsCollection.contains(null));
	}
	
	@Test
	public void testContainsTrue() {
		assertTrue(withElementsCollection.contains("b"));
	}
	
	@Test
	public void testContainsFalse() {
		assertFalse(withElementsCollection.contains("x"));
	}
	
	@Test
	public void testRemoveElementPresent() {
		cleanCollection.add("a");
		cleanCollection.add("c");
		cleanCollection.add("a");
		cleanCollection.add("b");
		boolean success = cleanCollection.remove("a");
		assertTrue(success);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"c","a","b"});
	}
	
	@Test
	public void testRemoveElementNotPresent() {
		cleanCollection.add("a");
		cleanCollection.add("c");
		cleanCollection.add("a");
		cleanCollection.add("b");
		boolean success = cleanCollection.remove("x");
		assertFalse(success);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"a","c","a","b"});
	}
	
	@Test
	public void testToArray() {
		assertEquals(withElementsCollection.toArray().length, 4);
		assertArrayEquals(withElementsCollection.toArray(), new String[] {"a", "b","c","d"});
	}
	
	@Test
	public void testForEach() {
		withElementsCollection.forEach(value -> cleanCollection.add(value));
		
		assertArrayEquals(withElementsCollection.toArray(), cleanCollection.toArray());
	}
	
	@Test
	public void testAddAll() {
		cleanCollection.addAll(withElementsCollection);
		
		assertArrayEquals(withElementsCollection.toArray(), cleanCollection.toArray());
	}
	
	@Test
	public void testClear() {
		cleanCollection.addAll(withElementsCollection);
		cleanCollection.clear();
		
		assertEquals(cleanCollection.size(), 0);
	}
	
	@Test
	public void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> withElementsCollection.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> withElementsCollection.get(-1));
	}
	
	@Test
	public void testGet() {
		assertEquals(withElementsCollection.get(1), "b");
	}
	
	@Test
	public void testInsertNull() {
		assertThrows(NullPointerException.class, () -> cleanCollection.insert(null, 0));
	}
	
	@Test
	public void testInsertInvalidPosition() {
		assertThrows(IndexOutOfBoundsException.class, () -> cleanCollection.insert("x", 7));
	}
	
	@Test
	public void testInsert() {
		cleanCollection.addAll(withElementsCollection);
		cleanCollection.insert("x", 1);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"a", "x", "b", "c", "d"});
		assertEquals(cleanCollection.size(), 5);
	}
	
	@Test
	public void testInsertBeginning() {
		cleanCollection.addAll(withElementsCollection);
		cleanCollection.insert("x", 0);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"x", "a", "b", "c", "d"});
		assertEquals(cleanCollection.size(), 5);
	}
	
	@Test
	public void testInsertEnd() {
		cleanCollection.addAll(withElementsCollection);
		cleanCollection.insert("x", 4);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"a", "b", "c", "d", "x"});
		assertEquals(cleanCollection.size(), 5);
	}
	
	@Test
	public void testIndexOfValuePresent() {
		assertEquals(withElementsCollection.indexOf("b"), 1);
	}
	
	@Test
	public void testIndexOfValueNotPresent() {
		assertEquals(withElementsCollection.indexOf("x"), -1);
	}
	
	@Test
	public void testIndexOfValueNull() {
		assertEquals(withElementsCollection.indexOf(null), -1);
	}
	
	@Test
	public void testRemoveInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> withElementsCollection.remove(10));
		assertThrows(IndexOutOfBoundsException.class, () -> withElementsCollection.remove(-10));
	}
	
	@Test
	public void testRemoveAtIndex() {
		cleanCollection.addAll(withElementsCollection);
		cleanCollection.remove(1);
		assertArrayEquals(cleanCollection.toArray(), new String[] {"a","c","d"});
		assertEquals(cleanCollection.size(), 3);
	}
}
