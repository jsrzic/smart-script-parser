package hr.fer.oprpp1.hw02.prob1;

/**
 * Lexical unit which groups one or multiple characters from some text.
 * @author Josip
 *
 */
public class Token {
	/**
	 * Type of the token. All possible types are given by TokenType enumeration.
	 */
	private TokenType type;
	
	/**
	 * The actual value that this token holds. <code>null</code> if the token is EOF.
	 */
	private Object value;
	
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for token value.
	 * @return value that this token represents
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Getter for token type.
	 * @return type of the token as given by TokenType enumeration
	 */
	public TokenType getType() {
		return this.type;
	}
}
