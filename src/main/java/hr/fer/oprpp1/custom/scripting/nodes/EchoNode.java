package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Node representing an empty tag with name '='.
 * @author Josip
 *
 */
public class EchoNode extends Node {
	/**
	 * Array of elements stored in this node.
	 * Those are all of the elements which can be found between opening and closing tags (denoted by {$= ... $}). 
	 */
	private Element[] elements;
	
	/**
	 * Creates a new echo node with given elements.
	 * @param elements array of elements which are stored inside this echo node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Returns array of elements which can be found between opening and closing tags of this echo node.
	 * @return array of elements that are stored in this echo node
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Returns original string representation (as found in document text) of the echo node.
	 */
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String prefix = "";
		for (int i = 0; i < this.elements.length; i++) {
			sb.append(prefix);
			prefix = " ";
			sb.append(elements[i].toString());
		}
		return "{$= " + sb.toString() + " $}";
	}
	
}
