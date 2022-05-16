package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node representing document text (which is found outside of tags bound by {$ and $}).
 * @author Josip
 *
 */
public class TextNode extends Node {
	/**
	 * Value of the text node.
	 */
	private String text;
	
	/**
	 * Creates a new text node with given value.
	 * @param text value of the next node, actual document text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Returns text value of the text node.
	 * @return text value of the text node
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns original string representation (as found in document text) of the text node.
	 */
	@Override 
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}
	
}
