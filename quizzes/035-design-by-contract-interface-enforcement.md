# Quiz 035: Design by Contract and interface enforcement

**Original label:** Week 08 Design by Contract lecture exercise, PDF p. 43.  
**Reviewed:** 11 November 2026.  
**Topic:** Syntactic versus behavioural contracts, interface checks and caller obligations.  
[Back to quiz index](README.md)

## Question

Which alternative is **INCORRECT** about design by contract?

### Options

a) Interfaces form a contract between the class and the outside world, and this contract is enforced at runtime.

b) Design by contract can be an effective way for debugging, testing and, more generally, quality assurance. Additionally, it is a method for documenting software components.

c) The basic premise of design by contract is to associate a specification with every software element. These specifications, also known as contracts, govern the interaction of the element with the rest of the world.

d) Preconditions specify the conditions that must be true before a method or function is called. They are obligations for the client (caller) to fulfill, ensuring that the method can safely and correctly execute its logic.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choice: a — it is the incorrect statement.**

A Java interface establishes structural requirements, such as compatible method declarations, that are primarily checked at compile time. It does not automatically check at runtime whether a method fulfils its intended behavioural contract.

### Why each option is correct or incorrect

| Option | Evaluation |
| --- | --- |
| a | Incorrect: an interface does not automatically enforce its intended behaviour at runtime. |
| b | Correct: explicit contracts support debugging, testing, quality assurance and documentation. |
| c | Correct: contracts describe the obligations and guarantees governing interactions between software elements. |
| d | Correct: meeting a method's preconditions is the caller's responsibility; the implementation must then fulfil its promised behaviour. |

### Additional example: valid implementation structure, incorrect behaviour

This example is separate from the supplied question.

```java
interface Calculator {
    int add(int a, int b);
}

class BadCalculator implements Calculator {
    @Override
    public int add(int a, int b) {
        return 0;
    }
}
```

The concrete class provides a public method with the required name, parameter types and return type. These declarations satisfy the interface's structural requirements, so ordinary Java compilation accepts the implementation.

However, calling `new BadCalculator().add(2, 3)` returns `0`, not `5`. The compiler does not infer the mathematical meaning of addition from the name `add`.

A JML postcondition can express an expected result explicitly, for example:

```java
//@ ensures \result == a + b;
int add(int a, int b);
```

This is an illustrative contract fragment; a complete specification should also state the intended arithmetic/overflow policy. The call with 2 and 3 has no overflow issue.

JML verification tools can check behavioural requirements through static verification or runtime assertion checking. Ordinary Java compilation ignores the JML comment. Contracts are therefore not inherently limited to runtime enforcement.

</details>

## Common mistakes

- Selecting a true statement when the question asks for the incorrect one.
- Assuming that compiling successfully proves that a method computes the intended result.
- Confusing runtime method dispatch with runtime checking of behavioural contracts.
- Assuming that JML comments are automatically enforced by ordinary Java execution.

**Memory aid:** Interfaces check compatible structure; behavioural contracts describe correct behaviour.

**Summary:** Java interfaces enforce structural requirements at compile time but do not automatically enforce behavioural contracts at runtime.

## Sources and related notes

- User-supplied lecture exercise screenshot, reviewed 11 November 2026.
- Course source C: *Week 08 - Design by Contract - COMP2100_6442 - Semester 2 2026-1.pdf*, PDF p. 43; background on interfaces pp. 7–8, contracts pp. 13–18, and JML checking pp. 26 and 40.
- [Design by Contract notes](../notes/week-08-design-by-contract.md)
- [Lecture source index](../notes/lecture-source-index.md)
- [Related quiz: JML pure methods in preconditions](036-jml-pure-methods-in-preconditions.md)
