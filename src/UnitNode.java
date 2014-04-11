import java.util.Queue;


public class UnitNode extends Node {
	private Node child;

	@Override
	public UnitNode parse(Queue<TokenData> tokens) {
		if (incomingTokenIs(tokens, Token.TAG_HEADER)) {
			this.child = new HeaderNode().parse(tokens);
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TAG_LIST_ORDERED) ||
			incomingTokenIs(tokens, Token.TAG_LIST_UNORDERED)) {
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TAG_PARAGRAPH)) {
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TAG_STYLE)) {
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TEXT)) {
			return this;
		}
		
		TokenData badToken = tokens.poll();
		throw new RuntimeException(String.format(
				"Could not match %s to any production rule prefix", badToken.getToken()));
	}

	@Override
	public String html() {
		return child.html();
	}
}
