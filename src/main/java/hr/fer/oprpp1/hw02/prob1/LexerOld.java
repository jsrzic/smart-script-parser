//package hr.fer.oprpp1.hw02.prob1;
//
//
///**
// * Implementation of a basic lexer.
// * Lexer is an object which takes text as input and outputs different types of tokens (in this case: WORD, NUMBER, SYMBOL and EOF tokens).
// * @author Josip
// *
// */
//public class LexerOld {
//	/**
//	 * Text data stored in an array of characters.
//	 */
//	private char[] data;
//	
//	/**
//	 * Last generated token.
//	 */
//	private Token token;
//	
//	/**
//	 * Position of the next character to be analyzed by the lexer.
//	 */
//	private int currentIndex;
//	
//	/**
//	 * One of two states lexer can operate in, given by {@link LexerState}.
//	 */
//	private LexerState state;
//	
//	/**
//	 * Creates a new lexer with initialized private fields.
//	 * @param text input text which is being parsed
//	 * @throws NullPointerException if input text is <code>null</code>
//	 */
//	public LexerOld(String text) {
//		if (text == null)
//			throw new NullPointerException("Input text can not be null.");
//		
//		this.data = text.toCharArray();
//		this.currentIndex = 0;
//		this.state = LexerState.BASIC;
//	}
//
//	/**
//	 * Generates next token from the text, stores it in private field <code>token</code> and returns it.
//	 * @return newly generated token
//	 * @throws LexerException if called after EOF token was generated, or if an error occurred during lexical analysis
//	 */
//	public Token nextToken() {
//		TokenType type = null;
//		Object value = null;
//		boolean tokenCompleted = false;
//		boolean escapeState = false;
//		StringBuilder sb = new StringBuilder();
//		
//		while(!tokenCompleted) {
//			if (this.currentIndex > data.length) {
//				throw new LexerException("There are no more tokens to be generated.");
//			}
//			
//			if (this.currentIndex == data.length) {
//				this.currentIndex++;
//				return this.token = new Token(TokenType.EOF, null);
//			}
//			
//			Character lexElement = data[this.currentIndex++];
//			boolean isWhitespace = lexElement.equals('\r') || lexElement.equals('\n') || lexElement.equals('\t') || lexElement.equals(' ');
//			
//			if (lexElement.equals('#')) {
//				tokenCompleted = true;
//				
//				if (type != TokenType.SYMBOL && type != null) {
//					this.currentIndex--;
//				}
//				else {
//					this.setState(this.state == LexerState.BASIC ? LexerState.EXTENDED : LexerState.BASIC);
//					type = TokenType.SYMBOL;
//					value = lexElement;
//				}
//				
//				continue;
//			}
//			
//			if (this.state == LexerState.BASIC) {
//				if (lexElement.equals('\\') && !escapeState) {
//					if (this.currentIndex == data.length || !(Character.isDigit(data[this.currentIndex]) || (data[this.currentIndex] == '\\'))) {
//						throw new LexerException("Escape sign must be followed by a digit or a backslash.");
//					}
//					
//					else {
//						escapeState = true;
//						continue;
//					}
//				}
//				
//				else if (isWhitespace) {
//					if (type == null) {
//						continue;
//					}
//					
//					tokenCompleted = true;
//				}
//				
//				else if (Character.isLetter(lexElement) || escapeState) {
//					if (escapeState) escapeState = false;
//					
//					if (type != TokenType.WORD && type != null) {
//						tokenCompleted = true;
//						this.currentIndex--;
//					}
//					else if (this.currentIndex == data.length) {
//						type = TokenType.WORD;
//						sb.append(lexElement);
//						tokenCompleted = true;
//					}
//					else {
//						type = TokenType.WORD;
//						sb.append(lexElement);
//					}
//				}
//				
//				else if (Character.isDigit(lexElement)) {
//					if (type != TokenType.NUMBER && type != null) {
//						tokenCompleted = true;
//						this.currentIndex--;
//					}
//					else if (this.currentIndex == data.length) {
//						tokenCompleted = true;
//						type = TokenType.NUMBER;
//						sb.append(lexElement);
//					}
//					else {
//						type = TokenType.NUMBER;
//						sb.append(lexElement);
//					}
//				}
//				
//				else {
//					tokenCompleted = true;
//					
//					if (type != TokenType.SYMBOL && type != null) {
//						this.currentIndex--;
//					}
//					else {
//						type = TokenType.SYMBOL;
//						value = lexElement;
//					}
//				}
//			}
//			
//			else if (this.state == LexerState.EXTENDED) {
//				if (isWhitespace) {
//					if (type == null) {
//						continue;
//					}
//					
//					tokenCompleted = true;
//				}
//				
//				else {
//					type = TokenType.WORD;
//					sb.append(lexElement);
//					if (this.currentIndex == data.length) {
//						tokenCompleted = true;
//					}
//				}
//				
//			}
//			
//		}
//		
//		
//		if (type == TokenType.NUMBER) {
//			try {
//				value = Long.valueOf(sb.toString());
//			} catch (NumberFormatException e) {
//				throw new LexerException("Number can not be parsed as a Long number.");
//			}
//		}
//		
//		else if (type == TokenType.WORD) {
//			value = sb.toString();
//		}
//		
//		return this.token = new Token(type, value);
//	}
//	
//	/**
//	 * Returns the last generated token.
//	 * @return the last generated token
//	 */
//	public Token getToken() {
//		return this.token;
//	}
//	
//	/**
//	 * Sets state of operation of this lexer to the given state.
//	 * @param state new state of operation
//	 * @throws NullPointerException if new state is <code>null</code>
//	 */
//	public void setState(LexerState state) {
//		if (state == null) {
//			throw new NullPointerException("State can not be null.");
//		}
//		
//		this.state = state;
//	}
//	
//	/**
//	 * Simple demonstration of lexer usage.
//	 */
//	public static void main(String[] args) {
//        LexerOld lexer = new LexerOld("#k123#3aa#2s da#234s");
//        while (lexer.nextToken().getType() != TokenType.EOF){
//            System.out.println(lexer.getToken().getType() + " " + lexer.getToken().getValue());
//        }
//        System.out.println(lexer.token.getType() + " " + lexer.token.getValue());
//    }
//}
