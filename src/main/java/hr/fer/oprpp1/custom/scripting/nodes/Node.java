package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Class representing a node in document model.
 * @author Josip
 *
 */
public class Node {
	/**
	 * Collection containing all children of this node.
	 * It is <code>null</code> until first child is added.
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Adds a child node to this node.
	 * @param child node that is added as a child node
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns number of children of this node.
	 * If 
	 * @return number of children of this node
	 */
	public int numberOfChildren() {
		return children == null ? 0 : children.size();
	}
	
	/**
	 * Returns child of this node at a given index from the children collection.
	 * @param index position of the wanted child node
	 * @return wanted child node
	 */
	public Node getChild(int index) {
		if (children == null)
			throw new IndexOutOfBoundsException("Collection of children is empty.");
		
		return (Node)children.get(index);
	}
	
}
