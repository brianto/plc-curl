# Curl: An Alternate Markup Language

## Implementation

- `root` the document root; Curl argument is the document's title; represented by the tags `<html>`, `<head>`, `<body>`, and `<title>`
- `p` paragraph; represented by the tag `<p>`
- `h` header; Curl argument is an integer n between 1 and 5; represented by the tags `<hn>`
- `ol` ordered list; represented by the tag `<ol>`
- `ul` unordered (bullet) list; represented by the tag `<ul>`
- `style` style of text contained inside; Curl argument is the word "italic" or the word "bold"; represented by a span/format construct or the old tags `<b>` or `<i>`
- `text` actual primitive text; if the text inside the braces is quoted, then it is copied verbatim to the HTML output without any changes nor any surrounding white space.

## Grammar

    Root ::= root "'"literal-text"'" "{" Unit* "}"
    Unit ::= Header | List | Paragraph | Style | Text
    Header ::= "h" integer "{" Unit* "}"
    List ::= ( "ol" | "ul" ) "{" Unit* "}"
    Paragraph ::= "p" "{" Unit* "}"
    Style ::= "style" ( "bold" | "italic" ) "{" Unit* "}"
    Text ::= "text" "{" ( word* | "'"literal-text"'" ) "}"

    Tokens Mentioned Above Without Quotes

    word :: longest-possible sequences of characters without white space
    (Text of this type is copied word-by-word, with blanks between, to output.)
    EXCEPTION: "<" is converted to "&lt;"; ">" to "&gt;"

    literal-text :: Any text (copied verbatim to output)
