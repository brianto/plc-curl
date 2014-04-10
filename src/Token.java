import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum Token {
	ROOT(capturedPattern("root")),
	BRACE_LEFT(capturedPattern("\\{")),
	BRACE_RIGHT(capturedPattern("\\}")),
	TEXT_LITERAL("^\\s*'(?<text>[^\\{\\}']+)'\\s*"),
	TEXT_ESCAPED("^\\s*(?<text>[^\\{\\}\\s']+)\\s*"),
	TAG_HEADER("^\\s*(?<id>h)\\s*(?<level>\\d)\\s*"),
	TAG_STYLE("^\\s*(?<id>style)\\s*(?<type>bold|italic)\\s*"),
	TAG_PARAGRAPH(capturedPattern("p")),
	TAG_LIST(capturedPattern("ol|ul"));

	private Pattern pattern;
	
	private static String capturedPattern(String innerPattern) {
		return String.format("^\\s*(?<id>%s)\\s*", innerPattern);
	}
	
	private Token(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		System.out.println(this.pattern.toString());
	}
	
	public Matcher matcher(String input) {
		return this.pattern.matcher(input);
	}
}
