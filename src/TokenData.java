/** @author Brian To (bxt5647) */

/**
 * Data class for extracting {@link Lexer} tokens
 * 
 * When lexing, token match data is stored here for use later when
 * reconstructing the AST. This class is needed because simple lexing loses
 * data from the actual text since some tokens have many ways of matching. One
 * example is {@link Token.ESCAPED_TEXT}
 */
public class TokenData {

	private Token token;
	private String data;
	
	/**
	 * Constructs a token data object lol.
	 * 
	 * Wha'd you expect?
	 * 
	 * @param token token
	 * @param data any string data, usually of the {@link Token.matcher()}'s <code>id</code> group.
	 */
	public TokenData(Token token, String data) {
		this.token = token;
		this.data = data;
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public String getData() {
		return this.data;
	}
	
	/**
	 * For debugging.
	 */
	@Override
	public String toString() {
		return String.format("%s:%s", this.token, this.data);
	}
}
