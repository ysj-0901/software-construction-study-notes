# Week 4 — Persistent Data

Source: **PD** in the [lecture source index](lecture-source-index.md). All references below use **1-based PDF viewer pages**, not the slide numbers printed on the slides.

## 1. What persistence means

**Persistent data survives after the program stops running.** It supports saving/restoring state, logging, reuse and long-term records. Working-memory data is temporary; files and databases can preserve it across restarts. Examples include JSON/XML files, snapshots, images, SQLite, MySQL and Cassandra. [PD pp. 5–6]

| Category | Examples from the lecture |
|---|---|
| Unstructured text | Raw text, word-processing formats |
| Structured text | CSV, TSV, DIMACS CNF, bespoke formats |
| Graphics | PNG, JPEG, BMP, FITS |
| Audio/video | MP3, WAV, MP4 |
| Archiving/compression | tar, gzip, rar |

The lecture groups archiving and compression together; an archive such as plain `.tar` need not itself compress its contents. [PD p. 7]

## 2. Choosing a format is a design decision

Start with the application's data and requirements, then consider storage, access speed, licensing and development effort. [PD p. 8]

| Criterion | Question to ask |
|---|---|
| Programming agility | How much code and development effort are needed? |
| Extensibility | Can fields or structure change without breaking readers? |
| Portability | Can other languages, applications and platforms read it? |
| Maintainability | Will the format remain understandable over the application's lifetime? |
| Robustness | Are structure, validation and interpretation clearly specified? |
| Size vs completeness | Is losing information acceptable? Financial records generally need lossless storage. |
| Internationalisation | Does the encoding support the intended users? UTF-8 supports far more characters than ASCII. |

A custom CSV layout may be quick to implement but difficult to extend. A poorly specified format can cause interoperability problems. XML and JSON still need agreed field meanings and validation; choosing a standard syntax alone does not ensure correct data. [PD pp. 9–12]

## 3. Formats at a glance

| Approach | Useful when | Main limitation |
|---|---|---|
| Bespoke text/CSV | Simple, predictable records; little implementation overhead | Escaping, validation and future changes must be designed |
| Java object serialisation | Saving/restoring Java object state | Java coupling, class-version compatibility and security |
| XML | Hierarchical data, attributes, interoperability and validation | Verbose; larger files; no explicit array/null type |
| JSON | Lightweight structured interchange | Fewer XML-style features; use a JSON library in Java |
| Relational database | Large structured datasets and relationships | Requires a database schema and access code |

Relational databases link separate tables using IDs. For example, `Person(id, name)` and `ContactPhones(personId, number)` support multiple numbers without repeating all person information. [PD pp. 13–14, 17–19, 27–30]

## 4. Java serialisation: the short implementation recipe

Serialisation converts object state into a byte representation; deserialisation reconstructs objects. The lecture uses `Serializable`, `ObjectOutputStream`, `ObjectInputStream` and `transient`. Collections such as `ArrayList` are serialisable, but the objects reached through their non-transient fields must also support serialisation. [PD pp. 16–19]

Remember **model → save → load → check**:

1. **Model:** `implements Serializable`; mark fields that should not be saved `transient`.
2. **Save:** file output stream → object output stream → `writeObject(object)`.
3. **Load:** file input stream → object input stream → `readObject()` → cast/check type.
4. **Check:** compare restored fields; close streams with try-with-resources.

`IOException` covers file/stream problems; `ClassNotFoundException` may occur while reading. A deserialised `transient` reference defaults to `null`; a transient primitive gets its default value. **Never use Java deserialisation on untrusted data.** [PD pp. 18–19]

### Original illustrative round trip

The following is a small original example applying the lecture's APIs, not code copied from a slide. `serialVersionUID`, temporary-file cleanup and executable checks are implementation additions. Save it as `SerialisationExample.java`.

```java
import java.io.*;
import java.nio.file.*;

public class SerialisationExample {
    static class Person implements Serializable {
        private static final long serialVersionUID = 1L;
        final String name;
        transient String sessionToken; // deliberately not persisted

        Person(String name, String sessionToken) {
            this.name = name;
            this.sessionToken = sessionToken;
        }
    }

    static void save(Path file, Person person) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                Files.newOutputStream(file))) {
            out.writeObject(person);
        }
    }

    static Person load(Path file)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                Files.newInputStream(file))) {
            return (Person) in.readObject(); // trusted file of known type
        }
    }

    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("person-", ".ser");
        try {
            save(file, new Person("Alice", "temporary-token"));
            Person restored = load(file);
            if (!"Alice".equals(restored.name)
                    || restored.sessionToken != null) {
                throw new AssertionError("Round trip failed");
            }
            System.out.println(restored.name + ": " + restored.sessionToken);
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
```

Output: `Alice: null`. Serialisation preserves ordinary shared references within a serialised object graph; it does not automatically preserve live operating-system resources such as an open file handle. Avoid treating the lecture's broad warning about references as a rule that every reference is lost.

**When time is short:** write the data model and the save/load stream skeleton first, then add fields, exception handling and checks. This is an implementation strategy, not a statement about exam marking.

## 5. XML: structure and parser choice

```xml
<people>
  <person id="1">
    <name>Alice &amp; Bob</name>
  </person>
</people>
```

`person` and `name` are elements; `id` is an attribute of `person`; `Alice & Bob` is text content. XML is **case-sensitive**: `<Root>` and `<root>` differ. Nest and close tags correctly. Escape literal `<` as `&lt;` and `&` as `&amp;`; `&gt;` represents `>`. [PD pp. 21–22, 24]

| DOM | SAX |
|---|---|
| Builds the whole document tree in memory | Processes a stream of parsing events |
| Convenient to navigate and modify the tree | Useful for large documents and sequential extraction |
| Memory grows with document size | Does not require retaining the whole document |

[PD p. 23]

### DOM implementation checklist

**Save:** `DocumentBuilderFactory` → `DocumentBuilder` → new `Document` → create/append elements and attributes → `Transformer` → file.

**Load:** factory → builder → parse `Document` → locate elements/attributes/text → reconstruct application objects. [PD p. 25]

### Original illustrative DOM round trip

This standalone example applies the lecture's DOM sequence. It parses only the temporary file it creates; it is not a hardened parser for untrusted XML. Save it as `XmlExample.java`.

```java
import java.nio.file.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class XmlExample {
    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("people-", ".xml");
        try {
            DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("people");
            doc.appendChild(root);

            Element person = doc.createElement("person");
            person.setAttribute("id", "1");
            Element name = doc.createElement("name");
            name.setTextContent("Alice & Bob");
            person.appendChild(name);
            root.appendChild(person);

            Transformer transformer =
                TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc),
                                  new StreamResult(file.toFile()));

            Document loaded = builder.parse(file.toFile());
            Element restored = (Element)
                loaded.getElementsByTagName("person").item(0);
            String restoredName = restored.getElementsByTagName("name")
                                          .item(0).getTextContent();
            if (!"1".equals(restored.getAttribute("id"))
                    || !"Alice & Bob".equals(restoredName)) {
                throw new AssertionError("XML round trip failed");
            }
            System.out.println(restoredName);
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
```

`setTextContent` plus the XML transformer handles escaping: store the actual text `Alice & Bob`, not the already escaped string `Alice &amp; Bob`.

## 6. JSON essentials

JSON uses objects with name/value pairs and arrays of values. Values can be **object, array, string, number, boolean or null**. Keys and strings use double quotes. [PD pp. 28–29]

```json
{"name":"Alice","age":20,"active":true,"phones":[],"address":null}
```

JSON is usually smaller and less verbose than equivalent XML, supports arrays and `null` directly, and distinguishes numbers, booleans and strings. XML offers elements plus attributes and richer XML-specific features. The lecture's Java workflow uses a library for JSON; XML APIs are included in the Java platform. [PD pp. 27–30]

For a JSON coding task: choose the required library → map object fields to keys → write the JSON → parse it → validate required keys/types → reconstruct the object. Exact method names depend on the chosen library.

## 7. Common mistakes and quick lookup

| Mistake | Correction | Source |
|---|---|---|
| Assuming persistence happens automatically | Explicitly save and restore application data | PD p. 6 |
| Choosing only by shortest code | Consider portability, maintenance, validation and data lifetime | PD pp. 8–12 |
| Treating Java serialisation as language-independent | XML/JSON/CSV are more natural cross-language choices | PD pp. 17, 32 |
| Serialising a live session token | Exclude unnecessary state with `transient` | PD p. 18 |
| Treating XML names as case-insensitive | `<root>` differs from `<ROOT>` | PD pp. 21, 31 |
| Hand-inserting raw `<` or `&` into XML text | Escape correctly or let XML APIs write text | PD p. 22 |
| Assuming DOM streams a huge file | DOM builds the whole tree; SAX streams events | PD p. 23 |
| Claiming JSON cannot represent arrays or null | Both are explicit JSON value types | PD pp. 29–31 |
