/** @author Brian To (bxt5647) */

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ListNode extends Node {
	
	private List<Node> children = new LinkedList<Node>();
	
	private String type;

	@Override
	public ListNode parse(Queue<TokenData> tokens) {
		this.type = consumeAndExpect(tokens, Token.TAG_LIST_ORDERED, Token.TAG_LIST_UNORDERED).getData();
		
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
			this.children.add(new UnitNode().parse(tokens));
		}
		
		consumeAndExpect(tokens, Token.BRACE_RIGHT);
		
		return this;
	}

	@Override
	public String html() {
		StringBuilder items = new StringBuilder();
		for (Node node : this.children) {
			items.append(String.format("<li>%s</li>", node.html()));
		}
		
		return String.format("<%s>%s</%s>", this.type, items, this.type);
	}
}
