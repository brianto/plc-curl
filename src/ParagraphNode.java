/** @author Brian To (bxt5647) */

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ParagraphNode extends Node {
	private List<Node> children = new LinkedList<Node>();

	@Override
	public ParagraphNode parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.TAG_PARAGRAPH);
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
			this.children.add(new UnitNode().parse(tokens));
		}

		consumeAndExpect(tokens, Token.BRACE_RIGHT);
		
		return this;
	}

	@Override
	public String html() {
		return String.format("<p>%s</p>", htmlFor(this.children));
	}
}
