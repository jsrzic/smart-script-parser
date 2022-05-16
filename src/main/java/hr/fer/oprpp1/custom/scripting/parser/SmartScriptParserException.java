package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception which can occur during parsing of a text using SmartScriptParser for various reasons, including exceptions thrown by the lexer.
 * @author Josip
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Auto-generated serial version ID of the exception.
	 */
	private static final long serialVersionUID = -7298940776722804518L;
	
	public SmartScriptParserException(String errorMessage) {
        super(errorMessage);
    }
	
	/**
	 * Creates a new SmartScriptParserException.
	 * @param errorMessage information about what caused the exception to be thrown
	 */
	public SmartScriptParserException(String errorMessage, Throwable other) {
        super(errorMessage, other);
    }
}
