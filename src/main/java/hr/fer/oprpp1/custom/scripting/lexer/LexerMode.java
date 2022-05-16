package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeration of two modes lexer can operate in.
 * Lexer is in <code>TEXT</code> mode when reading text outside of any tags.
 * Lexer is in <code>TAG</code> mode when reading text inside some tags (between {$ and $}).
 * @author Josip
 *
 */
public enum LexerMode {
	TEXT,
	TAG
}
