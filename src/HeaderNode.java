import java.util.List;
import java.util.Queue;


public class HeaderNode extends Node {
	
	private List<Node> children;

	private int level;

	@Override
	public Node parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.TAG_HEADER);
		
		TokenData levelToken = consumeAndExpect(tokens, Token.TAG_HEADER_LEVEL);
		this.level = Integer.parseInt(levelToken.getData());
		
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
			this.children.add(new UnitNode().parse(tokens));
		}

		consumeAndExpect(tokens, Token.BRACE_RIGHT);
		
		return this;
	}
	
	@Override
	public String html() {
		return String.format("<h%s>%s</h%s>", this.level, htmlFor(this.children), this.level);
	}
}
