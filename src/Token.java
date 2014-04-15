/** @author Brian To (bxt5647) */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Valid Curl tokens
 */
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
	
	/**
	 * Helper for creating token {@link Pattern}s.
	 * 
	 * This makes a regex which swallows spaces before and after the expected
	 * match. The matching <code>innerPattern</code> will be accessible via the
	 * <code>id</code> group.
	 *  
	 * @param innerPattern regex to match the language lexems
	 * @return augmented <code>innerPattern</code> that swallows whitespace and provides a data accessible group.
	 */
	private static String capturedPattern(String innerPattern) {
		return String.format("^\\s*(?<id>%s)\\s*", innerPattern);
	}
	
	/**
	 * Constructs a token with specified regex {@link Pattern}.
	 * 
	 * @param pattern matching pattern
	 */
	private Token(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * Wrapper for {@link Pattern#matcher(CharSequence)}
	 * 
	 * @param input input string
	 * @return {@link Matcher} for this Token
	 */
	public Matcher matcher(String input) {
		return this.pattern.matcher(input);
	}
	
	/**
	 * This token's regex as
	 * @return this token's regex
	 */
	public String regex() {
		return this.pattern.toString();
	}
}
