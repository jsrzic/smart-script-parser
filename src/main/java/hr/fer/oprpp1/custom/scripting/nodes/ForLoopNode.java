package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Node representing a for-loop construction consisted of a FOR and an END tag.
 * @author Josip
 *
 */
public class ForLoopNode extends Node {
	/**
	 * First element of the FOR tag. Must be of type ElementVariable.
	 */
	private ElementVariable variable;
	
	/**
	 * Second element of the FOR tag.
	 */
	private Element startExpression;
	
	/**
	 * Third element of the FOR tag.
	 */
	private Element endExpression;
	
	/**
	 * Fourth element of the FOR tag.
	 * It can be <code>null</code> if this element is not present in the FOR tag.
	 */
	private Element stepExpression;
	
	/**
	 * Creates a new ForLoopNode and initializes its elements.
	 * @throws NullPointerException if any element besides stepExpression is <code>null</code>
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("When making a ForLoopNode, only stepExpression can be null.");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns first element of the FOR tag.
	 * @return first element of the FOR tag
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns second element of the FOR tag.
	 * @return second element of the FOR tag
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns third element of the FOR tag.
	 * @return third element of the FOR tag
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns fourth element of the FOR tag.
	 * @return fourth element of the FOR tag
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
		
	/**
	 * Returns original string representation (as found in document text) of the for-loop node.
	 */
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(variable.toString());
		sb.append(" ");
		sb.append(startExpression.toString());
		sb.append(" ");
		sb.append(endExpression.toString());
		sb.append(" ");
		if (stepExpression != null) {
			sb.append(stepExpression.toString());
		}
		sb.append(" $}");
		
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i));
		}
		
		sb.append("{$END$}");
		
		return sb.toString();
	}
	
}
