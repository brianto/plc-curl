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
	public void header() {
		assertHtmlMatchesCurl(new HeaderNode(), "h 3 { }", "<h3></h3>");
	}
	
	@Test
	public void list() {
		assertHtmlMatchesCurl(new ListNode(), "ul { }", "<ul></ul>");
		assertHtmlMatchesCurl(new ListNode(), "ol { }", "<ol></ol>");
	}
	
	@Test
	public void paragraph() {
		assertHtmlMatchesCurl(new ParagraphNode(), "p { }", "<p></p>");
	}
	
	@Test
	public void style() {
		assertHtmlMatchesCurl(new StyleNode(), "style bold { }", "<strong></strong>");
		assertHtmlMatchesCurl(new StyleNode(), "style italic { }", "<em></em>");
	}
	
	@Test
	public void text() {
		assertHtmlMatchesCurl(new TextNode(), "text { whats <up> dog? }", "whats &lt;up&gt; dog?");
		assertHtmlMatchesCurl(new TextNode(), "text { '<litterally>' }", "<litterally>");
	}
	
	public static void assertHtmlMatchesCurl(Node node, String curl, String expected) {
		Queue<TokenData> tokens = TestUtil.lex(curl);
		String actual = node.parse(tokens).html();
		
		assertEquals(expected, actual);
	}
}
