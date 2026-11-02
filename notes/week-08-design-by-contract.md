# Week 8 — Design by Contract and JML

Source: **C**, `Week 08 - Design by Contract - COMP2100_6442 - Semester 2 2026-1(1).pdf`. References below use **1-based PDF viewer pages**, including the cover. See the [lecture source index](lecture-source-index.md).

## 1. The contract: who must establish what?

A contract specifies a component's obligations and guarantees without prescribing its algorithm. Java interfaces establish a **syntactic** contract checked by the compiler; behavioural conditions require more than matching method signatures. **[C pp. 7–12]**

| Condition | Meaning | Responsibility |
| --- | --- | --- |
| Precondition | Must hold before the call; can constrain arguments and object state | Caller establishes it; violation indicates a caller bug |
| Postcondition | Must hold after normal completion, assuming the precondition held | Callee establishes it; violation indicates an implementation bug |
| Class invariant | Must hold at observable object boundaries and after construction | Constructors establish it; methods preserve it |

An invariant may temporarily be false **inside** a method, but must be restored at its boundary; the lecture also requires it to survive exceptional exits. A stronger precondition restricts callers more and gives the implementation less work. **[C pp. 13–19, 29, 32]**

## 2. JML vocabulary

JML specifications appear in `//@ ...` or `/*@ ... @*/` comments. Ordinary Java compilation ignores them; OpenJML provides specification checking. **[C pp. 22–23, 40]**

| Construct | Read it as | Source |
| --- | --- | --- |
| `requires P;` | Caller must establish P | C p. 28 |
| `ensures Q;` | Q holds on normal return | C p. 29 |
| `\old(x)` | Value of x on method entry | C p. 29 |
| `\result` | Returned value | C p. 29 |
| `invariant I;` | Class-wide consistency condition | C p. 32 |
| `pure` | Query without side effects; usable in specifications | C p. 34 |
| `assignable balance;` | Only the specified field may change | C pp. 35–36 |
| `signals (Exception) Q;` | Q holds when the specified exception is thrown | C p. 38 |

Multiple `requires` clauses within one specification case are conjoined. `requires true` imposes no additional requirement; `ensures true` promises no useful result property. The lecture's reference-type default is non-null; explicitly use `nullable` when needed. **[C pp. 31, 33, 37]**

## 3. Worked contract: a bounded bank account

This is an **original illustrative adaptation** of the lecture's account example, with the missing sufficient-funds condition made explicit. **[C pp. 27–35]**

```java
public class ContractAccount {
    public static final int MAX_BALANCE = 1000;
    public int balance = 0;

    //@ public invariant 0 <= balance && balance <= MAX_BALANCE;

    /*@ requires 0 <= amount && amount <= balance;
      @ assignable balance;
      @ ensures balance == \old(balance) - amount;
      @ ensures \result == balance;
      @*/
    public int debit(int amount) {
        balance -= amount;
        return balance;
    }
}
```

For an entry balance of 100 and amount 30, the result and new balance must both be 70. The precondition excludes overdrafts and negative amounts. `amount >= 0` alone would not preserve the non-negative balance invariant. Zero is permitted by `>= 0`; do not paraphrase it as strictly positive.

The public field keeps this teaching example's specification visibility simple; production encapsulation would usually hide the representation. Java compilation does **not** establish that its JML contract is verified.

## 4. Exam checks

- Separate the state **before**, state **after**, and returned value.
- A normal-return `ensures` clause is not an exceptional postcondition.
- Do not use assignments, increments, or impure method calls inside specifications.
- Check constructors and all methods against the same invariant.
- Treat contracts as precise documentation, not an automatic guarantee that any implementation is bug-free. **[C pp. 26, 29, 32, 34, 38–40]**

Related: [SOLID and substitutability](week-07-refactoring-and-b-trees.md), [testing](week-05-software-testing.md).
