# Quiz 031: Path coverage versus branch coverage percentages

**Original label:** Question 1; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** Coverage subsumption versus partial coverage percentages.  
[Back to quiz index](README.md)

## Question

Through instrumentation, a test suite MyTests has been found to achieve 70% path coverage. What is known about the **branch coverage** of MyTests?

### Options

1. MyTests may have less than 70% branch coverage.
2. MyTests has at least 70% branch coverage.
3. MyTests has 100% branch coverage.
4. MyTests has exactly 70% branch coverage.
5. Nothing: path coverage doesn't say anything about branch coverage.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 1, “MyTests may have less than 70% branch coverage.”** The supplied screenshot confirms full credit.

### Complete coverage implies complete coverage

Under the same control-flow scope and feasibility assumptions:

**100% path coverage implies 100% branch coverage.**

Executing every complete path traverses every branch on those paths. This is the sense in which path coverage **subsumes** branch coverage.

However, this implication at 100% does **not** establish that branch coverage must be at least path coverage for every partial test suite.

### The percentages count different things

| Metric | Calculation |
| --- | --- |
| Path coverage | Distinct complete paths executed / total distinct complete paths |
| Branch coverage | Distinct decision outcomes executed / total decision outcomes |

Multiply each fraction by 100 to obtain its percentage. Repeating a path does not increase coverage.

Several paths may reuse the same branches. Covering many such paths can leave a substantial number of other branches untested.

### A counterexample: 70% paths, 56.25% branches

This is an **additional illustrative example**, not code supplied in the quiz. Count the true and false outcomes of each if as branches. All Boolean inputs are independent; the method has no loops, and every path is feasible.

```java
static int example(boolean modeA,
                   boolean a, boolean b, boolean c, boolean d,
                   boolean e, boolean f, boolean g) {
    int result = 0;
    if (modeA) {
        if (a) result++;
        if (b) result++;
        if (c) result++;
        if (d) result++;
    } else {
        if (e) {
            if (f) result++;
        } else {
            if (g) result++;
        }
    }
    return result;
}
```

**Count the paths:**

- With modeA true, four sequential decisions give 2 × 2 × 2 × 2 = **16 paths**.
- With modeA false, there are **4 paths**: e true with either outcome of f, or e false with either outcome of g. The unused Boolean does not create another path.
- Total: **20 complete paths**.

Now test only modeA true, using all combinations of (a, b, c, d) **except FFFF and TTTT**. Set e, f and g to any values; they are not evaluated in these tests.

This executes **14 of 20 paths = 70% path coverage**.

**Count the branches:**

| Decision group | Total outcomes | Covered outcomes |
| --- | ---: | ---: |
| modeA | 2 | 1: true only |
| a, b, c and d | 8 | 8: each is both true and false across the 14 tests |
| e, f and g | 6 | 0: the entire else region is untested |
| **Total** | **16** | **9** |

Therefore, **branch coverage = 9 / 16 = 56.25%**, which is less than 70%.

### Why the other options do not follow

| Option | Evaluation |
| --- | --- |
| 1: may be less than 70% | Correct. The counterexample establishes that this is possible. |
| 2: at least 70% | Not guaranteed; the counterexample has 56.25%. |
| 3: 100% | Possible for some suites, but not implied by 70% path coverage. |
| 4: exactly 70% | Not implied; the two metrics have different denominators. |
| 5: no relationship at all | Too strong. The full-coverage implication remains valid, even though the given percentage does not determine an exact branch percentage or guarantee 70%. |

“May have less” does not mean “must have less”.

</details>

## Common mistake

Applying the full-coverage implication directly to percentages:

**100% path implies 100% branch** does not imply **70% path guarantees at least 70% branch**.

**Memory aid:** Coverage subsumption concerns satisfying the full criterion; partial percentages count different sets of requirements.

## Sources and related notes

- User-supplied Question 1 screenshot and marked answer, reviewed 11 November 2026.
- [Software testing notes](../notes/week-05-software-testing.md): branch coverage in T, PDF pp. 58–61; path coverage in T, PDF pp. 68–71.
- [Minimum tests for complete branch coverage](021-minimum-branch-coverage.md)
- The counterexample was checked by enumerating all 256 Boolean input combinations, deduplicating execution paths, and counting distinct decision outcomes.
