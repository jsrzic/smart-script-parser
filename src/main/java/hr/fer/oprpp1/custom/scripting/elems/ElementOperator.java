package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing an element of type operator which can be found inside a tag. 
 * @author Josip
 *
 */
public class ElementOperator extends Element {
	/**
	 * The actual value of the element.
	 */
	private String symbol;
	
	/**
	 * Creates a new operator element with given value.
	 * @param value value of the element
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Returns value of the element.
	 * @return value of the element
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns value of the element in string representation.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Returns original string representation (as found in document text) of the element.
	 */
	@Override
	public String toString() {
		return symbol;
	}
}
