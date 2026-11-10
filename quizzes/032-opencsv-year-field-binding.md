# Quiz 032: Bind a CSV year column to a Java field with OpenCSV

**Original label:** Question 2; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** OpenCSV annotations, zero-based positions and column-name mapping.  
[Back to quiz index](README.md)

## Question

Suppose a CSV document in use by a car sales platform has the following format:

```text
id-brand-model-year-price
1234-"Mercedes-Benz"-"C-Class C300"-2018-50000
5678-Ferrari-Superamerica-2006-420000
```

The screenshot's typographic quotation marks have been normalised to straight double quotes, and the wrapped first record is shown on one line.

A developer wants to serialise this data to instances of the class Car using OpenCSV. Which annotation should be given to the field `private int yearMade;`?

### Options

1. `@CsvBindByPosition(position = 3)`
2. `@CsvBindByName`
3. `@CsvMarker("year")`
4. No annotation should be given to this field.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 1, `@CsvBindByPosition(position = 3)`.** The supplied feedback identifies this answer and marks the selected bare `@CsvBindByName` as incorrect.

### Position 3 means the fourth column

OpenCSV's position-based annotation uses a zero-based column index.

| Column | id | brand | model | year | price |
| --- | --- | --- | --- | --- | --- |
| Position | 0 | 1 | 2 | **3** | 4 |

```java
@CsvBindByPosition(position = 3)
private int yearMade;
```

This maps the fourth parsed field, such as 2018 or 2006, to yearMade.

### Why the selected annotation does not work as written

```java
@CsvBindByName
private int yearMade;
```

Without an explicit column argument, name-based binding uses the Java field's name. It therefore expects a header named **yearMade**, while the file contains **year**.

Name-based binding is a valid alternative when the mapping is made explicit:

```java
@CsvBindByName(column = "year")
private int yearMade;
```

This corrected form is **not the option shown**. The two snippets are alternative mapping approaches.

### Compare all options

| Option | Evaluation |
| --- | --- |
| 1: ByPosition, position 3 | Correct: year is the fourth column. |
| 2: bare ByName | The default header name is yearMade, which does not match year. |
| 3: CsvMarker | Not the OpenCSV binding annotation for this mapping; the name-based form uses CsvBindByName with a column argument. |
| 4: no annotation | Does not express the required mapping in the annotation-based approach shown. A separately configured mapping strategy would be a different setup. |

### Keep parsing and field mapping separate

The annotation maps a parsed column to a field. The reader must also handle the file's separator, quotation marks and header appropriately.

Here the separator is a hyphen. With correct quote handling, the hyphens inside "Mercedes-Benz" and "C-Class C300" remain part of those fields; they do not create additional columns. A simple split on every hyphen would give the wrong positions.

</details>

## Common mistake

Seeing a header row and choosing bare CsvBindByName without comparing the header name with the Java field name.

**Memory aid:**

- **ByPosition:** which column number? Start at 0.
- **ByName:** which header name? Supply `column = "year"` when the field is named yearMade.

## Sources and related notes

- User-supplied Question 2 screenshot and quiz feedback, reviewed 11 November 2026.
- [OpenCSV: CsvBindByPosition](https://opencsv.sourceforge.net/apidocs/com/opencsv/bean/CsvBindByPosition.html)
- [OpenCSV: CsvBindByName](https://opencsv.sourceforge.net/apidocs/com/opencsv/bean/CsvBindByName.html)
- [Persistent data notes](../notes/week-04-persistent-data.md)
- [Escaping strings in JSON, XML and CSV](020-json-xml-csv-escaping.md)
