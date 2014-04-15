/** @author Brian To (bxt5647) */

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
	public void tokens() {
		assertTokenRegex(Token.ROOT, "id", " root ", "root");
		
		assertTokenRegex(Token.BRACE_LEFT, "id", " { ", "{");
		assertTokenRegex(Token.BRACE_RIGHT, "id", " } ", "}");
		
		assertTokenRegex(Token.TAG_PARAGRAPH, "id", " p ", "p");
	}
	
	@Test
	public void text() {
		assertTokenRegex(Token.TEXT, "id", " text ", "text");

		assertTokenRegex(Token.TEXT_LITERAL, "id", " 'cool, dawg' ", "cool, dawg");
		assertTokenRegex(Token.TEXT_LITERAL, "id", " '\thow you doin babe?\t' ", "\thow you doin babe?\t");
		
		assertTokenRegex(Token.TEXT_ESCAPED, "id", " swagtastic\t", "swagtastic");
	}
	
	@Test
	public void header() {
		assertTokenRegex(Token.TAG_HEADER, "id", " h ", "h");
		assertTokenRegex(Token.TAG_HEADER_LEVEL, "id", " 3 ", "3");
	}
	
	@Test
	public void style() {
		assertTokenRegex(Token.TAG_STYLE, "id", " style ", "style");
		assertTokenRegex(Token.TAG_STYLE_BOLD, "id", "  bold", "bold");
		assertTokenRegex(Token.TAG_STYLE_ITALIC, "id", "italic", "italic");
	}
	
	@Test
	public void list() {
		assertTokenRegex(Token.TAG_LIST_ORDERED, "id", " ol ", "ol");
		assertTokenRegex(Token.TAG_LIST_UNORDERED, "id", " ul ", "ul");
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
