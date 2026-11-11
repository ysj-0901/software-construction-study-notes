# Quiz 036: JML pure methods in preconditions

**Original label:** Week 08 Design by Contract lecture exercise, PDF p. 44.  
**Reviewed:** 11 November 2026.  
**Topic:** Pure methods in assertions, preconditions, postconditions and pre-state values.  
[Back to quiz index](README.md)

## Question

Which one of the following alternatives is **INCORRECT** about JML?

### Options

a) `\old(balance)` is used to refer to the value of the variable balance at the time of entry into a method.

b) Precondition for a method can be specified using the instruction “requires”.

c) In the JML expression `//@ requires amount <= getBalance();`, the method `getBalance()` is not a pure method.

d) Postcondition for a method can be specified using the instruction “ensures”.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choice: c — it is the incorrect statement.** The selected answer is correct.

In the course's terminology, methods called in JML assertion expressions must be **pure**: checking a contract must not modify the existing program state. Therefore, `getBalance()` must be pure for this use to be valid.

### Why each option is correct or incorrect

| Option | Evaluation |
| --- | --- |
| a | Correct: `\old(balance)` denotes the balance at method entry, rather than its value after execution. |
| b | Correct: `requires` specifies a precondition. |
| c | Incorrect: `getBalance()` must be pure to be called in this precondition. |
| d | Correct: `ensures` specifies a postcondition that must hold on normal return when the applicable preconditions hold. |

### Additional example: checking a balance without changing it

This example is separate from the supplied question.

```java
class Account {
    public int balance;

    //@ ensures \result == balance;
    /*@ pure @*/
    public int getBalance() {
        return balance;
    }

    /*@ requires 0 <= amount && amount <= getBalance();
      @ assignable balance;
      @ ensures balance == \old(balance) - amount;
      @*/
    public void debit(int amount) {
        balance -= amount;
    }
}
```

`getBalance()` reads and returns the balance without changing it, so it can be used in the precondition. The `debit` method itself changes the balance and is not pure.

If `getBalance()` incremented `balance` before returning it, merely checking the precondition would change the account. That would violate the requirement for a pure method.

Using a method in a JML expression does **not** automatically make it pure. Its declaration/specification and implementation must satisfy the relevant purity rules.

### Syntax reminders

| Syntax | Meaning |
| --- | --- |
| `requires P;` | The caller must establish P before the call. |
| `ensures Q;` | Q must hold when the method returns normally under its applicable preconditions. |
| `\old(expression)` | Evaluate the expression in the method-entry state. |
| `\result` | The method's return value. |
| `/*@ pure @*/` | Declare the method pure; it must not modify existing program state. |

</details>

## Common mistakes

- Reading “is not a pure method” as “is a pure method”.
- Thinking every method with a JML contract must be pure: it is the methods called within assertion expressions that must meet the purity requirement.
- Assuming that a getter's name alone guarantees that its implementation has no side effects.
- Confusing `\old(balance)` with the current balance or with `\result`.

**Memory aid:** Checking a contract must not change the state being checked.

**Summary:** Methods called in JML assertions must be pure, so checking a precondition must not modify existing program state.

## Sources and related notes

- User-supplied lecture exercise screenshot and selected option c, reviewed 11 November 2026.
- Course source C: *Week 08 - Design by Contract - COMP2100_6442 - Semester 2 2026-1.pdf*, PDF p. 44; `requires` p. 28, `ensures` and `\old` p. 29, and pure methods p. 34.
- [Design by Contract notes](../notes/week-08-design-by-contract.md)
- [OpenJML: calling methods in specifications](https://www.openjml.org/tutorial/MethodsInSpecifications.html) — current documentation further distinguishes levels of purity; the primitive-returning getter in this exercise supports the course's expected answer.
- [Lecture source index](../notes/lecture-source-index.md)
- [Related quiz: Design by Contract and interface enforcement](035-design-by-contract-interface-enforcement.md)
