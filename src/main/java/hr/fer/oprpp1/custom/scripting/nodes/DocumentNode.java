package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node representing the whole document.
 * Parent node of all nodes in the document model tree.
 * @author Josip
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Returns original string representation (as found in document text) of the entire document.
	 */
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i));
		}
		
		return sb.toString();
	}
	
	/**
	 * Checks if this document node is equal to some other document node.
	 * @return <code>true</code> if both document nodes have the same string representation (as given by <code>toString()</code>), <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof DocumentNode)) {
			return false;
		}
		DocumentNode otherDocNode = (DocumentNode)other;
		
		return this.toString().equals(otherDocNode.toString());
	}
	
}
