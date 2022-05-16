package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of a basic lexer.
 * Lexer is an object which takes text as input and outputs different types of tokens (in this case: WORD, NUMBER, SYMBOL and EOF tokens).
 * @author Josip
 *
 */
public class Lexer {
	/**
	 * Text data stored in an array of characters.
	 */
	private char[] data;
	
	/**
	 * Last generated token.
	 */
	private Token token;
	
	/**
	 * Position of the next character to be analyzed by the lexer.
	 */
	private int currentIndex;
	
	/**
	 * One of two states lexer can operate in, given by {@link LexerState}.
	 */
	private LexerState state;
	
	/**
	 * Creates a new lexer with initialized private fields.
	 * @param text input text which is being parsed
	 * @throws NullPointerException if input text is <code>null</code>
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException("Input text can not be null.");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}

	/**
	 * Generates next token from the text, stores it in private field <code>token</code> and returns it.
	 * @return newly generated token
	 * @throws LexerException if called after EOF token was generated, or if an error occurred during lexical analysis
	 */
	public Token nextToken() {
		StringBuilder sb = new StringBuilder();
		
		if (this.token != null && this.token.getType() == TokenType.EOF) {
			throw new LexerException("There are no more tokens to be generated.");
		}
		
		clearWhitespaces();
		
		if (this.currentIndex == data.length) {
			return this.token = new Token(TokenType.EOF, null);
		}
		
		if (state == LexerState.BASIC) {
			if (data[currentIndex] == '#') {
				currentIndex++;
				setState(LexerState.EXTENDED);
				this.token = new Token(TokenType.SYMBOL, '#');
			}
			
			else if (data[currentIndex] == '\\' && !isValidEscape()) {
				throw new LexerException("Invalid escape sequence.");
			}
			
			else if (isWord() || isValidEscape()) {
				this.token = readWord(sb);
			}
			
			else if (isNumber()) {
				this.token = readNumber(sb);
			}
			
			else {
				this.token = readSymbol();
			}
			
		}
		
		else {
			if (data[currentIndex] == '#') {
				currentIndex++;
				setState(LexerState.BASIC);
				this.token = new Token(TokenType.SYMBOL, '#');
			}
			
			else {
				this.token = readWordExtended(sb);
			}
		}
		
		return this.token;
		
	}
	
	/**
	 * Returns the last generated token.
	 * @return the last generated token
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Extracts token of type SYMBOL from the element current index is pointing to.
	 * @return new token of type SYMBOL
	 */
	private Token readSymbol() {
		return new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex++]));
	}
	
	/**
	 * Extracts token of type NUMBER from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of type NUMBER
	 */
	private Token readNumber(StringBuilder sb) {
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		Long value;
		try {
			value = Long.valueOf(sb.toString());
		} catch (NumberFormatException e) {
			throw new LexerException("Number can not be parsed as a Long number.");
		}
		
		return new Token(TokenType.NUMBER, value);
	}
	
	/**
	 * Extracts token of type WORD from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of type WORD
	 */
	private Token readWord(StringBuilder sb) {
		while (currentIndex < data.length && (isWord() || isValidEscape())) {
			if(isValidEscape()) {
				currentIndex++;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return new Token(TokenType.WORD, sb.toString());
	}
	
	/**
	 * Extracts token of type WORD from the element current index is pointing to if lexer state is {@link LexerState#EXTENDED}.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of type WORD
	 */
	private Token readWordExtended(StringBuilder sb) {
		while (currentIndex < data.length && !isWhitespace() && data[currentIndex] != '#') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return new Token(TokenType.WORD, sb.toString());
	}

	
	/**
	 * Sets state of operation of this lexer to the given state.
	 * @param state new state of operation
	 * @throws NullPointerException if new state is <code>null</code>
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("State can not be null.");
		}
		
		this.state = state;
	}
	
	/**
	 * Checks if current index is pointing to the start of a number.
	 */
	private boolean isNumber() {
		return Character.isDigit(data[currentIndex]);
	}
	
	/**
	 * Checks if current index is pointing to the start of a word.
	 */
	private boolean isWord() {
		return Character.isLetter(data[currentIndex]);
	}
	
	/**
	 * Checks if current index is pointing to the start of valid escape sequence.
	 */
	private boolean isValidEscape() {
		return currentIndex + 1 < data.length && data[currentIndex] == '\\' &&
				(Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\');
	}
	
	/**
	 * Helper function for determining whether current index is pointing to a whitespace character.
	 * Characters recognized as whitespace: \n, \r, \t, ' '
	 * @return <code>true</code> if current index is pointing to a whitespace character, <code>false</code> otherwise
	 */
	private boolean isWhitespace() {
		return data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t' || data[currentIndex] == ' ';
	}
	
	/**
	 * Helper function that increments current index as long as it is pointing to a whitespace character.
	 * Characters recognized as whitespace: \n, \r, \t, ' '
	 */
	private void clearWhitespaces() {
		while (this.currentIndex < this.data.length && isWhitespace()) {
			this.currentIndex++;
		}
	}
	
	/**
	 * Simple demonstration of lexer usage.
	 */
	public static void main(String[] args) {
		Lexer lexer = new Lexer("diasjxoi 5.2 kwdo kwd123## ab\\8.");
        while (lexer.nextToken().getType() != TokenType.EOF){
            System.out.println(lexer.getToken().getType() + " " + lexer.getToken().getValue());
        }
        System.out.println(lexer.token.getType() + " " + lexer.token.getValue());
    }
}
