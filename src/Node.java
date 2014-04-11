import java.util.Collection;
import java.util.Queue;


public abstract class Node {
	public abstract Node parse(Queue<TokenData> tokens);
	public abstract String html();

	public static TokenData consumeAndExpect(Queue<TokenData> tokens, Token expectedToken) {
		TokenData next = tokens.poll();
		Token actualToken = next.getToken();
		
		if (!expectedToken.equals(actualToken)) {
			throw new RuntimeException(String.format("Expected %s but got %s", expectedToken, actualToken));
		}
		
		return next;
	}
	
	public static boolean incomingTokenIs(Queue<TokenData> tokens, Token expected) {
		TokenData incoming = tokens.peek();
		Token actual = incoming.getToken();
		
		return expected.equals(actual);
	}
	
	public static String htmlFor(Collection<Node> nodes) {
		StringBuilder result = new StringBuilder();
		
		for (Node node : nodes) {
			result.append(node.html());
		}
		
		return result.toString();
	}
}
