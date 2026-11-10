# Quiz 027: JUnit assertion for string reversal

**Original label:** Question 1; quiz week not supplied.  
**Reviewed:** 10 November 2026.  
**Topic:** String content equality versus object identity.  
[Back to quiz index](README.md)

## Question

An implementation of string reversal has a test case that begins with:

```java
String r = CustomReverse.reverse("hello");
```

Which JUnit assertion could then be used to check the output in reverse in this case?

1. `assertEquals("olleh", r);`
2. `assertSame("olleh", r);`
3. `assertApproximatelyEquals("olleh", r);`
4. `assertEquals("olleh", r, 1e-10);`

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 1, `assertEquals("olleh", r);`.** The supplied quiz screenshot confirms full credit.

```java
assertEquals(expected, actual);
```

The expected content is `"olleh"`; `r` is the actual result.

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | Correct | Compares string content. |
| 2 | Incorrect | Requires both references to identify the same object. Correct content does not require object identity. |
| 3 | Incorrect | Standard JUnit 4 has no assertion with this name. |
| 4 | Incorrect | A numeric delta is used for floating-point comparisons; there is no matching string/string/delta overload. |

### Equal content, different objects

This supplementary example deliberately creates a different String object:

```java
String actual = new String("olleh");
assertEquals("olleh", actual); // Passes: equal content.
assertSame("olleh", actual);   // Fails: different objects.
```

The second assertion illustrates the mistake; it is not a recommended test.

</details>

## Common mistake

Using `assertSame` because the two strings look the same. Reversal specifies the resulting text, not which String object must hold it.

**Memory aid:** `assertEquals` = equal content for strings; `assertSame` = identical object.

## Sources and related notes

- User-supplied Question 1 screenshot and marked answer, reviewed 10 November 2026.
- [JUnit 4.13.2 Assert API](https://junit.org/junit4/javadoc/4.13.2/org/junit/Assert.html)
- [Software testing notes](../notes/week-05-software-testing.md). The lecture source index places JUnit in T, PDF pp. 18–38.
- [JUnit factorial test review](022-junit-factorial-test-review.md)
