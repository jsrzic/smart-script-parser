package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing an element of type variable which can be found inside a tag. 
 * @author Josip
 *
 */
public class ElementVariable extends Element {
	/**
	 * The actual value of the element.
	 */
	private String name;
	
	/**
	 * Creates a new variable element with given value.
	 * @param value value of the element
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Returns value of the element.
	 * @return value of the element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns value of the element in string representation.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Returns original string representation (as found in document text) of the element.
	 */
	@Override
	public String toString() {
		return name;
	}
}
