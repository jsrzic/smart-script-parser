package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Lexer is a object which takes document text as input and generates tokens from all different kinds of text elements and tags.
 * Generates tokens which can be parsed by <code>SmartScriptParser</code>.
 * 
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
	private TokenUnit token;
	
	/**
	 * Position of the next character to be analyzed by the lexer.
	 */
	private int currentIndex;
	
	/**
	 * One of two modes lexer can operate in, given by {@link LexerMode}
	 */
	private LexerMode mode;
	
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
		
		if (this.data.length >= 2 && this.data[0] == '{' && this.data[1] == '$') {
			this.mode = LexerMode.TAG;
		}
		else {
			this.mode = LexerMode.TEXT;			
		}
	}
	
	/**
	 * Generates next token from the text, stores it in private field <code>token</code> and returns it.
	 * @return newly generated token
	 * @throws LexerException if called after EOF token was generated, or if an error occurred during lexical analysis
	 */
	public TokenUnit nextToken() {
		StringBuilder sb = new StringBuilder();
		
		if (this.token != null && this.token.getKind() == TokenKind.EOF) {
			throw new LexerException("There are no more tokens to be generated.");
		}
		
		
		if (this.mode == LexerMode.TEXT) {
			//TEXT MODE
			
			if (this.currentIndex == data.length) {
				return this.token = new TokenUnit(TokenKind.EOF, null);
			}
			
			Character currentChar;
			while(this.currentIndex < this.data.length && !isTagStart()) {
				currentChar = data[currentIndex];
				
				if (currentChar.equals('\\')) {
					if (this.currentIndex + 1 < this.data.length 
						&& (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{')) {
						
						currentChar = data[++currentIndex];
					}
					
					else
						throw new LexerException("Escape character in document text must be followed by \\ or {");
				}
				sb.append(currentChar);
				this.currentIndex++;
			}
			if (isTagStart()) {
				this.setMode(LexerMode.TAG);
			}
			return this.token = new TokenUnit(TokenKind.TEXT, sb.toString());
		}
		
		else {
			//TAG mode
			
			clearWhitespaces();
			
			if (this.currentIndex == data.length) {
				return this.token = new TokenUnit(TokenKind.EOF, null);
			}
			
			if (isTagStart()) {
				this.currentIndex += 2;
				this.token = new TokenUnit(TokenKind.TAGSTART, "{$");
			}
			
			else if (isTagEnd()) {
				this.mode = LexerMode.TEXT;
				this.currentIndex += 2;
				this.token = new TokenUnit(TokenKind.TAGEND, "$}");
			}
						
			else if (data[currentIndex] == '=') {
				this.token = readEquals(sb);
			}
				
			else if (Character.isLetter(data[currentIndex])) {
				this.token = readVariable(sb);
			}
			
			else if (Character.isDigit(data[currentIndex])) {
				this.token = readNumber(sb);
			}
			
			else if (data[currentIndex] == '-') {
				this.token = readMinus(sb);
			}
			
			else if (data[currentIndex] == '@') {
				this.token = readFunction(sb);
			}
			
			else if (data[currentIndex] == '\"') {
				this.token = readString(sb);
			}
			
			else if (isOperator()) {
				this.token = readOperator(sb);
			}
			
			else {
				throw new LexerException("Unsupported character found in tag: " + data[currentIndex]);
			}
									
		}
		
		return this.token;
	}
	
	
	/**
	 * Returns the last generated token.
	 * @return the last generated token
	 */
	public TokenUnit getToken() {
		return this.token;
	}
	
	/**
	 * Sets mode of operation of this lexer to the given mode.
	 * @param mode new mode of operation
	 * @throws NullPointerException if new mode is <code>null</code>
	 */
	public void setMode(LexerMode mode) {
		if (mode == null) {
			throw new NullPointerException("Mode can not be null.");
		}
		
		this.mode = mode;
	}
	
	/**
	 * Helper function for determining whether current index is pointing to the start of a tag.
	 * @return <code>true</code> if current index is pointing to the start of a tag, <code>false</code> otherwise
	 */
	private boolean isTagStart() {
		return this.currentIndex + 1 < this.data.length 
				&& data[currentIndex] == '{' 
				&& data[currentIndex+1] == '$';
	}
	
	/**
	 * Helper function for determining whether current index is pointing to the end of a tag.
	 * @return <code>true</code> if current index is pointing to the end of a tag, <code>false</code> otherwise
	 */
	private boolean isTagEnd() {
		return this.currentIndex + 1 < this.data.length 
				&& data[currentIndex] == '$' 
				&& data[currentIndex+1] == '}';
	}
	
	/**
	 * Helper function for determining whether current index is pointing to an element which is of type operator.
	 * Supported operators: +, -, *, /, ^
	 * @return <code>true</code> if current index is pointing to an operator, <code>false</code> otherwise
	 */
	private boolean isOperator() {
		return data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*' 
				|| data[currentIndex] == '/' || data[currentIndex] == '^';
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
	 * Extracts token of kind VARIABLE from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind VARIABLE with String value contained inside StringBuilder <code>sb</code>
	 */
	private TokenUnit readVariable(StringBuilder sb) {
		sb.append(data[currentIndex]);
		this.currentIndex++;
		while(this.currentIndex < this.data.length &&(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			sb.append(data[currentIndex]);
			this.currentIndex++;
		}
		
		String value = sb.toString();
		TokenKind kind = this.token.getKind() == TokenKind.TAGSTART ? TokenKind.TAGNAME : TokenKind.VARIABLE;
		
		return new TokenUnit(kind, value);
	}
	
	/**
	 * Extracts token of kind DOUBLE or INTEGER from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind DOUBLE or INTEGER with String value contained inside StringBuilder <code>sb</code>
	 * @throws LexerException if decimal number syntax is invalid
	 */
	private TokenUnit readNumber(StringBuilder sb) {
		boolean isDecimal = false;
		sb.append(data[currentIndex]);
		this.currentIndex++;
		while(this.currentIndex < this.data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') && !isDecimal) {
			if (data[currentIndex] == '.') {
				if (this.currentIndex + 1 < this.data.length && Character.isDigit(data[currentIndex + 1])) {
					isDecimal = true;
				}
				else {
					throw new LexerException("Invalid decimal number syntax.");
				}
			}
			
			sb.append(data[currentIndex]);
			this.currentIndex++;		
		}
		
		while(this.currentIndex < this.data.length && Character.isDigit(data[currentIndex]) && isDecimal) {
			sb.append(data[currentIndex]);
			this.currentIndex++;
		}
		
		Object value;
		TokenKind kind;
		
		if (isDecimal) {
			value = Double.valueOf(sb.toString());
			kind = TokenKind.DOUBLE;
		}
		else {
			value = Integer.valueOf(sb.toString());
			kind = TokenKind.INTEGER;
		}
		return new TokenUnit(kind, value);
	}
	
	/**
	 * Extracts token of kind TAGNAME with value of '=' from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind TAGNAME with String value of '='
	 * @throws LexerException if '=' is not positioned exactly after the start of a tag
	 */
	private TokenUnit readEquals(StringBuilder sb) {
		if (this.token.getKind() == TokenKind.TAGSTART) {
			this.currentIndex++;
			return new TokenUnit(TokenKind.TAGNAME, "=");
		}
		else {
			throw new LexerException("= is not a valid operator nor variable name.");
		}
	}
	
	/**
	 * Extracts token of value '-' from the element current index is pointing to.
	 * If character '-' is positioned immediately behind element of kind DOUBLE or INTEGER, the following DECIMAL or INTEGER element becomes negative
	 * and token of kind DOUBLE or INTEGER is returned.
	 * Else, character '-' is regarded as an operator and token of kind OPERATOR is returned.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind TAGNAME with String value of '='
	 * @throws LexerException if decimal number syntax is invalid
	 */
	private TokenUnit readMinus(StringBuilder sb) {
		sb.append('-');
		this.currentIndex++;
		
		if (this.currentIndex < this.data.length && Character.isDigit(data[currentIndex]) ) {
			return readNumber(sb);
		}
		
		else {
			return new TokenUnit(TokenKind.OPERATOR, '-');
		}
	}
	
	/**
	 * Extracts token of kind FUNCTION from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind FUNCTION with String value contained inside StringBuilder <code>sb</code>
	 * @throws LexerException if there is no variable name after @ character, or if variable syntax is invalid
	 */
	private TokenUnit readFunction(StringBuilder sb) {
		this.currentIndex++;
		if (Character.isLetter(data[currentIndex])) {
			return new TokenUnit(TokenKind.FUNCTION, readVariable(sb).getValue());
		}
		else {
			throw new LexerException("Invalid function syntax.");
		}
	}
	
	/**
	 * Extracts token of kind STRING from the element current index is pointing to.
	 * Allows escape sequences inside string element: \\, \", \n, \t, \r
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind STRING with String value contained inside StringBuilder <code>sb</code>
	 * @throws LexerException if an invalid escape sequence occurs, or if string tag isn't closed
	 */
	private TokenUnit readString(StringBuilder sb) {
		this.currentIndex++;
		boolean closed = false;
		while (!closed && this.currentIndex < this.data.length) {
			if (data[currentIndex] == '\"') {
				this.currentIndex++;
				closed = true;
			}
			else if (data[currentIndex] == '\\') {
				if (this.currentIndex + 1 < this.data.length 
					&& (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\"' 
					|| data[currentIndex + 1] == 'n' || data[currentIndex + 1] == 't' || data[currentIndex + 1] == 'r')) {
					
					if(data[currentIndex + 1] == 'n') {
						sb.append("\n");
					}
					else if(data[currentIndex + 1] == 't') {
						sb.append("\t");
					}
					else if(data[currentIndex + 1] == 'r') {
						sb.append("\r");
					}
					else {
						sb.append(data[currentIndex + 1]);
					}
					
					this.currentIndex += 2;
					
				}
					
				else {
					throw new LexerException("Invalid escape character sequence inside string tag.");
				}
			}
			
			else {
				sb.append(data[currentIndex++]);
			}
			
		}
		
		if (this.currentIndex == this.data.length && !closed) {
			throw new LexerException("String not closed.");
		}
		
		return new TokenUnit(TokenKind.STRING, sb.toString());
	}
	
	/**
	 * Extracts token of kind OPERATOR from the element current index is pointing to.
	 * @param sb StringBuilder used for token value generation
	 * @return new token of kind OPERATOR with String value contained inside StringBuilder <code>sb</code>
	 */
	private TokenUnit readOperator(StringBuilder sb) {
		return new TokenUnit(TokenKind.OPERATOR, data[currentIndex++]);
	}
	
	/**
	 * Simple demonstration of lexer usage.
	 */
	public static void main(String[] args) {
		Lexer lex = new Lexer("This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}");
		
		while (lex.nextToken().getKind() != TokenKind.EOF) {
			System.out.println(lex.getToken().getKind() + " " + lex.getToken().getValue());
		}
	}
}
		

