package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.hw02.prob1.LexerException;


public class LexerTest {
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
	}
	
	private TokenKind[] generateTokens(String text) {
		Lexer lex = new Lexer(text);
		ArrayIndexedCollection tokenKindCollection = new ArrayIndexedCollection();
		while (lex.nextToken().getKind() != TokenKind.EOF) {
			tokenKindCollection.add(lex.getToken().getKind());
		}
		
		TokenKind[] tokenKindArray = Arrays.copyOf(tokenKindCollection.toArray(), tokenKindCollection.size(), TokenKind[].class);
		return tokenKindArray;
	}
	
	@Test
	public void testExample1() {
		String text = readExample(1);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT};
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample2() {
		String text = readExample(2);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT};
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample3() {
		String text = readExample(3);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT};
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample4() {
		String text = readExample(4);
		assertThrows(LexerException.class, () -> generateTokens(text));
	}
	
	@Test
	public void testExample5() {
		String text = readExample(5);
		assertThrows(LexerException.class, () -> generateTokens(text));
	}
	
	@Test
	public void testExample6() {
		String text = readExample(6);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.STRING, TokenKind.TAGEND};
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample7() {
		String text = readExample(7);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.STRING, TokenKind.TAGEND};
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample8() {
		String text = readExample(8);
		assertThrows(LexerException.class, () -> generateTokens(text));
	}
	
	@Test
	public void testExample9() {
		String text = readExample(9);
		assertThrows(LexerException.class, () -> generateTokens(text));
	}
	
	@Test
	public void testExample10() {
		String text = readExample(10);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.VARIABLE, TokenKind.INTEGER,  TokenKind.INTEGER,TokenKind.INTEGER,
				TokenKind.TAGEND,TokenKind.TEXT,TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.VARIABLE, TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME,
				TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.VARIABLE, TokenKind.INTEGER, TokenKind.INTEGER, TokenKind.INTEGER, TokenKind.TAGEND, 
				TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.VARIABLE, TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART,TokenKind.TAGNAME, TokenKind.VARIABLE, 
				TokenKind.VARIABLE, TokenKind.OPERATOR, TokenKind.FUNCTION,TokenKind.STRING, TokenKind.FUNCTION, TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.TAGEND}; 
		
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample11() {
		String text = readExample(11);
		TokenKind[] expected = new TokenKind[] {TokenKind.TAGSTART,TokenKind.TAGNAME, TokenKind.VARIABLE,TokenKind.INTEGER, TokenKind.VARIABLE,TokenKind.INTEGER, TokenKind.TAGEND, 
				TokenKind.TEXT, TokenKind.TAGSTART,TokenKind.TAGNAME, TokenKind.VARIABLE, TokenKind.INTEGER, TokenKind.VARIABLE, TokenKind.INTEGER, TokenKind.TAGEND, TokenKind.TEXT, 
				TokenKind.TAGSTART, TokenKind.TAGNAME, TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.TAGEND, TokenKind.TEXT, TokenKind.TAGSTART, TokenKind.TAGNAME,TokenKind.TAGEND}; 
		
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample12() {
		String text = readExample(12);
		TokenKind[] expected = new TokenKind[] {TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.VARIABLE, TokenKind.INTEGER,TokenKind.VARIABLE, TokenKind.INTEGER,
				TokenKind.TAGEND,TokenKind.TEXT,TokenKind.TAGSTART,TokenKind.TAGNAME, TokenKind.VARIABLE, TokenKind.INTEGER, TokenKind.VARIABLE, TokenKind.INTEGER, TokenKind.TAGEND,
				TokenKind.TEXT, TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.TAGEND}; 
		
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample13() {
		String text = readExample(13);
		TokenKind[] expected = new TokenKind[] {TokenKind.TEXT,TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.VARIABLE,TokenKind.DOUBLE,TokenKind.VARIABLE,
				TokenKind.STRING,TokenKind.TAGEND,TokenKind.TEXT ,TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.TAGEND};
		
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample14() {
		String text = readExample(14);
		TokenKind[] expected = new TokenKind[] {TokenKind.TAGSTART,TokenKind.TAGNAME ,TokenKind.VARIABLE ,TokenKind.INTEGER  ,TokenKind.INTEGER  ,TokenKind.INTEGER  ,
			TokenKind.TAGEND,TokenKind.TEXT ,TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.TAGEND};
		
		assertArrayEquals(generateTokens(text), expected);
	}
	
	@Test
	public void testExample15() {
		String text = readExample(15);
		TokenKind[] expected = new TokenKind[] {TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.VARIABLE, TokenKind.STRING,TokenKind.INTEGER,TokenKind.STRING,
			TokenKind.TAGEND,TokenKind.TEXT,TokenKind.TAGSTART,TokenKind.TAGNAME,TokenKind.TAGEND};
		
		assertArrayEquals(generateTokens(text), expected);
	}
}
