package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.TokenKind;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Implementation of a parser which takes in document text and produces a document tree model based on the text.
 * @author Josip
 *
 */
public class SmartScriptParser {
	/**
	 * Root node in the document tree model produced by the parser after its done parsing the text.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Creates a new instance of the parser.
	 * Internally, it creates a lexer which takes in the same document body and generates tokens.
	 * Then, a method is called which takes in tokens from the lexer and produces document tree model. 
	 * @param documentBody text which is being parsed
	 * @throws SmartScriptParserException if any error occurs during parsing
	 */
	public SmartScriptParser(String documentBody) {
		if (documentBody == null)
			throw new SmartScriptParserException("Input text can not be null.");
		
		Lexer lexer = new Lexer(documentBody);
		this.documentNode = new DocumentNode();
		try {
			this.parseDocument(lexer);
		} catch (Exception e) {
			throw new SmartScriptParserException("An error has occured during parsing.", e);
		}
	}
	
	/**
	 * Returns root document node of the document tree model.
	 * @return root document node of the document tree model
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	/**
	 * Method which does the actual parsing of the tokens produced by the lexer
	 * @param lexer {@link Lexer} object which generates tokens from the text
	 * @throws SmartScriptParserException for many different kinds of errors which can occur during parsing of the tokens
	 */
	private void parseDocument(Lexer lexer) {
		ObjectStack stack = new ObjectStack();
		stack.push(this.documentNode);
		
		lexer.nextToken();
		while(lexer.getToken().getKind() != TokenKind.EOF) {
			switch (lexer.getToken().getKind()) {
				case TAGSTART -> {
					if(lexer.nextToken().getKind() != TokenKind.TAGNAME) {
						throw new SmartScriptParserException("Tag name missing.");
					}
					if(lexer.getToken().getValue().toString().equalsIgnoreCase("FOR")) {
						parseForTag(lexer, stack);
					}
					else if(lexer.getToken().getValue().toString().equalsIgnoreCase("=")) {
						parseEchoTag(lexer, stack);
					}
					else if(lexer.getToken().getValue().toString().equalsIgnoreCase("END")) {
						parseEndTag(lexer, stack);
					}
					else {
						throw new SmartScriptParserException("Invalid tag name.");
					}
				}
				
				case TEXT -> {
					((Node)stack.peek()).addChildNode(new TextNode(lexer.getToken().getValue().toString()));
					lexer.nextToken();
				}
				
				default -> throw new SmartScriptParserException("Unexpected token kind: " + lexer.getToken().getKind());
			}	
		}
		
		if (stack.size() > 1) {
			throw new SmartScriptParserException("Document contains more opened non-empty tags than END tags.");
		}
	}
	
	/**
	 * Helper method used for parsing out a single sequence of tokens which denote a ForLoopNode.
	 * @param lexer lexer used for generation of tokens
	 * @param stack stack of Nodes used for building the document tree model
	 */
	private void parseForTag(Lexer lexer, ObjectStack stack) {
		ElementVariable variable;
		Element startExpression;
		Element endExpression;
		Element stepExpression;
		ArrayIndexedCollection varNumberStringList = new ArrayIndexedCollection();
		
		if(lexer.nextToken().getKind() != TokenKind.VARIABLE) {
			throw new SmartScriptParserException("First element in FOR tag must be a variable.");
		}
		variable = new ElementVariable(lexer.getToken().getValue().toString());
		
		while(lexer.nextToken().getKind() != TokenKind.TAGEND) {
			if(lexer.getToken().getKind() == TokenKind.EOF) {
				throw new SmartScriptParserException("For loop tag not closed");
			}
			
			
			switch (lexer.getToken().getKind()) {
				case VARIABLE -> {
					varNumberStringList.add(new ElementVariable(lexer.getToken().getValue().toString()));
				}
				case INTEGER -> {
					varNumberStringList.add(new ElementConstantInteger(Integer.valueOf(lexer.getToken().getValue().toString())));
				}
				case DOUBLE -> {
					varNumberStringList.add(new ElementConstantDouble(Double.valueOf(lexer.getToken().getValue().toString())));
				}
				case STRING -> {
					varNumberStringList.add(new ElementString(lexer.getToken().getValue().toString()));
				}
				
				default -> throw new SmartScriptParserException("Invalid for loop tag element.");
			}
		}
		
		lexer.nextToken();
		
		if (varNumberStringList.size() < 2) {
			throw new SmartScriptParserException("Too few elements in for loop tag.");
		}
		else if (varNumberStringList.size() > 3) {
			throw new SmartScriptParserException("Too many elements in for loop tag.");
		}
		else {
			startExpression = (Element)varNumberStringList.get(0);
			endExpression = (Element)varNumberStringList.get(1);
			stepExpression = varNumberStringList.size() == 3 ? (Element)varNumberStringList.get(2) : null;
			
			ForLoopNode forloop = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
			((Node)stack.peek()).addChildNode(forloop);
			stack.push(forloop);
		}
	}
	
	/**
	 * Helper method used for parsing out a single sequence of tokens which denote an EchoNode.
	 * @param lexer lexer used for generation of tokens
	 * @param stack stack of Nodes used for building the document tree model
	 */
	private void parseEchoTag(Lexer lexer, ObjectStack stack) {
		ArrayIndexedCollection elementList = new ArrayIndexedCollection();
		
		while(lexer.nextToken().getKind() != TokenKind.TAGEND) {
			if(lexer.getToken().getKind() == TokenKind.EOF) {
				throw new SmartScriptParserException("Echo tag not closed");
			}
			switch (lexer.getToken().getKind()) {
				case VARIABLE -> {
					elementList.add(new ElementVariable(lexer.getToken().getValue().toString()));
				}
				case INTEGER -> {
					elementList.add(new ElementConstantInteger(Integer.valueOf(lexer.getToken().getValue().toString())));
				}
				case DOUBLE -> {
					elementList.add(new ElementConstantDouble(Double.valueOf(lexer.getToken().getValue().toString())));
				}
				case STRING -> {
					elementList.add(new ElementString(lexer.getToken().getValue().toString()));
				}
				case FUNCTION -> {
					elementList.add(new ElementFunction(lexer.getToken().getValue().toString()));
				}
				case OPERATOR -> {
					elementList.add(new ElementOperator(lexer.getToken().getValue().toString()));
				}
				default -> throw new SmartScriptParserException("Invalid echo element.");
			}
		}
		
		lexer.nextToken();
		
		if(!elementList.isEmpty()) {
			Element[] elements = Arrays.copyOf(elementList.toArray(), elementList.size(), Element[].class);
			((Node)stack.peek()).addChildNode(new EchoNode(elements));
		}
		else {
			throw new SmartScriptParserException("Echo tag is empty.");
		}
	}
	
	/**
	 * Helper method used for parsing out a single sequence of tokens which denote an end tag for the ForLoopNode.
	 * @param lexer lexer used for generation of tokens
	 * @param stack stack of Nodes used for building the document tree model
	 */
	private void parseEndTag(Lexer lexer, ObjectStack stack) {
		if(lexer.nextToken().getKind() != TokenKind.TAGEND) {
			throw new SmartScriptParserException("End tag not closed");
		}
		else {
			stack.pop();
			if (stack.isEmpty()) {
				throw new SmartScriptParserException("Document contains more END tags than opened non-empty tags.");
			}
			lexer.nextToken();
		}
	}
	
	/**
	 * Demonstration of a simple parsing procedure.
	 * After the original text has been parsed, the root document node has been successfully created and filled with children.
	 * Then, that same document node is converted to its original string representation.
	 * After that, that newly reconstructed text is now parsed by a second parser and document tree models of those 2 parsers are compared.
	 * If same, our parsing procedure has been successful.
	 */
	public static void main(String[] args) {
//		String docBody = "This is sample text.\r\n"
//				+ "{$ FOR i 1 10 1 $}\r\n"
//				+ " This is \\{$= i $}-th time this message is generated.\r\n"
//				+ "{$END$}\r\n"
//				+ "{$FOR i 0 10 2 $}\r\n"
//				+ " sin({$=i$}^2) = {$= i i * @sin \" \\\" 0.000\" @decfmt $}\r\n"
//				+ "{$END$}";
		String docBody = "{$ fOr a 2 3 $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		System.out.println(same);
		System.out.println(originalDocumentBody);
	}
	
}
