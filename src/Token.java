import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum Token {
	ROOT(capturedPattern("root")),
	BRACE_LEFT(capturedPattern("\\{")),
	BRACE_RIGHT(capturedPattern("\\}")),
	TAG_HEADER(capturedPattern("h")),
	TAG_HEADER_LEVEL(capturedPattern("\\d")),
	TAG_STYLE(capturedPattern("style")),
	TAG_STYLE_BOLD(capturedPattern("bold")),
	TAG_STYLE_ITALIC(capturedPattern("italic")),
	TAG_PARAGRAPH(capturedPattern("p")),
	TAG_LIST_UNORDERED(capturedPattern("ul")),
	TAG_LIST_ORDERED(capturedPattern("ol")),
	TEXT(capturedPattern("text")),
	TEXT_LITERAL("^\\s*'(?<id>[^\\{\\}']+)'\\s*"),
	TEXT_ESCAPED(capturedPattern("[^\\{\\}\\s']+")),
	;

	private Pattern pattern;
	
	private static String capturedPattern(String innerPattern) {
		return String.format("^\\s*(?<id>%s)\\s*", innerPattern);
	}
	
	private Token(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}
	
	public Matcher matcher(String input) {
		return this.pattern.matcher(input);
	}
	
	public String regex() {
		return this.pattern.toString();
	}
}
