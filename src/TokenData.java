
public class TokenData {

	private Token token;
	private String data;
	
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
	
	@Override
	public String toString() {
		return String.format("%s:%s", this.token, this.data);
	}
}
