package hr.fer.oprpp1.hw02.prob1;

/**
 * Enumeration describing two states in which Lexer can operate.
 * <b>BASIC</b> state sorts tokens in 4 groups given by TokenType enumeration and also allows escape sequences.
 * <b>EXTENDED</b> state sorts tokens in 2 categories: WORD and EOF, and doesn't allow escape sequences.
 * Both ignore whitespaces.
 * @author Josip
 */
public enum LexerState {
	BASIC, 
	EXTENDED
}