/** @author Brian To (bxt5647) */

import java.util.Collection;
import java.util.Queue;

/**
 * Curl abstract syntax tree node
 */
public abstract class Node {
	/**
	 * Parses incoming tokens from lexer and stores relevant node data.
	 * 
	 * @param tokens {@link Queue} of tokens from {@link Lexer}
	 * @return current node, <code>this</code>
	 */
	public abstract Node parse(Queue<TokenData> tokens);
	
	/**
	 * Converts current node (and children if any) to an HTML {@link String}
	 * @return {@link String} representing this node as HTML
	 */
	public abstract String html();

	/**
	 * Consumes a token from the token queue.
	 * 
	 * This method will raise an exception if one of the the expected {@link Token} types do not match the incoming token type.
	 * 
	 * The {@link Queue#poll()}'d {@link TokenData} object is returned for use.
	 * 
	 * @param tokens queue of {@link Lexer} tokens
	 * @param expected list of valid expected {@link Token} types
	 * @return next {@link TokenData} in queue
	 */
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
	
	/**
	 * Checks to see if next token is one of the expected types without {@link Queue#poll()}ing.
	 * 
	 * @param tokens queue of {@link Lexer} tokens
	 * @param expected valid {@link Token} types
	 * @return next token is one of the expected token types
	 */
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
	
	/**
	 * Helper for converting a collection of {@link Node} objects to HTML.
	 * 
	 * This is a limited implementation that only concantenates the result of all {@link Node#html()} calls.
	 * 
	 * @param nodes nodes to {@link Node#html()} and concatenate
	 * @return HTML for nodes
	 */
	public static String htmlFor(Collection<Node> nodes) {
		StringBuilder result = new StringBuilder();
		
		for (Node node : nodes) {
			result.append(node.html());
		}
		
		return result.toString();
	}
}
