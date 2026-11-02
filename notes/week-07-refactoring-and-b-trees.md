---
title: "COMP6442 Week 7 - Refactoring and B-Trees"
course: COMP6442
week: 7
tags:
  - COMP6442
  - refactoring
  - SOLID
  - b-trees
---

# Week 7 - Refactoring and B-Trees

> **Main ideas**
> **Refactoring:** improve internal structure while preserving external behaviour.
> **SOLID:** design classes and interfaces that are easier to maintain, extend and test.
> **B-tree:** a balanced multiway search tree that reduces disk accesses by storing multiple keys per node.

Source: **R = `Week 07 - B-trees Refactoring - COMP2100_6442 - Semester 2 2026 (2)(4).pdf`**. All references below use **1-based PDF pages, counting the cover as page 1**, not printed slide labels. See the [lecture source index](lecture-source-index.md). Clarifications and supplemental examples are labelled where they go beyond the slides.

| Topic | PDF pages |
| --- | --- |
| Refactoring definition, goals, workflow | R4–12 |
| SOLID: SRP / OCP / LSP / ISP / DIP | R14–18 / R19–27 / R28–34 / R35–38 / R39–41 |
| DRY, KISS, smells and defensive copies | R42–57 |
| B-tree purpose, order, height and capacity | R60–67 |
| Search / insertion / deletion | R68 / R69–73 / R75–80 |

## 1. Refactoring

### Definition and goals

**Refactoring restructures existing code without changing its externally observable behaviour.** It removes design weaknesses and makes future changes easier.

| Goal | Meaning |
| --- | --- |
| Extensibility | Easier to add functionality |
| Performance | Meet timing and resource requirements |
| Maintainability | Easier to correct faults and modify code |
| Readability | Easier to understand code |
| Reliability | Continue operating correctly over time |
| Scalability | Handle increased demand effectively |

The lecture connects refactoring with the Agile values: individuals and interactions, working software, customer collaboration, and responding to change.

### When and how

- **Before adding functionality:** improve the existing structure first.
- **During code review:** identify and address code smells.
- Make **small, incremental changes**; the lecture suggests 200–300 lines at a time.
- Run existing tests after each step and use peer review or pair programming.
- Keep large architectural changes separate; the lecture suggests a later sprint.

**Definition of done:** structure improved, no new functionality, all existing tests pass.

*Source: R4–12.*

## 2. SOLID principles

| Principle                           | Core rule                                                  | Lecture example / application                                                             |
| ----------------------------------- | ---------------------------------------------------------- | ----------------------------------------------------------------------------------------- |
| **S — Single Responsibility (SRP)** | One responsibility; one reason to change                   | Separate reading data, calculating the average, and displaying results                    |
| **O — Open–Closed (OCP)**           | Open for extension, closed for modification                | Add `FlyingCar` as an extension instead of repeatedly rewriting `Car`                     |
| **L — Liskov Substitution (LSP)**   | A subtype must honour its supertype's behavioural contract | An account that rejects required withdrawals cannot safely replace a withdrawable account |
| **I — Interface Segregation (ISP)** | Clients should not depend on methods they do not need      | Separate `Flyable` from `Oviparous` (egg-laying)                                          |
| **D — Dependency Inversion (DIP)**  | Depend on abstractions, not concrete implementations       | `Program` depends on `DAO`, implemented by `JSON` or `XML`                                |

### Coupling and single responsibility

*Source: R15–18.*

**Coupling** = how strongly one component depends on another.

- **High coupling:** changing one component can force changes elsewhere.
- **Loose coupling:** components are easier to replace, reuse and test independently.
- Separate responsibilities into focused classes; avoid a class that reads files, performs calculations and renders output itself.

### Open–closed principle

*Source: R19–27.*

Use a stable interface or base class so new implementations can be added without changing existing client logic.

**Warning sign:** every new account type or football-player role requires another branch in the same client `switch`.

**Typical fix:** move type-specific behaviour into implementations of a common abstraction.

### Liskov substitution: preserve the contract

*Source: R28–34.*

Inheritance alone does not guarantee substitutability. Code using the parent type should continue working correctly with a subtype.

| Contract rule | Subtype requirement |
| --- | --- |
| Preconditions | Must not become stronger: **require no more** |
| Postconditions | Must not become weaker: **promise no less** |
| Invariants | Preserve the supertype's valid-state rules |
| History constraint | Do not introduce state changes forbidden by the supertype |
| Return type | May be narrower: e.g. `Number` → `Integer` |
| Exceptions | Do not add broader checked exceptions when overriding in Java |

Example: if `withdraw(amount)` is promised by an account abstraction, a fixed-term account that simply throws “withdrawal unsupported” breaks that promise. A better model gives withdrawal capability only to account types that support it.

> **Java clarification**
> The slides discuss **contravariant parameter types** as a general subtyping rule. In Java, overriding normally requires the same parameter types; widening a parameter creates an overload instead. Covariant return types are supported.

### Interface segregation

*Source: R35–38.*

The lecture's `Bird` abstraction requires both `layEggs()` and `fly()`. A cassowary cannot satisfy the flying requirement.

- `Oviparous`: declares `layEggs()`.
- `Flyable`: declares `fly()`.
- `Pigeon`: implements both interfaces.
- `Cassowary`: implements only `Oviparous`.

**ISP focuses on interface size and relevance; LSP focuses on behavioural substitutability.** The same poor design can violate both.

### Dependency inversion

*Source: R39–41.*

High-level application logic and low-level storage implementations both depend on an abstraction.

```java
interface DAO {
    void read();
    void write();
}

class Program {
    private final DAO dao;

    Program(DAO dao) {
        this.dao = dao;
    }

    void read() { dao.read(); }
    void write() { dao.write(); }
}
```

`JSON` and `XML` implement `DAO`; either can be passed to `Program` without rewriting it. Passing the dependency through the constructor is **constructor injection**; depending on `DAO` is **dependency inversion**.

*Source: R13–41; code is a condensed supplemental example with corrected Java syntax.*

## 3. DRY, KISS and code smells

- **DRY — Don't Repeat Yourself:** extract common logic and reuse it.
- **KISS — Keep It Small and Simple:** keep designs, classes and methods simple.
- **Code smell:** a sign of a potential design weakness, **not necessarily a bug**.

| Smell | Typical improvement |
| --- | --- |
| Mysterious names | Use descriptive names and language naming conventions |
| Hardcoding | Move changeable values into configuration; name meaningful constants; never hardcode passwords |
| Duplicated code | Extract a shared method or class |
| Long class / method | Split by responsibility; extract methods |
| Long parameter list | Group related values into meaningful objects |
| Excessive explanatory comments | Improve names and structure; retain useful explanations of intent |
| Primitive obsession | Use domain types or enums instead of loosely related integers/strings |
| Unsafe exposure of mutable objects | Use defensive copies when needed |
| Dead code | Delete unused code, files and parameters |

**Primitive obsession example:** prefer `enum Day { MONDAY, TUESDAY, WEDNESDAY }` to unrelated integer or string constants representing days.

**Defensive copy example:**

```java
List<Person> localPersons = new ArrayList<>(persons);
```

> **Shallow-copy clarification**
> This copies the list structure, **not its `Person` objects**. Adding/removing elements in the copy does not change the original list, but modifying a shared mutable `Person` can affect both. Copy individual objects too if independent object state is required.

*Source: R42–57. The shallow-copy explanation clarifies the example on R56.*

## 4. B-trees: purpose and properties

A **B-tree** generalises a binary search tree: each node stores multiple sorted keys and can have more than two children.

**Why use it?** Secondary-storage access is slow. Map a node to a disk block/page so one access retrieves several keys. A large branching factor makes the tree shallow and reduces page accesses.

### Order $m$

In this lecture, **order $m$ = maximum number of children per node**. Let:

$$
t = \left\lceil \frac{m}{2} \right\rceil
$$

| Property | Rule |
| --- | --- |
| Maximum children | $m$ |
| Maximum keys | $m-1$ |
| Minimum children, non-root internal node | $t$ |
| Minimum keys, any non-root node | $t-1$ |
| Internal root | At least 2 children and 1 key |
| Non-leaf node with $c$ children | Exactly $c-1$ keys |
| Leaves | All at the same depth |
| Keys within a node | Sorted; separate the ranges of its child subtrees |

A root that is also a leaf has no children; an empty tree may have zero keys.

**Order 5:** maximum 5 children / 4 keys; non-root minimum 3 children for internal nodes / 2 keys.

> **Minimum-key formula**
> Use **$\lceil m/2\rceil-1$**, consistent with the formal definition on R64 and minimum-key formula on R66. Some other slides use informal “$m/2$ keys” wording. An underflow means fewer than $\lceil m/2\rceil-1$ keys in a non-root node.

### Height and capacity

Let $h$ be the number of **edges** from the root to a leaf: a root-only tree has $h=0$.

For a nonempty B-tree:

$$
N_{\max}=(m-1)\sum_{i=0}^{h}m^i=m^{h+1}-1
$$

$$
N_{\min}=2\left\lceil\frac{m}{2}\right\rceil^h-1=2t^h-1
$$

Example: order $5$, height $2$ gives **17–124 keys**.

The tree remains balanced because every leaf is at the same depth. For fixed order, its height grows logarithmically with the number of keys.

*Source: R60–67. The order-5 height-2 calculation is a supplemental worked example.*

## 5. B-tree operations

### Search

*Source: R68; the numeric interval example below is supplemental.*

1. Search among the current node's sorted keys; binary search is useful for large nodes.
2. If the key is found, stop.
3. Otherwise, descend into the child whose range contains the target.
4. If there is no such child because this is a leaf, the key is absent.

For a node `[20, 50]`: the left subtree holds values below 20, the middle holds values between 20 and 50, and the right holds values above 50, assuming distinct keys.

### Insertion: overflow → split → promote

*Source: R69–73.*

1. Find the appropriate leaf and insert the key in sorted order.
2. If it has at most $m-1$ keys, finish.
3. If it overflows to $m$ keys, split it and **promote a middle key** to the parent.
4. If the parent overflows, repeat upwards.
5. If the root splits, create a new root; **height increases by 1**.

Following the lecture's split convention, promote the key at 1-based position $\lceil m/2\rceil$: the left node receives $\lceil m/2\rceil-1$ keys and the right receives $m-\lceil m/2\rceil$ keys. Split child pointers too when splitting an internal node.

**Lecture example, order 5** — each bracket is one node; children are listed left to right:

| Inserted so far | Root | Children |
| --- | --- | --- |
| 10, 30, 50, 70 | `[10, 30, 50, 70]` | None |
| Then 90 | `[50]` | `[10, 30]`; `[70, 90]` |
| Then 20, 40 | `[50]` | `[10, 20, 30, 40]`; `[70, 90]` |
| Then 60, 80 | `[50]` | `[10, 20, 30, 40]`; `[60, 70, 80, 90]` |
| Then 100 | `[50, 80]` | `[10, 20, 30, 40]`; `[60, 70]`; `[90, 100]` |

### Deletion: remove → repair underflow

*Source: R75–80, with occupancy clarified using R64/R66.*

1. If the target is internal, replace it with its **predecessor** (largest key in its left subtree) or **successor** (smallest key in its right subtree).
2. Remove that replacement key from its original leaf position.
3. If a non-root node has fewer than $t-1$ keys, repair it by borrowing or merging.

| Repair | When possible | What happens |
| --- | --- | --- |
| **Borrow / redistribute** | An adjacent sibling has more than the minimum keys | Parent separator moves down to the deficient node; sibling boundary key moves up to the parent |
| **Merge** | Combined nodes plus their parent separator fit within $m-1$ keys | Join the deficient node, separator and adjacent sibling; remove that separator and one child pointer from the parent |

- Borrow from the **left**: move the sibling's **largest** key up.
- Borrow from the **right**: move the sibling's **smallest** key up.
- Borrowing leaves the parent's key count unchanged, so repair does not propagate upwards.
- Merging can make the **parent** underfull, so repair may continue upwards.
- If the root becomes empty with one child, that child becomes the root; **height decreases by 1**.
- For internal-node repair, move the relevant child pointers as well.

**Lecture order-5 examples [R77–78]** (minimum 2 keys per non-root node):

| Case | Before deletion | After repair |
| --- | --- | --- |
| Merge after deleting 5 | Root `[10, 20]`; children `[2, 5]`, `[13, 14]`, `[22, 24]` | Root `[20]`; children `[2, 10, 13, 14]`, `[22, 24]` |
| Borrow after deleting 22 | Root `[20]`; children `[2, 10, 13, 14]`, `[22, 24]` | Root `[14]`; children `[2, 10, 13]`, `[20, 24]` |

*Source: R68–80. Deletion conditions above use the formal occupancy bounds on R64/R66; the informal thresholds on R76–78 should not override those bounds.*

### Supplemental order-3 trace

Order 3 permits **1–2 keys in each non-root node**, and an internal node has one more child than keys. The following small examples apply R64/R66/R69/R75–78; they are independently constructed.

| Operation | Root | Children, from left to right | Explanation |
| --- | --- | --- | --- |
| Insert 10, then 20 | `[10,20]` | None | Root is a leaf and still within capacity |
| Insert 30 | `[20]` | `[10]`; `[30]` | Three-key overflow: promote 20 |
| Insert 40 | `[20]` | `[10]`; `[30,40]` | Leaf has room |
| Insert 50 | `[20,40]` | `[10]`; `[30]`; `[50]` | Split the right leaf and promote 40 |
| Delete 10 from the preceding tree | `[40]` | `[20,30]`; `[50]` | Empty left child cannot borrow from `[30]`; merge with separator 20 |

**Separate borrowing example:** start with root `[20]`, left child `[10]`, right child `[30,40]`. Deleting 10 leaves the left child empty. Move parent separator 20 down into it and move sibling's smallest key 30 up: root becomes `[30]`, with children `[20]` and `[40]`. The parent's key count is unchanged.

**Root-collapse example:** root `[20]` with children `[10]` and `[30]`; delete 10. Merge the empty left child, separator 20, and `[30]` into `[20,30]`. The old root now has no keys and one child, so `[20,30]` becomes the new root and height falls from 1 to 0.

### Common B-tree mistakes

- **Order is not key count:** order `m` permits `m−1` keys per node.
- A non-leaf node with **one key has two children**, not three. A node containing `[5]` therefore cannot directly own `[3]`, `[8,10]`, and `[12]` as three children.
- **Supplemental correction:** with keys `3,5,8,10,12` in an order-3 tree, root `[5,10]` and children `[3]`, `[8]`, `[12]` is valid. Each child lies in its separator range.
- Split/promote and merge/borrow must move **child pointers** as well as keys for internal nodes.
- Borrowing moves a separator **through the parent**, rather than copying a sibling key directly across.
- Check all leaves stay at the same depth, including after a root split or root collapse.

These reminders apply the invariants on R64/R66 and update procedures on R69/R75–80. For fixed order, search, insert and delete take `O(log n)` time. With a variable order, distinguish tree height/page accesses from the work of scanning or shifting keys inside a node.

## 6. Quick revision checklist

- [ ] Define refactoring and distinguish it from adding functionality.
- [ ] Recognise each SOLID violation and suggest a repair.
- [ ] Explain LSP using preconditions, postconditions and substitutability.
- [ ] Identify code smells; distinguish a shallow copy from a deep copy.
- [ ] Calculate B-tree key/child bounds from order $m$.
- [ ] Use the minimum/maximum key formulas with the correct height convention.
- [ ] Trace search, insertion splits, deletion borrowing and merging.
- [ ] Explain why a B-tree reduces disk accesses.

**R74 practice (printed slide 75):** insert `20, 11, 15, 3, 5, 7, 12, 15, 16, 19, 25, 30, 33, 37, 22, 23, 26, 31, 42, 35, 6, 47` using orders 3 and 5; determine the final height. The sequence repeats `15`; establish the expected duplicate-key policy before solving.

## Historical assessment reminder from this deck

The following describes the **9 October hackathon**, not the final examination. According to **R3**:

- **Hackathon:** Friday 9 October 2026, 2–6 pm, Melville Hall G01; teams of 5.
- **Coverage:** through Week 8 inclusive, excluding Android Studio.
- **Marking:** 80% individual + 10% group task + 10% average of the top two other team members' individual scores.
- Bring a charged device with the necessary course software.
- **“No GenAI”**; course staff must be able to see your screen.

