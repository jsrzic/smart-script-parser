package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeration of all possible kinds of tokens.
 * Used for lexical analysis and parsing.
 * @author Josip
 *
 */
public enum TokenKind {
	EOF,
	VARIABLE,
	INTEGER,
	DOUBLE,
	STRING,
	FUNCTION,
	OPERATOR,
	TEXT,
	TAGSTART,
	TAGEND,
	TAGNAME
}
