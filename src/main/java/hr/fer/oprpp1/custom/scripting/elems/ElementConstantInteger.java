package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing an element of type integer which can be found inside a tag. 
 * @author Josip
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * The actual value of the element.
	 */
	private int value;
	
	/**
	 * Creates a new integer element with given value.
	 * @param value value of the element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Returns value of the element.
	 * @return value of the element
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns value of the element in string representation.
	 */
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}
	
	/**
	 * Returns original string representation (as found in document text) of the element.
	 */
	@Override
	public String toString() {
		return asText();
	}
	
}
