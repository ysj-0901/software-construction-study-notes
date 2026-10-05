# Week 5 — Software Testing

### 1. Overview

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

`assertEquals()` usually compares values using:

```
equals()
```

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

---

#### `@BeforeClass` and `@AfterClass`

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

Parameterized testing runs the **same test logic with multiple inputs**.

Example input sets:

```
0.7, 0.3, expected 1
0.4, 0.2, expected 0
```

Instead of writing many almost identical test methods, one test is repeatedly executed with different parameters.

---

#### Test Suite

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

#### Integration Testing

Integration testing happens after individual units have been tested.

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

#### Condition Coverage

Condition coverage becomes important when one decision contains multiple conditions.

Example:

```
if (A || B) {
    ...
}
```

There are two individual conditions:

```
A
B
```

Condition coverage asks whether each condition has been evaluated as:

```
true
and
false
```

So we want:

```
A → true and false
B → true and false
```

Important:

```
100% Condition Coverage
does NOT necessarily imply
100% Branch Coverage
```

For example, both `A` and `B` may individually become true and false while the complete expression:

```
A || B
```

never becomes false.

Then the `else` branch is never executed.

---

#### Short-Circuit Evaluation

Java may skip evaluating later conditions.

Example:

```
A || B
```

If:

```
A == true
```

then Java already knows:

```
A || B == true
```

so `B` may not be evaluated.

Similarly:

```
A && B
```

If:

```
A == false
```

then `B` may not be evaluated.

This matters when designing condition-coverage tests.

---

#### Multiple-Condition Coverage

Multiple-condition coverage considers how the compiler evaluates combinations of conditions and branches.

Example:

```
if (a > 0 && c == 1) {
    X;
}

if (b == 3 || d < 0) {
    Y;
}
```

Tests should exercise combinations that allow the different conditions and branches to be evaluated.

Important relationship:

```
100% Multiple-Condition Coverage
        ↓
100% Condition Coverage
        +
100% Decision/Branch Coverage
```

Multiple-condition coverage is therefore more thorough.

However:

```
100% Multiple-Condition Coverage
does NOT guarantee
100% Path Coverage
```

### 8. Path Coverage

Path coverage asks whether **every possible execution path** through the program has been tested.

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
100% Multiple Condition
→ 100% Condition
→ 100% Decision/Branch
```

but still does not guarantee path coverage.

---
### 9. Fault Injection, Instrumentation, and Loop Testing

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
