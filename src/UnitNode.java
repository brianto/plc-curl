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
			this.child = new ListNode().parse(tokens);
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TAG_PARAGRAPH)) {
			this.child = new ParagraphNode().parse(tokens);
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TAG_STYLE)) {
			this.child = new StyleNode().parse(tokens);
			return this;
		}
		
		if (incomingTokenIs(tokens, Token.TEXT)) {
			this.child = new TextNode().parse(tokens);
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
