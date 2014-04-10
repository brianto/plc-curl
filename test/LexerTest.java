import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

public class LexerTest {

	@Test
	public void simpleTokens() {
		Queue<TokenData> tokens = lex("root 'ohai' { p { wazzup bro? } }");
		
		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "ohai", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TAG_PARAGRAPH, tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "wazzup", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "bro?", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
	}
	
	@Test
	public void spaces() {
		Queue<TokenData> tokens;
		
		tokens = lex("root'ohai'{ok}");

		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "ohai", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ok", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());

		tokens = lex("root 'ohai' {\n\tok\n}");
		
		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "ohai", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ok", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
	}
	
	private static void assertTokenData(Token token, String data, TokenData actual) {
		assertEquals(token, actual.getToken());
		assertEquals(data, actual.getData());
	}
	
	private static void assertTokenData(Token token, TokenData actual) {
		assertEquals(token, actual.getToken());
	}
	
	private static Queue<TokenData> lex(String input) {
		Lexer lexer = new Lexer(input);
		
		Queue<TokenData> tokens = new LinkedList<TokenData>();
		
		while (lexer.hasNext()) {
			tokens.offer(lexer.next());
		}
		
		return tokens;
	}
}
