/** @author Brian To (bxt5647) */

import java.util.Queue;

public class TextNode extends Node {
	
	private String text;

	@Override
	public TextNode parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.TEXT);
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		if (incomingTokenIs(tokens, Token.TEXT_LITERAL)) {
			this.text = consumeAndExpect(tokens, Token.TEXT_LITERAL).getData();
		} else if (incomingTokenIsEscapedText(tokens)) {
			StringBuilder builder = new StringBuilder();
			
			while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
				TokenData token = tokens.poll();
				String text = sanitize(token.getData());
				
				builder.append(" " + text + " ");
			}
			
			this.text = builder.toString();
		} else {
			TokenData badToken = tokens.poll();
			
			throw new RuntimeException(String.format(
					"Unexpected %s in text node", badToken.getToken()));
		}
		
		consumeAndExpect(tokens, Token.BRACE_RIGHT);

		return this;
	}
	
	@Override
	public String html() {
		return this.text;
	}

	private static String sanitize(String text) {
		return text
				.replaceAll("\\<", "&lt;")
				.replaceAll("\\>", "&gt;");
	}
	
	private static boolean incomingTokenIsEscapedText(Queue<TokenData> tokens) {
		return !incomingTokenIs(tokens, Token.BRACE_LEFT, Token.BRACE_RIGHT, Token.TEXT_LITERAL);
	}
}
