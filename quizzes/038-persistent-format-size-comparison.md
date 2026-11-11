# Quiz 038: Comparing Serialized, CSV, JSON and XML file sizes

**Original label:** Question 2; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** Persistent data, text versus binary representation, structural overhead and primitive types.  
**Review status:** Understood after examples; revisit the difference between numeric values and digit characters.  
[Back to quiz index](README.md)

## Question

A class `LogEntry` contains some `String` fields but mostly numerical primitives such as `int` and `double`. Its instances typically require about 10 kilobytes of RAM.

Which ordering correctly depicts how we would expect the file sizes of a LogEntry stored in JSON, CSV, XML and Serialized formats to compare?

### Options

a) Serialized < CSV < JSON < XML.

b) Serialized < CSV < XML < JSON.

c) CSV < Serialized < XML < JSON.

d) No answer text provided.

e) CSV < Serialized < JSON < XML.

Option d is recorded as displayed in the screenshot; no missing answer has been inferred.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Quiz answer: a — Serialized < CSV < JSON < XML.**

This is the quiz's expected ordering for a numeric-heavy record, **not a universally guaranteed ordering**. The stated RAM size and field types alone do not determine the actual file sizes.

Here, “Serialized” refers to Java binary object serialization. More generally, JSON, CSV and XML are also ways to serialize data.

### Why the quiz expects this order

| Format | Intended explanation |
| --- | --- |
| Serialized | Numeric values can use compact binary representations. |
| CSV | Mainly values and delimiters, with an optional header. |
| JSON | Object records include property names, quotes, colons and braces. |
| XML | Element-based records include opening and closing tags. |

CSV normally avoids repeating field names on every record; JSON objects generally repeat their property names. XML using an element for each field adds paired tags. These are typical layouts, not mathematical size guarantees.

### Same data in three text formats

These are supplemental examples, not measurements of the original 10 KB object.

**CSV**

```csv
id,name
1,Ada
```

The header is optional. Fields are separated by commas and records by line breaks. Fields containing commas, quotes or line breaks are quoted; quotes within a quoted field are doubled.

**JSON**

```json
{"id":1,"name":"Ada"}
```

Objects use braces; arrays use square brackets. Property names and string values use double quotes. Numbers, booleans and null are not quoted when represented as those types.

**XML**

```xml
<entry>
    <id>1</id>
    <name>Ada</name>
</entry>
```

A document has one root element. Tags must match and be properly nested; names are case-sensitive. XML can also represent values using attributes.

### Binary value versus decimal digit characters

Consider:

```java
int count = 123456789;
```

In UTF-8 text, `123456789` is nine digit characters, each occupying one byte: **9 bytes for the value alone**.

Its 32-bit binary integer representation is:

```text
00000111 01011011 11001101 00010101
```

Those are four groups of eight bits: **4 bytes**. The displayed zeros and ones illustrate the actual bits; binary output does not write 32 text characters.

| Value | UTF-8 decimal text, excluding punctuation | Binary int value |
| --- | ---: | ---: |
| 123456789 | 9 bytes | 4 bytes |
| 1 | 1 byte | 4 bytes |

Therefore, binary integers can save space for long decimal numbers, but they are not always smaller than short textual numbers.

### Why the ordering is not guaranteed

Java object serialization also writes metadata such as class descriptors and field information. The four bytes above describe the integer value, not the complete serialized object.

Actual size depends on values, field names, metadata, the number of records, formatting, encoding and compression. A CSV file can be smaller than a Java-serialized file, especially with small numbers or significant serialization metadata.

The stated **10 KB RAM footprint is not a serialized file-size measurement**. Memory layout can include object headers, references and alignment that do not map directly to the file representation.

### Why the other options are not the marked quiz answer

| Option | Explanation |
| --- | --- |
| a | Matches the quiz's intended binary / delimiters / keys / paired-tags progression. |
| b | Places XML before JSON, contrary to the quiz's expected typical overhead. |
| c | Places CSV before Serialized and XML before JSON. |
| d | No meaningful alternative was supplied. |
| e | Places CSV before Serialized, contrary to the expected answer; this can nevertheless occur in real files. |

</details>

## Follow-up: Java primitive type widths

The widths below describe primitive values, not the complete heap footprint of objects or wrapper classes.

| Type | Value width | Bytes for the fixed-width numeric/char representation | Example |
| --- | ---: | ---: | --- |
| byte | 8 bits | 1 | -128 to 127 |
| short | 16 bits | 2 | -32768 to 32767 |
| int | 32 bits | 4 | 123456789 |
| long | 64 bits | 8 | 3000000000L |
| float | 32 bits | 4 | 3.14f |
| double | 64 bits | 8 | 3.14 |
| char | 16 bits | 2 | 'A'; one UTF-16 code unit |
| boolean | No universally specified memory-storage width | Depends on representation | true or false |

Java binary output via `ObjectOutputStream` writes a boolean value as **one byte**. This does not imply that every in-memory boolean has a universally fixed one-byte footprint.

A `char` is one UTF-16 code unit; some Unicode characters require two code units. Its fixed width does not mean the character always occupies two bytes when encoded in a text file.

`String` is not a primitive type. `Integer` and `Double` are wrapper objects, whose total memory footprint is not simply the corresponding primitive width.

## Common mistakes

- Assuming a binary file stores zeros and ones as visible text characters.
- Confusing the bytes needed for a value with the size of the complete serialized object.
- Assuming “binary” always means “smaller”.
- Inferring disk size directly from the RAM footprint.
- Treating the quiz's expected ordering as a universal rule.

**Memory aid:** Binary values; CSV delimiters; JSON keys; XML paired tags.

**Summary:** The quiz expects Serialized < CSV < JSON < XML, but actual sizes depend on numeric values, encoding and structural metadata.

## Sources and related notes

- User-supplied Question 2 screenshot and marked answer, reviewed 11 November 2026.
- [Week 4 Persistent Data notes](../notes/week-04-persistent-data.md)
- Lecture background: PD, PDF pp. 16–19 (Java serialization), 21–27 (XML), 28–30 (JSON); references count the cover as page 1. The exact ordering above comes from the supplied quiz feedback.
- [Java Object Serialization Stream Protocol](https://docs.oracle.com/en/java/javase/17/docs/specs/serialization/protocol.html)
- [Java DataOutput API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataOutput.html)
- [Related quiz: JSON, XML and CSV escaping](020-json-xml-csv-escaping.md)
- [Lecture source index](../notes/lecture-source-index.md)
