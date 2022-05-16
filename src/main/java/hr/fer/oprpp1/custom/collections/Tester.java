package hr.fer.oprpp1.custom.collections;

/**
 * Interface which receives and value and accepts it if it passes the test as given by the test method.
 * @author Josip
 */
public interface Tester {
	/**
	 * Tests the received object based on some criteria.
	 * @param obj value which is being tested
	 * @return <code>true</code> if the value passes the test, <code>false</code> otherwise
	 */
	boolean test(Object obj);
}
