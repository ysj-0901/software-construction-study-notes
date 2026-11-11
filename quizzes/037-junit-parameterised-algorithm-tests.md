# Quiz 037: JUnit 4 parameterised tests across algorithm implementations

**Original label:** Question 1; quiz week not supplied.  
**Reviewed:** 11 November 2026.  
**Topic:** JUnit 4 parameterised runners, data providers, field injection and static versus instance methods.  
**Review priority:** High — currently difficult; revisit the execution flow and annotation roles.  
**Lecture:** Week 5 Software Testing, `week05-testing(2).pdf`, PDF pp. 33–34; timeout on p. 32. Page numbers count the cover as page 1.  
[Back to quiz index](README.md)

## Question

A student wants to apply parameterised testing to three implementations of an algorithm. All three classes implement the same algorithm interface. Imports are omitted.

The supplied code contains these declarations and test:

```java
// Line 1
@RunWith(JUnit4ClassRunner.class)

// Line 4: under @Parameterized.Parameters
public static Collection<Object[]> data()

// Lines 7–8
@Parameterized.Parameter()
public IAlgorithm implementation;

// Lines 9–12
@Test(timeout = 1000)
public static void test() {
    assertEquals(7, implementation.run(3));
}
```

The data provider is intended to supply instances of `Imp1`, `Imp2` and `Imp3`. Which issues must be fixed to allow the tests to work? **Select all that apply.**

### Options

a) On line 10, the test function should be non-static.

b) On line 7, the parameter must be given the index 0.

c) After line 8, additional parameters must be added to contain the actual and expected values for the test case.

d) On line 10, the test function should be non-static.

e) On line 1, the runner must be changed to a parameterised runner.

f) On line 4, the data function should be non-static.

**Transcription note:** The screenshot repeats the non-static test-method option as a and d. It also uses inconsistent class/interface names between the prose and code. The corrected example below consistently uses `IAlgorithm` and `Imp1/2/3`, and explicitly represents each dataset as an `Object[]` to match `Collection<Object[]>`.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choices: a, d and e as displayed — two distinct fixes.**

1. Change the runner to `@RunWith(Parameterized.class)`.
2. Remove `static` from `test()`.

### Start with the purpose

All three algorithms should produce 7 for input 3. Instead of writing three nearly identical tests, write one test and run it with three different algorithm objects.

| Run | Object injected into implementation | Check |
| --- | --- | --- |
| 1 | `new Imp1()` | `implementation.run(3)` equals 7 |
| 2 | `new Imp2()` | `implementation.run(3)` equals 7 |
| 3 | `new Imp3()` | `implementation.run(3)` equals 7 |

The varying parameter is the **algorithm object**. The input 3 and expected result 7 can remain fixed.

### Corrected test class

This example assumes the three implementation classes and their interface already exist.

```java
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IAlgorithmParameterizedTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { new Imp1() },
            { new Imp2() },
            { new Imp3() }
        });
    }

    @Parameterized.Parameter
    public IAlgorithm implementation;

    @Test(timeout = 1000)
    public void test() {
        assertEquals(7, implementation.run(3));
    }
}
```

### How JUnit runs this example

1. The parameterised runner calls the static `data()` method to obtain three datasets.
2. For each dataset, JUnit creates a test instance and places that dataset's first value into its `implementation` field.
3. JUnit calls that instance's `test()` method.
4. The assertion compares the expected value 7 with the actual result of `implementation.run(3)`.

There is one test method and three datasets, so there are three test executions.

### Read the annotations by their roles

| Syntax | Role |
| --- | --- |
| `@RunWith(Parameterized.class)` | Select the runner that repeats tests for each dataset. |
| `@Parameterized.Parameters` | Mark the public static method that supplies all datasets. |
| `@Parameterized.Parameter` | Mark the public instance field that receives a value from the current dataset. |
| `@Test(timeout = 1000)` | Mark the test method; an execution exceeding 1000 milliseconds fails. |

**Plural Parameters supplies data; singular Parameter receives a value.**

### Why test() must be non-static

`implementation` is an instance field: each test object holds its own algorithm reference.

A static method belongs to the class rather than a particular instance. The original static method cannot directly access the instance field `implementation`; this is a Java compile-time error.

In addition, an ordinary JUnit 4 `@Test` method is a public, non-static, no-argument method returning `void`. JUnit constructs a test instance and invokes the test on that instance.

### Why data() remains static

The runner needs to obtain the datasets used to configure the test instances. JUnit 4 requires the `@Parameters` provider to be **public and static**.

Remember the contrast:

```java
public static Collection<Object[]> data()  // provides all datasets
public void test()                        // checks one configured instance
```

### Why each option is correct or incorrect

| Option | Select? | Reason |
| --- | --- | --- |
| a | Yes | The test must be non-static. |
| b | No | The default field parameter index is already 0. |
| c | No | Expected and actual values need not be injected fields; 7 is fixed and the actual value is computed. |
| d | Yes | A duplicate of a in the screenshot. |
| e | Yes | The parameterised runner is required for this setup. |
| f | No | The data provider must remain static. |

### What does the default index 0 mean?

These are equivalent:

```java
@Parameterized.Parameter
@Parameterized.Parameter()
@Parameterized.Parameter(0)
```

Index 0 means **the first value within each dataset**, not only the first dataset. In this example, each row contains exactly one algorithm object, so all three rows use index 0.

For comparison, a dataset such as `{ new Imp1(), 3, 7 }` would contain three values at indexes 0, 1 and 2. That is an optional alternative design, not a requirement of this question.

</details>

## Common mistakes

- Thinking parameterised tests can vary only numeric inputs; they can also vary implementation objects.
- Confusing the plural data-provider annotation with the singular field annotation.
- Making both methods static, or making both non-static.
- Thinking an omitted parameter index prevents injection; its default is 0.
- Interpreting index 0 as “run only the first dataset”.
- Forgetting that each row must match the number of injected parameters when using `Collection<Object[]>`.

**Memory aid:** Parameterised runner; static provider; non-static test; index defaults to zero.

**Summary:** JUnit 4 parameterised tests use a Parameterized runner, a static data provider and a non-static test method to execute the same checks with different parameter values.

## Sources and related notes

- User-supplied Question 1 screenshots and quiz feedback, reviewed 11 November 2026.
- [Week 5 Software Testing notes](../notes/week-05-software-testing.md): parameterised tests, T PDF pp. 33–34; timeouts, p. 32.
- [Lecture source index](../notes/lecture-source-index.md)
- [JUnit 4 Parameterized runner](https://junit.org/junit4/javadoc/4.13.2/org/junit/runners/Parameterized.html)
- [JUnit 4 Parameter annotation](https://junit.org/junit4/javadoc/4.13.2/org/junit/runners/Parameterized.Parameter.html)
- [JUnit 4 Parameters annotation](https://junit.org/junit4/javadoc/4.13.2/org/junit/runners/Parameterized.Parameters.html)
- [JUnit 4 Test annotation](https://junit.org/junit4/javadoc/4.13.2/org/junit/Test.html)
- [Related quiz: JUnit 4 factorial test review](022-junit-factorial-test-review.md)
