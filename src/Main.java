/** @author Brian To (bxt5647) */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		File input = inputFile(args);
		String text = readFileToString(input);
		Lexer lexer = new Lexer(text);
		
		Queue<TokenData> tokens = new LinkedList<TokenData>();
		
		while (lexer.hasNext()) {
			tokens.offer(lexer.next());
		}
		
		Node root = new RootNode().parse(tokens);
		
		System.out.println(root.html());
	}

	private static File inputFile(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad arguments: make run CURL=<file.curl>");
			System.exit(-1);
		}
		
		File target = new File(args[0]);
		
		if (!target.exists()) {
			System.err.println(String.format("Could not find file %s", target.getName()));
			System.exit(-1);
		}
		
		return target;
	}
	
	private static String readFileToString(File input) {
		StringBuilder result = new StringBuilder();
		String line = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));
			
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			
			reader.close();
		} catch (Exception e) {
			System.err.println(String.format("An error occurred: %s", e.getMessage()));
			System.exit(-1);
		}
		
		return result.toString();
	}
}
