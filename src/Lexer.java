/** @author Brian To (bxt5647) */

import java.util.Iterator;
import java.util.regex.Matcher;

/**
 * Takes a {@link String} of Curl and produces a stream of {@link TokenData}.
 * 
 * This lexer works in two modes due to the oddities in tokenizing raw text.
 * If in normal mode, tokens that represent tags (p, h, style) take precedence.
 * However, in text mode, those text tokens aren't text tokens, but text. This
 * lexer would interpret text as those more specific texts if not in the text
 * context. Creating a separate text-only token state fixes this problem.
 */
public class Lexer implements Iterator<TokenData> {

	private String input;
	private LexerState state;
	
	/**
	 * 
	 */
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
	
	/**
	 * Grabs the next matching token in normal mode and returns the token as {@link TokenData}
	 *  
	 * @return {@link TokenData} for next token
	 */
	private TokenData nextNormal() {
		for (Token token : Token.values()) {
			Matcher matcher = token.matcher(this.input);
			
			if (matcher.find()) {
				String data = matcher.group("id");
				
				this.input = this.input.replaceFirst(token.regex(), "");
				
				// Go into text mode if we see the text mode token
				if (token.equals(Token.TEXT)) {
					this.state = LexerState.TEXT;
				}
				
				return new TokenData(token, data);
			}
		}
		
		throw new IllegalStateException(String.format("No token matches next input %s", this.input));
	}

	/**
	 * Grabs the next matching token in text mode and returns the token as {@link TokenData}
	 *  
	 * @return {@link TokenData} for next token
	 */
	private TokenData nextText() {
		for (Token token : TEXT_RESTRICTED_TOKENS) {
			Matcher matcher = token.matcher(this.input);
			
			if (matcher.find()) {
				String data = matcher.group("id");
				
				this.input = this.input.replaceFirst(token.regex(), "");
				
				// Get out of text mode and back into normal mode
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
