import java.util.Iterator;
import java.util.regex.Matcher;

public class Lexer implements Iterator<TokenData> {

	private String input;
	
	public Lexer(String input) {
		this.input = input;
	}

	@Override
	public boolean hasNext() {
		return !this.input.matches("^\\s*$");
	}

	@Override
	public TokenData next() {
		for (Token token : Token.values()) {
			Matcher matcher = token.matcher(this.input);
			
			if (matcher.find()) {
				String data = matcher.group("id");
				
				this.input = this.input.replaceFirst(token.regex(), "");
				
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
