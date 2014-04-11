import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.Test;


public class NodeTest {

	@Test
	public void root() {
		Queue<TokenData> tokens = TestUtil.lex("root 'kowloon' { }");
		RootNode node = new RootNode().parse(tokens);
		
		assertEquals("<html><head><title>kowloon</title></head><body></body></html>", node.html());
	}
	
	@Test
	public void header() {
		Queue<TokenData> tokens = TestUtil.lex("h 3 { }");
		HeaderNode node = new HeaderNode().parse(tokens);
		
		assertEquals("<h3></h3>", node.html());
	}
}
