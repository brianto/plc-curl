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
}
