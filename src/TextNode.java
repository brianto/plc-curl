import java.util.Queue;


public class TextNode extends Node {
	
	private String text;

	@Override
	public TextNode parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.TEXT);
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		if (incomingTokenIs(tokens, Token.TEXT_LITERAL)) {
			this.text = consumeAndExpect(tokens, Token.TEXT_LITERAL).getData();
		} else if (incomingTokenIs(tokens, Token.TEXT_ESCAPED)) {
			StringBuilder builder = new StringBuilder();
			
			while (incomingTokenIs(tokens, Token.TEXT_ESCAPED)) {
				TokenData token = tokens.poll();
				String text = sanitize(token.getData());
				
				builder.append(text);
				
				if (incomingTokenIs(tokens, Token.TEXT_ESCAPED)) {
					builder.append(' ');
				}
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
}
