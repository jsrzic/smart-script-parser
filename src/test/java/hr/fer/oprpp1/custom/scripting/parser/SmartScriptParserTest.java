package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	private boolean checkParse(String docBody) {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		return same;
	}
	
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
	
	@Test
	public void testOneTextNode() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = parser.getDocumentNode();
		assertEquals(docNode.numberOfChildren(), 1);
		assertTrue(docNode.getChild(0) instanceof TextNode);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testOneTextNode2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = parser.getDocumentNode();
		assertEquals(docNode.numberOfChildren(), 1);
		assertTrue(docNode.getChild(0) instanceof TextNode);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testOneTextNode3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = parser.getDocumentNode();
		assertEquals(docNode.numberOfChildren(), 1);
		assertTrue(docNode.getChild(0) instanceof TextNode);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testIllegalEscape() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testIllegalEscape2() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testMultilineString() {
		String text = readExample(6);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testMultilineString2() {
		String text = readExample(7);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testIllegalEscape3() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testIllegalCharacterInEchoTag() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testVanillaExample() {
		String text = readExample(10);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testTooManyEndTags() {
		String text = readExample(11);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testTooFewEndTags() {
		String text = readExample(12);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testComplexForLoop() {
		String text = readExample(13);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testForLoopGoodExample() {
		String text = readExample(14);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testForLoopGoodExample2() {
		String text = readExample(15);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testForLoopGoodExample3() {
		String text = readExample(16);
		assertTrue(checkParse(text));
	}
	
	@Test
	public void testForLoopVariableNameMissing() {
		String text = readExample(17);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testForLoopVariableNameMissing2() {
		String text = readExample(18);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testForLoopFunctionElement() {
		String text = readExample(19);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testForLoopTooManyArguments() {
		String text = readExample(20);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testForLoopTooFewArguments() {
		String text = readExample(21);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
	@Test
	public void testForLoopTooManyArguments2() {
		String text = readExample(22);
		assertThrows(SmartScriptParserException.class, () -> checkParse(text));
	}
	
}
