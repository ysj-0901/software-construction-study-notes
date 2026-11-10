# Quiz 034: Facade and Java package access modifiers

**Original label:** Question 4; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** Facade, public entry points and package-private implementation classes.  
[Back to quiz index](README.md)

## Question

Consider a usage of the Facade design pattern in an email application which uses SpamFilter as the Facade for the spam package. Assuming SpamFilter is also in the spam package, what access modifiers would you expect to be used on classes in spam?

### Options

1. SpamFilter would be public, and the other classes would have no access modifier.
2. SpamFilter would be public, and the other classes would be protected.
3. Every class in spam would have no access modifier.
4. Every class in spam would be public.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 1.** The supplied screenshot marks it correct.

The intended design exposes **SpamFilter as the public entry point** and keeps implementation classes inside the package.

### Minimal class declarations

Treat these as ordinary top-level classes. This is a declaration example, not a complete spam-filter implementation.

```java
// SpamFilter.java
package spam;

public class SpamFilter {
    // Public facade operations coordinate the internal helpers.
}

class KeywordChecker {
    // Package-private implementation class.
}

class SenderChecker {
    // Package-private implementation class.
}
```

| Class | Access modifier | Intended role |
| --- | --- | --- |
| SpamFilter | public | Entry point for clients outside spam |
| KeywordChecker | None: package-private | Internal helper available within spam |
| SenderChecker | None: package-private | Internal helper available within spam |

SpamFilter can use the helper classes because it belongs to the **same package**. Clients outside spam use the facade instead of directly depending on those helpers.

### Why the other options do not fit

| Option | Evaluation |
| --- | --- |
| 1: public facade; other classes package-private | Correct: provides an external entry point while keeping helper types internal. |
| 2: public facade; other classes protected | Invalid for top-level Java classes. Protected access is available for certain members, including member classes, but not top-level classes. |
| 3: every class package-private | Clients outside spam cannot access SpamFilter as the intended entry point. |
| 4: every class public | Exposes the helper types, allowing clients to depend directly on them. This misses the package-encapsulation design expected by the question. |

### A pattern is not an absolute visibility rule

Facade provides a simpler interface to a subsystem and reduces the client's need to coordinate its implementation classes.

A facade can still exist when some subsystem classes are public. Therefore, option 4 is not inherently invalid Java or proof that no Facade pattern exists. Option 1 is the expected choice for **this design**, which makes SpamFilter the public entry point and hides the other types.

</details>

## Common mistakes

- Treating no access modifier as public. For these top-level classes, it means **package-private**.
- Using protected or private on a top-level class.
- Making the facade package-private and unintentionally hiding the intended external entry point.
- Confusing the expected encapsulation choice with a universal requirement of every Facade implementation.

**Memory aid:** The facade is the public front desk; helper classes work inside the package.

## Sources and related notes

- User-supplied Question 4 screenshot and marked answer, reviewed 11 November 2026.
- [UML and design-pattern notes](../notes/week-02-uml-and-design-patterns.md): Facade in U, PDF pp. 105–109.
- [Java Language Specification: top-level class and interface declarations](https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.6)
- [Lecture source index](../notes/lecture-source-index.md)
