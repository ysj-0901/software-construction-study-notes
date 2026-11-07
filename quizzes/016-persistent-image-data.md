# Quiz 016: Persisting account data with a PNG profile picture

**Original label:** Question number not visible in the supplied screenshots; quiz week not supplied.  
**Topic:** Persistent data, image references and binary-to-text encoding.  
[Back to quiz index](README.md)

## Question

A client wishes to store user account data persistently. This data includes a profile picture, which is a 256 by 256 pixel PNG image, to be stored with each account. Which formats are suitable for this purpose?

1. Because java.awt.Image implements the Serializable interface, it can be represented in any format that supports serialization.
2. None of XML, JSON, or CSV natively support storing images. The data could be stored separately with its file path referenced inside XML, JSON, or CSV, or the image data could be manually encoded and decoded to a string-like representation.
3. Because the number of pixels in the image is fixed, the data is suitable for storing in a CSV array.
4. Because XML is based on HTML, XML is able to include images using standard HTML syntax.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 2.** The supplied screenshot marks it as correct.

### The key word: natively

A PNG file contains binary data. XML, JSON and CSV can store a reference to that file or a text representation of its bytes; they do not provide a built-in PNG-image field that accepts arbitrary raw PNG bytes directly.

For example, JSON has objects, arrays, strings, numbers, booleans and null, but no image value type ([JSON specification](https://www.rfc-editor.org/rfc/rfc8259#section-3)).

**“No native image support” does not mean “cannot be used for an account that has a picture”.**

### Method 1: save the image separately and store its path

Save the picture as `images/u123.png`. The account record could be:

```json
{
  "userId": "u123",
  "name": "Alice",
  "profilePicture": "images/u123.png"
}
```

The JSON stores the **location**, not the picture's bytes. Loading the account requires following that path to load the PNG. The picture file must be preserved along with the record.

The same reference can be stored in XML or CSV:

```xml
<account id="u123">
  <profilePicture>images/u123.png</profilePicture>
</account>
```

```csv
userId,name,profilePicture
u123,Alice,images/u123.png
```

### Method 2: encode the image bytes as text

**PNG bytes → Base64 text → store in a string/text field.**  
**Read the text → Base64-decode → recover the original PNG bytes.**

Illustrative JSON:

```json
{
  "userId": "u123",
  "profilePictureBase64": "iVBORw0KGgo..."
}
```

The value above is deliberately truncated and is not a complete image.

A short Java example, assuming `pngBytes` already contains the PNG file's bytes and `java.util.Base64` is imported:

```java
String encoded = Base64.getEncoder().encodeToString(pngBytes);
byte[] restored = Base64.getDecoder().decode(encoded);
```

Java provides [Base64 encoders and decoders](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Base64.html), so “manually encoded” means the application explicitly performs the conversion; it does not mean writing the encoding algorithm from scratch.

Encoding the complete PNG preserves its bytes. Base64 is a representation change, not image compression or encryption. The surrounding XML/JSON/CSV parser still sees text; the application must know how to interpret it.

### Why the other options are wrong

| Option | Problem |
| --- | --- |
| 1: Image implements Serializable | The premise is false: [java.awt.Image](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Image.html) does not implement Serializable. Also, Java object serialisation does not automatically make an object natively representable in every other data format. |
| 3: fixed pixels → CSV array | Fixed dimensions do not give CSV a native image type. CSV contains rows and fields. You could design a custom representation of pixels or encoded bytes, but conversion and reconstruction would still be required. |
| 4: XML uses HTML image syntax | XML is a restricted form of SGML, not a format that inherits HTML's image-element behaviour ([XML specification](https://www.w3.org/TR/xml/)). An XML element named img has no built-in image-rendering meaning. |

### Why 256 × 256 does not decide the answer

The dimensions describe **65,536 pixels**, not a fixed PNG byte length. Pixel data and a PNG file are different representations; PNG encoding also involves compression and other file information.

The dimensions do not remove the need to store either a **reference** or an **encoded representation** when using the text formats in the options.

### Memory aid

**Image in a text-based account record → store a path, or encode its bytes as text.**

**Path = where the image is. Base64 = the image bytes represented as text.**

</details>

Related: [Persistent-data notes](../notes/week-04-persistent-data.md).
