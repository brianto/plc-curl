import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.Test;


public class NodeTest {

	@Test
	public void root() {
		assertHTMLMatchesCurl(
				new RootNode(),
				"root 'kowloon' { }",
				"<html><head><title>kowloon</title></head><body></body></html>");
	}
	
	@Test
	public void header() {
		assertHTMLMatchesCurl(new HeaderNode(), "h 3 { }", "<h3></h3>");
	}
	
	public static void assertHTMLMatchesCurl(Node node, String curl, String expected) {
		Queue<TokenData> tokens = TestUtil.lex(curl);
		String actual = node.parse(tokens).html();
		
		assertEquals(expected, actual);
	}
}
