import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class RootNode extends Node {

	private List<Node> children = new LinkedList<Node>();
	
	private String title;
	
	@Override
	public RootNode parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.ROOT);
		
		this.title = consumeAndExpect(tokens, Token.TEXT_LITERAL).getData();
		
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
			this.children.add(new UnitNode().parse(tokens));
		}
		
		consumeAndExpect(tokens, Token.BRACE_RIGHT);

		if (!tokens.isEmpty()) {
			throw new RuntimeException("There should be no more symbols but more symbols found");
		}
		
		return this;
	}
	
	@Override
	public String html() {
		return String.format(
				"<html><head><title>%s</title></head><body>%s</body></html>",
				this.title,
				htmlFor(this.children));
	}
}
