# Quiz 022: Review a JUnit 4 factorial test class

**Original label:** Question 1. Quiz week not supplied.  
**Topic:** Test timeouts, expected exceptions and parameterised tests.  
[Back to quiz index](README.md)

## Question

`MyMath.factorial` computes the factorial of a non-negative integer and should throw an exception for a negative input. Review the supplied test class and select every accurate criticism.

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class MyMathTests {
    @Test
    public void testLargeInput() {
        assertEquals(120, MyMath.factorial(6));
        assertEquals(720, MyMath.factorial(5));
    }

    @Test
    public void testZero() {
        assertEquals(1, MyMath.factorial(0));
    }

    @Test
    public void testOne() {
        assertEquals(1, MyMath.factorial(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeInput() {
        MyMath.factorial(-3);
        MyMath.factorial(-1);
    }
}
```

### Options

1. The test class does not achieve full statement coverage, so it may not uncover all the bugs in the program.
2. If MyMath.factorial enters an infinite loop, these test cases will not fail.
3. If only one of the calls to MyMath.factorial inside the testNegativeInput throws an exception, that test case will still pass.
4. These unit tests could not easily be used in integration testing, because they would only be suitable in a top-down integration testing approach.
5. The test class is missing methods with the @Before and @After annotations, which are necessary to reset the MyMath class.
6. Because the test cases follow a consistent layout, code duplication could be reduced by using parameterised testing, at least for the non-negative inputs.
7. If only one of the calls to MyMath.factorial inside the testLargeInput gives the expected output, that test case will still pass.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choices are 2, 3 and 6**, as marked in the quiz feedback.

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | Incorrect | The implementation of factorial is missing, so its statement coverage cannot be inferred from the test class. Full statement coverage would still allow some bugs to go undetected. |
| 2 | Correct | An infinite loop can leave the test hanging. The shown tests have no timeout to turn an overlong run into a reported failure. |
| 3 | Correct | The expected-exception annotation checks the entire test method. One escaping exception of the expected type can satisfy it. |
| 4 | Incorrect | These unit tests check a calculation. Nothing in them restricts the integration strategy to top-down testing. |
| 5 | Incorrect | Setup and teardown are needed when a test has state or resources to prepare or clean up. The question gives no evidence that MyMath needs a reset. |
| 6 | Correct | The same assertion can be run with multiple input and expected-output pairs. |
| 7 | Incorrect | A failed assertion fails the test method. Both assertions must succeed for testLargeInput to pass. |

### 2. A timeout can report a hang as a failure

If a call loops forever, execution never reaches the assertion's comparison. With the shown annotations, the runner can keep waiting. In JUnit 4, `@Test(timeout = 1000)` gives the test a one-second time limit. Choose a limit appropriate to the environment and expected work.

### 3. The expected exception is checked once per test method

In the screenshot, the expected type is `IllegalArgumentException`. The choice uses “an exception” as shorthand for an exception that matches that expected type.

| First call: factorial(-3) | Second call: factorial(-1) | Outcome |
| --- | --- | --- |
| Throws the expected exception | Never reached | Passes |
| Returns normally | Throws the expected exception | Passes |
| Returns normally | Returns normally | Fails |

Give each input an independent test so an exception from one cannot mask the behaviour of the other:

```java
@Test(expected = IllegalArgumentException.class, timeout = 1000)
public void rejectsNegativeThree() {
    MyMath.factorial(-3);
}

@Test(expected = IllegalArgumentException.class, timeout = 1000)
public void rejectsNegativeOne() {
    MyMath.factorial(-1);
}
```

### 6. A parameterised test reuses the assertion

Use one assertion, `assertEquals(expected, MyMath.factorial(input))`, with a separate run for each row. A JUnit 4 parameterised class can use the `Parameterized` runner and a `@Parameters` data source.

| Input | Correct expected result |
| ---: | ---: |
| 0 | 1 |
| 1 | 1 |
| 5 | 120 |
| 6 | 720 |

**Additional code review finding.** The screenshot's `testLargeInput` swaps the expected values for 5 and 6. A correct factorial implementation would fail its first assertion. The swapped values are separate from the seven listed choices.

</details>

## Common mistake

Treating an expected-exception test as though it verifies every call in its body. The first matching exception ends that test method. Grouping assertions behaves differently because the first failing assertion makes the test fail.

**Memory aid:** One expected-exception check per input. For repeated value checks, reuse the assertion with a table of inputs and expected results.

## Sources and related notes

- User-supplied screenshots of Question 1 and its accepted choices.
- [Software testing notes](../notes/week-05-software-testing.md). The source index locates JUnit 4 in T, PDF pp. 18–38, and statement coverage in T, PDF pp. 51–57.
- [JUnit 4.13.2 Test annotation](https://github.com/junit-team/junit4/blob/r4.13.2/src/main/java/org/junit/Test.java)
- [JUnit 4.13.2 Parameterized runner](https://github.com/junit-team/junit4/blob/r4.13.2/src/main/java/org/junit/runners/Parameterized.java)
- [Back to quiz index](README.md)
