import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class StyleNode extends Node {

	private List<Node> children = new LinkedList<Node>();

	private String style;
	
	private static final Map<String, String> STYLE_TO_TAG_MAPPINGS = new HashMap<String, String>();
	
	static {
		STYLE_TO_TAG_MAPPINGS.put("bold", "b"); // Should really be <strong>
		STYLE_TO_TAG_MAPPINGS.put("italic", "i"); // Should really be <em>
	}

	@Override
	public StyleNode parse(Queue<TokenData> tokens) {
		consumeAndExpect(tokens, Token.TAG_STYLE);
		
		this.style = consumeAndExpect(tokens, Token.TAG_STYLE_BOLD, Token.TAG_STYLE_ITALIC).getData();
		
		consumeAndExpect(tokens, Token.BRACE_LEFT);
		
		while (!incomingTokenIs(tokens, Token.BRACE_RIGHT)) {
			this.children.add(new UnitNode().parse(tokens));
		}

		consumeAndExpect(tokens, Token.BRACE_RIGHT);
		
		return this;
	}

	@Override
	public String html() {
		String tag = STYLE_TO_TAG_MAPPINGS.get(this.style);
		
		return String.format("<%s>%s</%s>", tag, htmlFor(this.children), tag);
	}
}
