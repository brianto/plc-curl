import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

public class TokenTest {

	@Test
	public void spaces() {
		assertTokenRegex(Token.ROOT, "id", "root", "root");
		assertTokenRegex(Token.ROOT, "id", "   root   ", "root");
		assertTokenRegex(Token.ROOT, "id", "\t\nroot\n\t", "root");
	}
	
	@Test
	public void root() {
		assertTokenRegex(Token.ROOT, "id", " root ", "root");
	}
	
	@Test
	public void braceLeft() {
		assertTokenRegex(Token.BRACE_LEFT, "id", " { ", "{");
	}
	
	@Test
	public void braceRight() {
		assertTokenRegex(Token.BRACE_RIGHT, "id", " } ", "}");
	}
	
	@Test
	public void textLiteral() {
		assertTokenRegex(Token.TEXT_LITERAL, "text", " 'cool, dawg' ", "cool, dawg");
		assertTokenRegex(Token.TEXT_LITERAL, "text", " '\thow you doin babe?\t' ", "\thow you doin babe?\t");
	}
	
	@Test
	public void textEscaped() {
		assertTokenRegex(Token.TEXT_ESCAPED, "text", " swagtastic\t", "swagtastic");
	}
	
	@Test
	public void header() {
		assertTokenRegex(Token.TAG_HEADER, "id", "h 3", "h");
		assertTokenRegex(Token.TAG_HEADER, "level", "h 3", "3");
	}
	
	@Test
	public void style() {
		assertTokenRegex(Token.TAG_STYLE, "id", "style   italic", "style");
		assertTokenRegex(Token.TAG_STYLE, "type", "style   italic", "italic");
		assertTokenRegex(Token.TAG_STYLE, "type", "stylebold", "bold");
	}
	
	@Test
	public void paragraph() {
		assertTokenRegex(Token.TAG_PARAGRAPH, "id", " p ", "p");
	}
	
	@Test
	public void list() {
		assertTokenRegex(Token.TAG_LIST, "id", " ol ", "ol");
		assertTokenRegex(Token.TAG_LIST, "id", " ul ", "ul");
	}
	
	/**
	 * Token regex assertion helper
	 * 
	 * @param token
	 * @param groupName
	 * @param input
	 * @param expected
	 */
	private static void assertTokenRegex(Token token, String groupName, String input, String expected) {
		Matcher matcher = token.matcher(input);
		
		assertTrue(
				String.format("%s with regex %s could not match %s", token, matcher, input),
				matcher.find());
		
		String actual = matcher.group(groupName);
		
		assertEquals(
				String.format("'%s' expected, but '%s' found", expected, actual),
				expected,
				actual);
	}
}
