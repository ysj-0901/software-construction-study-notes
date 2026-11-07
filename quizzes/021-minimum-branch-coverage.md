# Quiz 021: Minimum tests for complete branch coverage

**Original label:** Question 5; quiz week not supplied.  
**Topic:** Branch coverage in two sequential if/else-if chains.  
[Back to quiz index](README.md)

## Question

Consider a program with the following control flow, where each labelled statement below corresponds to a distinct oval in the supplied graph.

Equivalent control-flow pseudocode (statement names added for clarity):

```text
if x == 1:
    statement X1
else if x == 2:
    statement X2
else if x == 3:
    statement X3

if y == 1:
    statement Y1
else if y == 2:
    statement Y2
else if y == 3:
    statement Y3
else if y == 4:
    statement Y4
```

After the x chain, execution always continues to the y chain. Each chain also has an all-false route that skips its statements.

What is the **minimum number of test cases** needed to achieve **branch complete coverage** for the shown control flow graph? Enter your answer as a numeral.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: 5.**

Branch coverage requires taking both the **True and False outgoing edges of every decision** at least once across the test suite.

### Shujie's test cases

The proposed inputs work:

**(x, y) = (1, 1), (2, 2), (3, 3), (4, 4), (5, 5).**

| Input | x == 1 | x == 2 | x == 3 | y == 1 | y == 2 | y == 3 | y == 4 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| (1, 1) | T | — | — | T | — | — | — |
| (2, 2) | F | T | — | F | T | — | — |
| (3, 3) | F | F | T | F | F | T | — |
| (4, 4) | F | F | F | F | F | F | T |
| (5, 5) | F | F | F | F | F | F | F |

**— means not evaluated**, because an earlier condition in that chain was true. It does not count as False.

Every decision column contains both T and F, so these five tests achieve complete branch coverage. The last two tests follow the same x route but different y routes.

### Why five is the minimum

The y chain has five mutually exclusive routes:

1. y = 1
2. y = 2
3. y = 3
4. y = 4
5. y outside {1, 2, 3, 4}

Each of the first four routes is needed to cover a different True edge. The fifth is needed to cover the False edge of the final decision, y == 4.

One execution can take only one of these routes, so **at least five tests are necessary**. The table demonstrates that five are sufficient for both chains. Therefore, the minimum is exactly five.

### Why not add or multiply?

- **Not 4 + 5 = 9:** a single test exercises both chains, so their coverage can be combined.
- **Not 4 × 5 = 20:** that counts all combinations of x routes and y routes, relevant to complete path coverage if all combinations are feasible. Branch coverage does not require every combination.
- The shortcut of combining independent sequential chains does **not** automatically apply to nested or dependent conditions.

### Statement coverage is different

For the seven oval statements in this graph, the first four tests already execute every statement. The fifth test adds the missing False branch of y == 4, even though it executes no new oval statement.

### Exam method

**Construct examples → mark T/F/skip → check every decision → prove fewer tests cannot work.**

Examples show that a number of tests is sufficient. Counting mutually exclusive required routes establishes the lower bound.

</details>
