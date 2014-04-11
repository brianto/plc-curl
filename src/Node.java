import java.util.Collection;
import java.util.Queue;


public abstract class Node {
	public abstract Node parse(Queue<TokenData> tokens);
	public abstract String html();

	public static TokenData consumeAndExpect(Queue<TokenData> tokens, Token... expected) {
		TokenData next = tokens.poll();
		Token actual = next.getToken();
		
		for (Token e : expected) {
			if (e.equals(actual)) {
				return next;
			}
		}
		
		throw new RuntimeException(String.format("Expected %s but got %s", expected, actual));
	}
	
	public static boolean incomingTokenIs(Queue<TokenData> tokens, Token... expected) {
		TokenData incoming = tokens.peek();
		Token actual = incoming.getToken();
		
		for (Token e : expected) {
			if (e.equals(actual)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String htmlFor(Collection<Node> nodes) {
		StringBuilder result = new StringBuilder();
		
		for (Node node : nodes) {
			result.append(node.html());
		}
		
		return result.toString();
	}
}
