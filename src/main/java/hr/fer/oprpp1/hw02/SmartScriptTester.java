package hr.fer.oprpp1.hw02;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Demo program which takes in document file path as a command line argument.
 * It checks if it can properly parse the given document using SmartScriptParser.
 * @author Josip
 *
 */
public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		String docBody = new String(
				 Files.readAllBytes(Paths.get(args[0])),
				 StandardCharsets.UTF_8
				);
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		System.out.println("Parsing successful: "  + same);
		System.out.println("Reconstructed document body:\n" + originalDocumentBody);
	}
}
