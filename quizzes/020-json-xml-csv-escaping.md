# Quiz 020: Escaping strings in JSON, XML and CSV

**Original label:** Question 4; quiz week not supplied.  
**Topic:** Storing printable ASCII and escaping syntax characters.  
[Back to quiz index](README.md)

## Question

A client wishes to store some string data persistently. This data is encoded in ASCII and is expected to include all printable ASCII characters. Which format would **not** be suitable for this purpose?

1. JSON, as it does not support some ASCII characters, such as the square brackets "[" and "]".
2. XML, as it does not support some ASCII characters, such as the less than and greater than signs "<" and ">".
3. XML, JSON, and CSV all support the entire set of ASCII characters, provided they are properly escaped.
4. CSV, as it does not support some ASCII characters, such as the comma "," and newline "\n".

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 3, interpreted within the question’s scope of printable ASCII.** All three formats can represent these characters. A character used as syntax is not necessarily forbidden as data.

Escaping tells the parser: **this character belongs to the value; it is not a structural delimiter.**

### JSON: backslash escapes inside strings

| Original character | JSON string representation |
| --- | --- |
| Double quote `"` | `\"` |
| Backslash `\` | `\\` |
| Line feed | `\n` |
| Tab | `\t` |

Inside a JSON string, brackets `[]`, braces `{}`, `<`, `>` and `&` need no escaping. For example, `{"text":"[hello]"}` is valid. `\[` is not a valid JSON escape. See [RFC 8259, section 7](https://www.rfc-editor.org/rfc/rfc8259#section-7).

### XML: entity references

| Original character | Entity reference |
| --- | --- |
| `<` | `&lt;` |
| `>` | `&gt;` |
| `&` | `&amp;` |
| `"` | `&quot;` |
| `'` | `&apos;` |

In ordinary element text, escape `<` and `&`. `>` can usually remain literal, but must be escaped in the sequence `]]>`. Quotes can remain literal in element text; in an attribute, escape whichever quote character encloses the value. XML does not use `\` for this purpose. See [XML 1.0, section 2.4](https://www.w3.org/TR/xml/#syntax).

### Same value, two formats

Original value:

```text
She said "A < B & C".
```

JSON file content:

```json
{"text": "She said \"A < B & C\"."}
```

XML file content:

```xml
<text>She said "A &lt; B &amp; C".</text>
```

The XML quotes above need no escaping because they are inside element text. In a double-quoted attribute:

```xml
<message text="She said &quot;A &lt; B &amp; C&quot;."/>
```

Parsing either format recovers the original value. These examples show actual file contents, not Java string literals; embedding them in Java source introduces an additional escaping layer.

### CSV: quote the field and double inner quotes

For RFC 4180-style CSV, enclose a field containing commas, quotes or line breaks in double quotes. Represent each quote inside it as `""`.

Original field: `She said "Hi", then left.`

```csv
"She said ""Hi"", then left."
```

A line break can occur inside a quoted field. CSV does not generally use `\n` as an escape for a line break. See [RFC 4180, section 2](https://www.rfc-editor.org/rfc/rfc4180#section-2).

### Why the distractors fail

- **JSON brackets:** literal brackets are allowed inside strings.
- **XML angle brackets:** represent them as text using entity references.
- **CSV commas and newlines:** quoted fields can contain them. A newline is a control character, not printable ASCII.

### Scope caveat

The answer’s phrase “entire set of ASCII characters” is too broad if read literally. XML 1.0 excludes certain control characters, such as NUL, even as numeric character references. The intended claim is about **all printable ASCII characters (U+0020–U+007E)**, as stated in the question.

### Memory aid

**JSON: backslash. XML: ampersand + name + semicolon. CSV: quote the field, double the quotes.**

</details>

Related: [Storing PNG images with text formats](016-persistent-image-data.md).
