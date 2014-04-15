/** @author Brian To (bxt5647) */

import static org.junit.Assert.assertEquals;

import java.util.Queue;

import org.junit.Test;

public class LexerTest {

	@Test
	public void simpleTokens() {
		Queue<TokenData> tokens = TestUtil.lex("root 'ohai' { p { wazzup bro? } }");
		
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
		
		tokens = TestUtil.lex("root'ohai'{ok}");

		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "ohai", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ok", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());

		tokens = TestUtil.lex("root 'ohai' {\n\tok\n}");
		
		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "ohai", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ok", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
	}
	
	@Test
	public void contextSwitch() {
		Queue<TokenData> tokens = TestUtil.lex("root 'tricky' { text { p h ul ol bold italic } }");
		
		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "tricky", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT, tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "p", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "h", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ul", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "ol", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "bold", tokens.poll());
		assertTokenData(Token.TEXT_ESCAPED, "italic", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
	}
	
	@Test
	public void specialLiterals() {
		Queue<TokenData> tokens = TestUtil.lex("root'{}'{text{'{}'}}");
		
		assertTokenData(Token.ROOT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "{}", tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT, tokens.poll());
		assertTokenData(Token.BRACE_LEFT, tokens.poll());
		assertTokenData(Token.TEXT_LITERAL, "{}", tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
		assertTokenData(Token.BRACE_RIGHT, tokens.poll());
	}
	
	private static void assertTokenData(Token token, String data, TokenData actual) {
		assertEquals(token, actual.getToken());
		assertEquals(data, actual.getData());
	}
	
	private static void assertTokenData(Token token, TokenData actual) {
		assertEquals(token, actual.getToken());
	}
}
