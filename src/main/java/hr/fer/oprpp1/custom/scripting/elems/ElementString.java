package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing an element of type string which can be found inside a tag. 
 * @author Josip
 *
 */
public class ElementString extends Element {
	/**
	 * The actual value of the element.
	 */
	private String value;
	
	/**
	 * Creates a new string element with given value.
	 * @param value value of the element
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Returns value of the element.
	 * @return value of the element
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns value of the element in string representation.
	 */
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Returns original string representation (as found in document text) of the element.
	 */
	@Override
	public String toString() {
		String stringRepresentationWithEscape = value.replace("\\", "\\\\").replace("\"", "\\\"");
		return "\"" + stringRepresentationWithEscape + "\"";
	}
}
