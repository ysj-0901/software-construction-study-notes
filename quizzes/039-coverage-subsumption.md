# Quiz 039: Coverage subsumption relationships

**Original label:** Question 1; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** Path, branch and condition coverage; the meaning of subsumption.  
[Back to quiz index](README.md)

## Question

Which statement is true about the relationship between different types of coverage?

a) Path coverage subsumes branch coverage.

b) Branch coverage subsumes condition coverage.

c) Branch coverage subsumes path coverage.

d) Condition coverage subsumes branch coverage.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: a — Path coverage subsumes branch coverage.**

A criterion A **subsumes** criterion B when every test suite satisfying A also satisfies B. Here, this concerns complete coverage, not equal percentages of partial coverage.

Under the course's usual assumptions about feasible paths and reachable branches:

**100% path coverage ⇒ 100% branch coverage ⇒ 100% statement coverage.**

A complete path is a route through the program. Exercising all complete paths also exercises every branch occurring on those paths.

### Why the alternatives are false

| Option | Evaluation |
| --- | --- |
| a | True: complete path coverage includes complete branch coverage. |
| b | False: covering both decision outcomes does not necessarily evaluate each atomic condition both ways. |
| c | False: covering branches separately need not cover every combination of branches along complete paths. |
| d | False: atomic conditions can each evaluate true and false while the overall decision always has the same outcome. |

### Branch coverage does not imply path coverage

Supplemental example:

```java
if (a) { first(); } else { second(); }
if (b) { third(); } else { fourth(); }
```

Assume a and b are independent inputs. Tests `(true,true)` and `(false,false)` cover both outcomes of each decision, giving complete branch coverage. They cover only two of the four complete paths: `(true,false)` and `(false,true)` remain untested.

### Branch coverage does not imply condition coverage

Supplemental Java example:

```java
if (a || b) {
    action();
}
```

| Inputs (a, b) | a evaluated | b evaluated | Decision |
| --- | --- | --- | --- |
| (true, false) | true | Skipped | true |
| (false, false) | false | false | false |

Both decision outcomes occur, but b is never evaluated to true. Short-circuited operands do not count as evaluated conditions.

### Condition coverage does not imply branch coverage

Supplemental Java example, deliberately using Boolean `|` rather than short-circuit `||`:

```java
if (a | b) {
    action();
}
```

Tests `(true,false)` and `(false,true)` evaluate both a and b to both values, so condition coverage is complete. However, the overall decision is always true, so branch coverage is incomplete.

### Complete versus partial coverage

Subsumption does **not** imply that 70% path coverage guarantees at least 70% branch coverage. Different criteria count different things with different denominators. See [Quiz 031](031-path-versus-branch-coverage-percentages.md) for a worked counterexample.

</details>

## Common mistakes

- Reversing the direction of “subsumes”.
- Treating branch outcomes as identical to the outcomes of individual conditions.
- Counting values of operands that Java skips.
- Applying the 100% subsumption rule directly to partial-coverage percentages.

**Memory aid:** All paths include all branches; all branches do not include all path combinations.

**Summary:** Path coverage subsumes branch coverage, but branch coverage and condition coverage do not generally subsume one another.

## Sources and related notes

- User-supplied Question 1 screenshot and marked answer, reviewed 11 November 2026.
- [Week 5 Software Testing notes](../notes/week-05-software-testing.md): T, PDF pp. 58–64 (branch and condition coverage), 68–71 (path coverage). Page numbers include the cover.
- [Lecture source index](../notes/lecture-source-index.md)
- [Quiz 031: Path versus branch coverage percentages](031-path-versus-branch-coverage-percentages.md)
- [Quiz 021: Minimum tests for complete branch coverage](021-minimum-branch-coverage.md)
