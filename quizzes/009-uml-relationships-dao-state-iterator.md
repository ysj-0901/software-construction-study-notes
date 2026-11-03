# Quiz 009: Identify UML relationships in DAO, State and Iterator

**Original label:** Question 5; quiz week not supplied.  
**Result shown:** 0.1 / 0.1; correct answer selected.  
[Back to quiz index](README.md)

## Question

The lab scenario states:

1. `DAO` is an abstract class; `UserDAO` and `PostDAO` extend it.
2. `StateManager` holds a reference to the current `UserState`; concrete states are `GuestState`, `MemberState` and `AdminState`.
3. `PostDAO.getAllMessages()` returns an `Iterator<Message>`, assumed for this quiz to be implemented by `MessageIteratorImpl`.

Identify the most appropriate UML relationship for each connection, choosing from generalisation, association with multiplicity, dependency, realisation, composition and aggregation.

| No. | Connection | Notation shown in the question |
| --- | --- | --- |
| 1 | UserDAO to DAOState | Solid line with hollow triangle |
| 2 | Manager to UserState | Solid line; multiplicity 1..1 at UserState |
| 3 | GuestState, MemberState and AdminState to UserState | Solid lines with hollow triangles |
| 4 | PostDAO to Iterator&lt;Message&gt; | Dashed line with open arrowhead |
| 5 | MessageIteratorImpl to Iterator&lt;Message&gt; | Dashed line with hollow triangle |

**Naming inconsistencies in the screenshot:** the scenario says `DAO` and `StateManager`, while the relationship list says `DAOState` and `Manager`. The explanation below uses `DAO` and `StateManager` consistently, interpreting these as the intended corresponding classes.

### Options

Each sequence answers relationships 1–5 in order.

- **A.** realisation; composition (1..1); dependency; association; generalisation
- **B.** association; generalisation; realisation; generalisation; composition
- **C.** generalisation; association (1..1); generalisation; dependency; realisation
- **D.** dependency; aggregation; association; composition; aggregation

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: C — generalisation; association (1..1); generalisation; dependency; realisation.**

| No. | Relationship | Why? | Direction / multiplicity |
| --- | --- | --- | --- |
| 1 | **Generalisation** | `UserDAO extends DAO`: inheritance from a class, even though the superclass is abstract. | Hollow triangle points to `DAO`. |
| 2 | **Association (1..1)** | `StateManager` stores a reference to its current state. | `1..1` at the UserState end means exactly one current UserState per manager. |
| 3 | **Generalisation** | The solid-line hollow triangles indicate that the concrete states inherit from `UserState`. | Triangles point to `UserState`. |
| 4 | **Dependency** | `PostDAO` uses `Iterator<Message>` as a return type. | Dashed open arrow points to the needed/used type, `Iterator<Message>`. |
| 5 | **Realisation** | `MessageIteratorImpl` implements the iterator interface. | Dashed hollow triangle points to `Iterator<Message>`. |

Options A, B and D confuse these distinct relationships; none matches all five.

### 1. Extending an abstract class is still inheritance

```java
abstract class DAO { }

class UserDAO extends DAO { }
class PostDAO extends DAO { }
```

An abstract class remains a **class**. Extending it is generalisation, not interface realisation.

### 2. Holding one state is an association

Illustrative structure:

```java
class StateManager {
    private UserState currentState;

    StateManager(UserState initialState) {
        currentState = java.util.Objects.requireNonNull(initialState);
    }

    void setState(UserState nextState) {
        currentState = java.util.Objects.requireNonNull(nextState);
    }
}
```

The manager remains connected to a state through a stored reference. The non-null invariant models exactly one current state.

**Multiplicity counts simultaneous links, not all objects ever used.** The manager can switch from GuestState to MemberState while still holding exactly one current state at each valid point.

Also, `1..1` at the UserState end does **not** establish that each state belongs to exactly one manager. The other end's multiplicity would be needed for that conclusion.

**Why not composition or aggregation?**

- A reference field alone does not establish a whole–part relationship.
- Composition additionally expresses exclusive whole–part ownership and lifecycle responsibility.
- Aggregation additionally expresses a shared whole–part relationship.
- This scenario only establishes that the manager holds its current state. **Association is the most appropriate answer.**

### 3. State classes inherit from their parent class

The diagram's solid lines with hollow triangles indicate:

```java
abstract class UserState { }

class GuestState extends UserState { }
class MemberState extends UserState { }
class AdminState extends UserState { }
```

A different State-pattern implementation could use an interface, in which case concrete implementations would use realisation. **Read the actual diagram and declarations, not just the pattern name.**

### 4. Returning a type creates a dependency

```java
abstract class PostDAO extends DAO {
    public abstract java.util.Iterator<Message> getAllMessages();
}
```

This illustrative signature uses the iterator interface without implementing it. Therefore:

**PostDAO needs/uses Iterator&lt;Message&gt; → dependency.**

### 5. Implementing an interface is realisation

The relevant declaration is:

```java
class MessageIteratorImpl implements java.util.Iterator<Message> {
    // Implement hasNext() and next().
}
```

This is a declaration sketch, not a complete class. The concrete implementation fulfils the iterator interface's contract: **realisation**.

### Memory aid

| Code or design evidence | Most appropriate relationship |
| --- | --- |
| Class `extends` another class | Generalisation |
| Class `implements` an interface | Realisation |
| Stored reference / ongoing connection | Association |
| Parameter, local use, return type or object creation | Dependency |
| Explicit whole–part ownership and lifecycle | Composition |
| Explicit shared whole–part relationship | Aggregation |

**Abstract superclass ≠ interface. One reference ≠ composition. Returning an interface ≠ implementing it.**

</details>

## Lecture references

Source: `week02-uml-dp(6).pdf` (**U**). Page numbers are **PDF viewer pages, with the cover as page 1**.

- **PDF pp. 15–16:** inheritance and interface notation.
- **PDF pp. 17–19:** association versus dependency; stored references and method-use examples.
- **PDF pp. 20–21:** composition versus aggregation.
- **PDF p. 22:** multiplicity.
- **PDF pp. 69–77:** State pattern and the context's current-state reference.
- **PDF pp. 87–93:** Iterator pattern and its interfaces.

The question, class names and accepted answer come from the supplied quiz screenshots. These are lab-specific premises: do not assume every DAO implementation or every State pattern must use an abstract class. Code snippets above are explanatory additions.

[Lecture source index](../notes/lecture-source-index.md) · [Exam revision guide](../notes/final-exam-revision.md)
