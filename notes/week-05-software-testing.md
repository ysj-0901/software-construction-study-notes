# Week 5 — Software Testing

[Lecture source index and PDF-page navigation](lecture-source-index.md) · **Source key T:** `week05-testing(2).pdf`. References use **1-based PDF viewer pages, including the cover**, not printed slide numbers. Worked examples identified as supplemental are original study aids, not claims about examination coverage.

### 1. Overview

**Lecture reference:** T pp. 6–11.

#### What is Software Testing?
- Software testing checks whether software **behaves as expected**.
- It can range from compiling code to testing a complete system under realistic conditions.
- Testing can **find bugs**, but it cannot prove that software contains no bugs.
- Testing increases confidence in the correctness of the implementation.

#### Benefits of Testing
- Finds and isolates bugs early.
- Checks whether software meets requirements.
- Increases developer and customer confidence.
- Helps evaluate the correctness of software design and implementation.

#### Validation vs Verification

##### Validation
> **Building the right thing**

- Checks whether the software satisfies the **customer's needs and requirements**.
- Focuses on whether the correct product is being developed.
- Examples include:
  - Functional testing
  - Usability testing
  - Performance testing

##### Verification
> **Building the thing right**

- Checks whether the implementation follows the **specification and design**.
- Checks whether individual functions behave correctly.
- Can involve:
  - Testing
  - Reviews
  - Walkthroughs
  - Code inspections
- COMP6442 mainly focuses on **automated verification**.

#### Easy way to remember

```text
Validation   → Are we building the RIGHT THING?
Verification → Are we building the THING RIGHT?
```
#### Key Idea

```
Testing ≠ proof of correctness

Testing → increases confidence
        → detects defects
        → checks expected behaviour
```

> Testing can demonstrate the presence of bugs, but not guarantee their absence.

### 2. Black-Box vs White-Box Testing

**Lecture reference:** T pp. 13–16.

#### Black-Box Testing

Black-box testing creates test cases based on the **functional requirements** of the software without looking at the internal implementation.

Typical cases include:
- Normal cases
- Boundary cases
- Inputs outside the requirements
- Robustness/error cases

Example:

Suppose free delivery applies when:

```java
totalOrder >= 40 && validUser == true
```

Useful black-box tests include:

```
39.99, valid user
40.00, valid user
40.01, valid user
40.00, invalid user
```

The value `40.00` is especially important because it is a **boundary value**.
#### White-Box Testing

White-box testing creates tests based on the **internal code structure**.

The tester knows:

- Conditions
- Branches
- Loops
- Statements
- Execution paths

The goal is often to achieve good **code coverage**.

```
Black box → requirements tell us what to test
White box → code structure tells us what to test
```
### 3. Unit Testing and JUnit 4

**Lecture reference:** T pp. 17–40.

#### Unit Testing

A **unit** is the smallest independently testable part of software.

In object-oriented Java, this is often a:

```
method
```

Unit testing checks one unit in **isolation**.

Example:

```
public int add(int a, int b) {
    return a + b;
}
```

JUnit test:

```
@Test
public void testAdd() {
    MyMath math = new MyMath();
    assertEquals(4, math.add(2, 2));
}
```


---

#### `@Test`

A method annotated with:

```
@Test
```

is treated as a JUnit test case.

Example:

```
@Test
public void testSomething() {
    ...
}
```

All `@Test` methods in the test class are run by JUnit.

---

#### Common Assertions

**Lecture reference:** T pp. 21–23.

```
assertTrue(condition);
assertFalse(condition);

assertEquals(expected, actual);

assertSame(expected, actual);
assertNotSame(expected, actual);

assertNull(value);
assertNotNull(value);

fail();
```

Meaning:

```
assertTrue(x)
→ passes if x == true

assertFalse(x)
→ passes if x == false

assertEquals(a, b)
→ checks equality

assertSame(a, b)
→ checks whether they are the same object/reference
```

---

#### `assertEquals()` vs `assertSame()`

For object arguments, `assertEquals()` compares equality using:

```
equals()
```

If a class does not override `Object.equals()`, its default equality is reference identity. Primitive `assertEquals` overloads compare primitive values.

`assertSame()` compares references using:

```
==
```

Example:

```
String s1 = "ANU";
String s2 = "ANU";
String s3 = new String("ANU");
```

Then:

```
assertEquals(s1, s3); // PASS
assertSame(s1, s3);   // FAIL
```

because:

```
s1 and s3
→ same content
→ different objects
```

---

#### Floating-Point Assertions

**Lecture reference:** T p. 24.

Floating-point numbers may contain tiny representation errors.

Therefore use:

```
assertEquals(expected, actual, delta);
```

Example:

```
assertEquals(
    0.3333333,
    1.0 / 3.0,
    0.0000001
);
```

`delta` specifies the acceptable error.

---

#### `@Before` and `@After`

**Lecture reference:** T pp. 27–28.

```
@Before
```

runs **before each test method**.

```
@After
```

runs **after each test method**.

Example:

```
@Before
public void setUp() {
    math = new MyMath();
}

@After
public void tearDown() {
    math = null;
}
```

Execution:

```
@Before
@Test 1
@After

@Before
@Test 2
@After
```

Useful for:

- Object initialisation
- Preparing arrays/data structures
- Cleaning up resources

Setting a field to `null` only removes that reference; it does not immediately free memory or close a file/database connection. Explicitly close external resources when appropriate (implementation clarification of T p. 27).

---

#### `@BeforeClass` and `@AfterClass`

**Lecture reference:** T pp. 29–30.

These run once for the **entire test class**.

```
@BeforeClass
public static void beforeAll() {
    ...
}

@AfterClass
public static void afterAll() {
    ...
}
```

They must be:

```
static
```

Execution:

```
@BeforeClass

@Before
@Test 1
@After

@Before
@Test 2
@After

@AfterClass
```

Useful for expensive shared resources such as database connections.

---

#### Testing Exceptions

**Lecture reference:** T p. 31.

JUnit can check whether an exception is expected.

```
@Test(expected = ArithmeticException.class)
public void testDivisionByZero() {
    math.divide(1, 0);
}
```

The test:

```
throws ArithmeticException → PASS
does not throw exception    → FAIL
```

---

#### Timeouts

**Lecture reference:** T p. 32.

A test can fail if execution takes too long.

```
@Test(timeout = 1000)
public void testSomething() {
    ...
}
```

Here:

```
1000 ms = 1 second
```

Useful for:

- Detecting infinite loops
- Checking performance

---

#### Parameterized Tests

**Lecture reference:** T pp. 33–34.

Parameterized testing runs the **same test logic with multiple inputs**.

Example input sets:

```
0.7, 0.3, expected 1
0.4, 0.2, expected 0
```

Instead of writing many almost identical test methods, one test is repeatedly executed with different parameters.

---

#### Test Suite

**Lecture reference:** T p. 35.

A test suite combines multiple test classes.

Example:

```
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestClass1.class,
    TestClass2.class
})
public class FeatureTestSuite {
}
```

---

#### Unit Testing Guidelines

**Lecture reference:** T pp. 36–37, 40.

Good unit tests should:

- Test one thing at a time.
- Prefer many small tests over one huge test.
- Use only a few assertions per test.
- Avoid complicated test logic.
- Avoid unnecessary loops, `if/else`, and `switch`.
- Test normal cases and boundary cases.
- Test expected errors and exceptions.
- Use clear test names.
- Use clear assertion messages.
- Consider adding timeouts.

Example:

```
10 small tests
    >
1 enormous test
```

---

#### Test-Driven Development — TDD

**Lecture reference:** T pp. 38–39.

TDD reverses the usual order.

Instead of:

```
write code → write tests
```

TDD uses:

```
write test
    ↓
write code
    ↓
make test pass
```

The test is written before the feature is implemented.

---

### 4. Integration and System Testing

**Lecture reference:** T pp. 42–48.

#### Integration Testing

Integration testing combines units and checks their interactions; the lecture presents it after unit testing.

It checks whether multiple units work correctly **together**.

```
Unit A ─┐
        ├→ Integration Test
Unit B ─┘
```

Integration testing focuses on:

```
interaction between components
```

Both black-box and white-box approaches can be used.

---

#### Why Separate Unit and Integration Testing?

If a large integration test fails, it may be difficult to determine:

```
Which component caused the failure?
```

Unit testing first helps isolate bugs.

```
Unit tests
    ↓
verify individual components
    ↓
Integration tests
    ↓
verify interactions
```

---

#### Top-Down Integration

Start with higher-level modules and gradually integrate lower-level components.

```
Top module
    ↓
middle modules
    ↓
low-level modules
```

---

#### Bottom-Up Integration

Start with the lowest-level components.

```
low-level modules
    ↓
middle modules
    ↓
top module
```

---

#### System Testing

System testing checks the **entire system** in the environment where it will operate.

It checks:

- Software components
- Hardware components
- External systems
- Normal operational loads

```
Unit testing
    ↓
individual units

Integration testing
    ↓
interaction between units

System testing
    ↓
whole system
```

### 5. Statement Coverage

**Lecture reference:** T pp. 50–57.

Code coverage measures how much code is exercised by a test suite.

Statement coverage asks:

> Have all statements been executed at least once?

Formula:

```
Statement Coverage
=
executed statements
------------------- × 100
 total statements
```

Example:

```
if (a) {
    statementX;
} else {
    statementY;
    if (b)
        statementZ;
}

if (c)
    statementW;
```

To achieve 100% statement coverage, tests must execute:

```
statementX
statementY
statementZ
statementW
```

In the lecture example, at least **2 test cases** are needed.

Important:

```
100% statement coverage
≠
bug-free program
```

Statement coverage only tells us whether statements were executed.

Also:

```
Line coverage ≠ Statement coverage
```

A single line may contain several statements.

### 6. Branch Coverage

**Lecture reference:** T pp. 58–61.

Branch coverage checks whether every possible branch of each decision has been executed.

For:

```
if (a) {
    ...
} else {
    ...
}
```

there are two branches:

```
a = true
a = false
```

Formula:

```
Branch Coverage
=
executed branches
----------------- × 100
 total branches
```

Example:

```
if (a) {
    X;
} else {
    Y;

    if (b)
        Z;
}

if (c)
    W;
```

The decisions are:

```
a
b
c
```

Each has:

```
true branch
false branch
```

Therefore:

```
6 branches total
```

The lecture example requires at least **3 test cases** for 100% branch coverage.

Important relationship:

```
100% Branch Coverage
        ↓
100% Statement Coverage
```

Therefore:

```
Branch Coverage subsumes Statement Coverage
```

But:

```
100% Branch Coverage
≠
bug-free program
```

### 7. Condition and Multiple-Condition Coverage

**Lecture reference:** T pp. 62–67.

#### Condition Coverage

A decision may contain several **atomic conditions**, such as `A` and `B` in `if (A || B)`. Condition completeness asks whether each condition has actually been **evaluated to both true and false**. An input value assigned to an operand does not count as an evaluation if Java skips that operand.

T p. 62 calculates its percentage as:

```text
100 × number of clauses evaluated to both true and false
    / total number of clauses
```

Use the stated criterion and denominator when calculating a percentage; do not confuse clauses with complete decisions.

#### Short-Circuit Evaluation: Trace What Actually Executes

For Java `A || B`, `B` is skipped when `A` is true. For `A && B`, `B` is skipped when `A` is false.

**Supplemental worked example** applying T pp. 62–64 to Java `if (A || B)`:

| Test | A evaluated | B evaluated | Decision outcome |
| --- | --- | --- | --- |
| `A=true, B=false` | true | **Skipped** | true |
| `A=false, B=true` | false | true | true |
| `A=false, B=false` | false | false | false |

All three tests cover both outcomes of both evaluated conditions and both decision outcomes. The first two alone do **not** evaluate `B` to false.

> **Slide clarification (T p. 63):** the slide assigns `(true,false)` and `(false,true)` to `A,B` and calls this condition-complete for `A || B`. With Java short-circuit evaluation, `B=false` in the first test is skipped. Treat the slide's example as an input truth-value illustration; use actual evaluation when tracing Java code.

Condition coverage and decision coverage are distinct criteria and do not imply one another in general:

- **Decision without condition coverage:** for `A || B`, tests `(true,false)` and `(false,false)` cover true and false decisions, but never evaluate `B` to true.
- **Condition without decision coverage:** in the supplemental Java example `if (A | B)`, Boolean `|` evaluates both operands. Tests `(true,false)` and `(false,true)` evaluate each condition both ways, but the decision is always true. This example deliberately uses `|`, **not** `||`.

#### The Lecture's “Multiple Condition” Convention

T pp. 65–67 use the label **multiple condition coverage** for coverage of individual condition outcomes and decision outcomes, illustrated with an expanded short-circuit control-flow graph. Follow the illustrated criterion when answering a question referring to these slides; do not silently substitute a requirement to enumerate every Boolean input combination.

The lecture example is:

```java
if (a > 0 && c == 1) {
    X;
}
if (b == 3 || d < 0) {
    Y;
}
```

Write tests that reach each operand and exercise its true and false outcomes. Inputs for skipped operands do not add coverage. T p. 66 identifies three tests as sufficient for its illustrated criterion:

| Test | `a > 0` | `c == 1` | `b == 3` | `d < 0` |
| --- | --- | --- | --- | --- |
| 1 | true | true | true | skipped |
| 2 | true | false | false | true |
| 3 | false | skipped | false | false |

Here both decisions become true and false, and every atomic condition is evaluated to both values somewhere in the suite. This does not cover every complete path through the two decisions.

**Relationship in these slides:** course “multiple condition” completeness guarantees **both** condition completeness and decision completeness. It does **not** mean that condition completeness alone guarantees decision completeness.

### 8. Path Coverage

**Lecture reference:** T pp. 68–71.

Path coverage asks whether **every feasible complete execution path** through the program has been tested. Paths that cannot occur for any valid input should not be confused with feasible paths.

A path is a complete route through the control-flow graph.

Example:

```
start
 ↓
decision A
 ↙      ↘
...
 ↓
decision C
 ↙      ↘
return
```

Different combinations of branches create different paths.

Formula:

```
Path Coverage
=
executed paths
-------------- × 100
 total paths
```

In the lecture example, there are **6 possible paths**, so 6 suitable test cases are needed for complete path coverage.

#### Worked Test Suite for the Lecture's Nested Decisions

**Lecture reference:** T pp. 51–60, 68–70. The table consolidates the lecture's examples; `*` means the value is irrelevant because `b` is not evaluated when `a` is true.

```java
if (a) X;
else {
    Y;
    if (b) Z;
}
if (c) W;
```

| Criterion | A sufficient suite `(a,b,c)` | Why |
| --- | --- | --- |
| Statement | `(true,true,true)`, `(false,true,true)` | Executes X, Y, Z and W; two tests are necessary to enter both sides of `a`. |
| Branch | Add `(false,false,false)` to the statement suite | Also takes the false outcomes of `b` and `c`; six branches in total. |
| Path | `(true,*,true)`, `(true,*,false)`, `(false,true,true)`, `(false,true,false)`, `(false,false,true)`, `(false,false,false)` | Three routes through `a/b`, each combined with two outcomes of `c`: 3 × 2 = 6. |

Do not count all eight Boolean input triples as distinct paths: changing `b` when `a=true` does not change execution.


Important relationship:

```
100% Path Coverage
        ↓
100% Branch Coverage
        ↓
100% Statement Coverage
```

Therefore:

```
Path Coverage subsumes Branch Coverage
Branch Coverage subsumes Statement Coverage
```

However, path coverage can become impractical because loops can generate extremely many or even unlimited possible paths.

```
while (...) {
    ...
}
```

can execute:

```
0 times
1 time
2 times
3 times
...
```

Therefore complete path coverage is often too expensive or impossible.

---

### Coverage Relationships

Useful summary:

```
Path Coverage
      ↓
Branch Coverage
      ↓
Statement Coverage
```

So:

```
100% Path
→ 100% Branch
→ 100% Statement
```

But the reverse is not necessarily true.

Condition coverage is different:

```
100% Condition Coverage
        ✗
does not guarantee
100% Branch Coverage
```

Multiple-condition coverage gives:

```
100% course Multiple Condition → 100% Condition
100% course Multiple Condition → 100% Decision/Branch
```

but still does not guarantee path coverage.

---
### 9. Fault Injection, Instrumentation, and Loop Testing

**Lecture reference:** T pp. 72–76.

#### Fault Injection

Some code only executes under unusual situations:

- Low memory
- Full disk
- Lost network connection
- Corrupted data
- Hardware failures

These situations may be difficult to reproduce naturally.

Fault injection deliberately introduces faults during execution or compilation.

Examples include:

```
Mutation
Code insertion
Memory corruption
Network faults
```

Example mutation:

```
i = i + 1;
```

may be changed to:

```
i = i - 1;
```

to examine whether tests can detect the fault.

---

#### Instrumentation

Instrumentation adds extra code to monitor program execution.

It can record:

- Function calls
- Returned values
- Variable changes
- Memory allocation
- Which conditions or branches were executed

The collected information can then be used to calculate test coverage.

```
Original program
      +
monitoring code
      ↓
instrumented program
      ↓
execution information
      ↓
coverage measurement
```

---

#### Testing Loops

For loops such as:

```
while (x < y) {
    ...
}
```

or:

```
for (...) {
    ...
}
```

important tests include:

```
0 iterations
1 iteration
n iterations (small typical value)
maximum number of iterations
```

Optional boundary cases:

```
maximum - 1
maximum + 1
```

The goal is to test the loop around important iteration boundaries.

---

### Final Week 5 Testing Structure

```
Software Testing
│
├── Testing Fundamentals
│   ├── Validation
│   └── Verification
│
├── Testing Approaches
│   ├── Black Box
│   └── White Box
│
├── Testing Levels
│   ├── Unit
│   ├── Integration
│   └── System
│
├── JUnit 4
│   ├── @Test
│   ├── Assertions
│   ├── @Before / @After
│   ├── @BeforeClass / @AfterClass
│   ├── Exceptions
│   ├── Timeout
│   ├── Parameterized Tests
│   └── Test Suites
│
└── Code Coverage
    ├── Statement
    ├── Branch
    ├── Condition
    ├── Multiple Condition
    └── Path
```

