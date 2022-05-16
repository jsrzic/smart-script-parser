package hr.fer.oprpp1.custom.collections;

/**
 * Exception that occurs when trying to access an element from an empty stack.
 * @author Josip
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Auto-generated serial version ID of the exception.
	 */
	private static final long serialVersionUID = 735807516317944609L;
	
	
	/**
	 * Creates a new EmptyStackException.
	 * @param errorMessage information about what caused the exception to be thrown
	 */
	public EmptyStackException(String errorMessage) {
        super(errorMessage);
    }
}