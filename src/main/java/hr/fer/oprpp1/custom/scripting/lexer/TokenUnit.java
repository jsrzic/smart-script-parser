package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Lexical unit which groups one or multiple characters from some text.
 * @author Josip
 *
 */
public class TokenUnit {
	/**
	 * Type of the token. All possible types are given by TokenKind enumeration.
	 */
	private TokenKind kind;
	
	/**
	 * The actual value that this token holds. <code>null</code> if the token is EOF.
	 */
	private Object value;
	
	public TokenUnit(TokenKind kind, Object value) {
		this.kind = kind;
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
	 * @return type of the token as given by TokenKind enumeration
	 */
	public TokenKind getKind() {
		return this.kind;
	}
}
