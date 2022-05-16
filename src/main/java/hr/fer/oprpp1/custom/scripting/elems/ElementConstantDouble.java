package hr.fer.oprpp1.custom.scripting.elems;


/**
 * Class representing an element of type double which can be found inside a tag. 
 * @author Josip
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * The actual value of the element.
	 */
	private double value;
	
	/**
	 * Creates a new double element with given value.
	 * @param value value of the element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Returns value of the element.
	 * @return value of the element
	 */
	public double getValue() {
		return value;
	}
	
	
	/**
	 * Returns value of the element in string representation.
	 */
	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}
	
	/**
	 * Returns original string representation (as found in document text) of the element.
	 */
	@Override
	public String toString() {
		return asText();
	}
	
}
