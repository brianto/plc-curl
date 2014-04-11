import java.util.LinkedList;
import java.util.Queue;


public class TestUtil {
	private TestUtil() { }
	
	public static Queue<TokenData> lex(String input) {
		Lexer lexer = new Lexer(input);
		
		Queue<TokenData> tokens = new LinkedList<TokenData>();
		
		while (lexer.hasNext()) {
			tokens.offer(lexer.next());
		}
		
		return tokens;
	}
}
