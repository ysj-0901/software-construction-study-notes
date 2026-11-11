# 041 — State transitions: return a new state or call a setter?

Original label: **Question 4**; quiz week not supplied. Recorded 11 November 2026.

## Question

In the State design pattern, some state-based operations will need to modify the current state. There are two obvious ways this can be achieved:

1. These operations return the new state, which is then assigned within the Context class.
2. These operations execute a method such as `Context.setState(State newState)`.

Which approach is generally preferred? Why?

| Option | Answer |
| --- | --- |
| a | (i), as it reduces coupling between the Context and State |
| b | (ii), as if some state subclasses do not change the state, they do not need to return anything |
| c | (ii), as it allows state operations to be run from classes other than Context |
| d | (i), as it is necessary if the Context class uses the Singleton pattern |

<details>
<summary>Answer and explanation</summary>

**Correct answer: a. Return the new state to reduce coupling.**

The Context already depends on the State interface to delegate operations. Returning the next State lets the Context update its own field without requiring the State to know the Context or its setter.

**Supplemental Java example:**

```java
interface State {
    State handle();
}

class Waiting implements State {
    public State handle() {
        return new Running();
    }
}

class Running implements State {
    public State handle() {
        return this; // No transition: keep the current state.
    }
}

class Context {
    private State state = new Waiting();

    public void handle() {
        state = state.handle();
    }
}
```

The key line is `state = state.handle();`: the State determines the next state, while the Context stores it.

With approach (ii), a State typically needs a Context reference to call `context.setState(new Running());`. This introduces a dependency back to Context.

| Alternative | Why it is not the best answer |
| --- | --- |
| b | A state that does not transition can simply return `this`; avoiding a return value does not outweigh the stated coupling benefit. |
| c | Returning a State does not inherently restrict who can call the operation. A setter-based transition still needs access to a Context. |
| d | Singleton controls how many Context instances exist; it does not require a particular transition mechanism. |

**Common mistake:** thinking that the setter-based variant is always wrong. Both variants can implement the State pattern; the quiz prefers returning the next state because it avoids an unnecessary dependency on Context. Some designs legitimately give State access to Context for other work.

**One-sentence summary:** Returning the next State lets the Context update itself and reduces coupling by avoiding a State-to-Context dependency solely for transitions.

</details>

## Lecture references

- **U, PDF pp. 69–77**: State pattern, Context delegation and the UserSession/UserState example.
- U: `week02-uml-dp(6).pdf`.
- The preference between the two transition mechanisms is confirmed by the supplied quiz feedback; the code above is a supplemental explanation.
- [State pattern notes](../notes/week-02-uml-and-design-patterns.md#2-state) · [Related question 006](006-state-pattern-vs-boolean.md) · [Lecture source index](../notes/lecture-source-index.md).

References use PDF viewer pages, counting the cover as page 1.

[Back to quiz index](README.md)
