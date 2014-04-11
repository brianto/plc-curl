import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;


/**
 * Assume everything is run from eclipse
 */
public class ExampleTest {

	@Test
	public void test01() throws Exception {
		String curl = resource("00.curl");
		String expected = resource("00.html");
		
		Lexer lexer = new Lexer(curl);
		
		Queue<TokenData> tokens = new LinkedList<TokenData>();
		while (lexer.hasNext()) {
			tokens.offer(lexer.next());
		}
		
		Node node = new RootNode().parse(tokens);
		
		assertEquals(expected, node.html());
	}
	
	private static String resource(String path) {
		File file = new File("test/resources/" + path);
		StringBuilder builder = new StringBuilder();
		String line;
		
		assertTrue(file.exists());
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		return builder.toString();
	}
}
