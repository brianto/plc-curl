/** @author Brian To (bxt5647) */

import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.Test;

public class NodeTest {

	@Test
	public void root() {
		assertHtmlMatchesCurl(
				new RootNode(),
				"root 'kowloon' { }",
				"<html><head><title>kowloon</title></head><body></body></html>");
	}
	
	@Test
	public void text() {
		assertHtmlMatchesCurl(new TextNode(), "text { whats <up> dog? }", " whats  &lt;up&gt;  dog? ");
		assertHtmlMatchesCurl(new TextNode(), "text { '<litterally>' }", "<litterally>");
	}
	
	@Test
	public void header() {
		assertHtmlMatchesCurl(new HeaderNode(), "h 3 { }", "<h3></h3>");
		
		assertHtmlMatchesCurl(new HeaderNode(), "h 1 { h 2 { text { nest! } } }", "<h1><h2> nest! </h2></h1>");
	}
	
	@Test
	public void list() {
		assertHtmlMatchesCurl(new ListNode(), "ul { }", "<ul></ul>");
		assertHtmlMatchesCurl(new ListNode(), "ol { }", "<ol></ol>");
		
		assertHtmlMatchesCurl(new ListNode(), "ol { text { a } text { b } }", "<ol><li> a </li><li> b </li></ol>");
	}
	
	@Test
	public void paragraph() {
		assertHtmlMatchesCurl(new ParagraphNode(), "p { }", "<p></p>");
		
		assertHtmlMatchesCurl(new ParagraphNode(), "p { text { oh shit } }", "<p> oh  shit </p>");
	}
	
	@Test
	public void style() {
		assertHtmlMatchesCurl(new StyleNode(), "style bold { }", "<b></b>");
		assertHtmlMatchesCurl(new StyleNode(), "style italic { }", "<i></i>");
		
		assertHtmlMatchesCurl(new StyleNode(), "style italic { text { italic } }", "<i> italic </i>");
	}
	
	public static void assertHtmlMatchesCurl(Node node, String curl, String expected) {
		Queue<TokenData> tokens = TestUtil.lex(curl);
		String actual = node.parse(tokens).html();
		
		assertEquals(expected, actual);
	}
}
