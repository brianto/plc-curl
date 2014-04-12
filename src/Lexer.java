import java.util.Iterator;
import java.util.regex.Matcher;

public class Lexer implements Iterator<TokenData> {

	private String input;
	private LexerState state;
	
	private static final Token[] TEXT_RESTRICTED_TOKENS = {
		Token.TEXT_ESCAPED, Token.TEXT_LITERAL, Token.BRACE_LEFT, Token.BRACE_RIGHT
	};
	
	public Lexer(String input) {
		this.input = input;
		this.state = LexerState.NORMAL;
	}

	@Override
	public boolean hasNext() {
		return !this.input.matches("^\\s*$");
	}

	@Override
	public TokenData next() {
		switch (this.state) {
		case NORMAL: return this.nextNormal();
		case TEXT: return this.nextText();
		}
		
		throw new RuntimeException("Illegal state: not in NORMAL nor NEXT");
	}
	
	private TokenData nextNormal() {
		for (Token token : Token.values()) {
			Matcher matcher = token.matcher(this.input);
			
			if (matcher.find()) {
				String data = matcher.group("id");
				
				this.input = this.input.replaceFirst(token.regex(), "");
				
				if (token.equals(Token.TEXT)) {
					this.state = LexerState.TEXT;
				}
				
				return new TokenData(token, data);
			}
		}
		
		throw new IllegalStateException(String.format("No token matches next input %s", this.input));
	}
	
	private TokenData nextText() {
		for (Token token : TEXT_RESTRICTED_TOKENS) {
			Matcher matcher = token.matcher(this.input);
			
			if (matcher.find()) {
				String data = matcher.group("id");
				
				this.input = this.input.replaceFirst(token.regex(), "");
				
				if (token.equals(Token.BRACE_RIGHT)) {
					this.state = LexerState.NORMAL;
				}
				
				return new TokenData(token, data);
			}
		}
		
		throw new IllegalStateException(String.format("No token matches next input %s", this.input));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from Lexer stream");
	}
}

enum LexerState {
	NORMAL,
	TEXT;
}
