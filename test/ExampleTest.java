/** @author Brian To (bxt5647) */

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
	public void test00() throws Exception {
		assertParsedCurlMatchesHtml("00.curl", "00.html");
	}
	
	@Test
	public void test01() throws Exception {
		assertParsedCurlMatchesHtml("01.curl", "01.html");
	}
	
	@Test
	public void test02() throws Exception {
		assertParsedCurlMatchesHtml("02.curl", "02.html");
	}
	
	private static void assertParsedCurlMatchesHtml(String curlFileLocation, String htmlFileLocation) {
		String curl = resource(curlFileLocation);
		String expected = resource(htmlFileLocation);
		
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
