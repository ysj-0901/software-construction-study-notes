# Quiz 006 - State pattern versus a boolean flag

- **Topic:** Behavioural design patterns / State
- **Original label:** Question 2; quiz week not supplied.
- **Lecture source:** U, `week02-uml-dp(6).pdf`, PDF pp. 69–77.

## Question

A developer wishes to store whether or not a user is logged in. They know this may be done with the State design pattern, or by using a boolean flag variable (and possibly additional variables to store data relevant to each state). Which of these are benefits of the State design pattern? Select all that apply.

### Options

1. The length of the resulting program, as measured in lines of code, will be substantially shorter under the State design pattern.
2. The State design pattern allows for changes in state reacting to external stimuli, for example a user being forcefully logged-out when they log in on another device. This is more difficult with the boolean flag approach.
3. The boolean flag approach is limited to only two states, which may be limiting.
4. Under proper implementation, the State design pattern makes it harder for the program to enter an illegal state related to variables for multiple states being simultaneously set.
5. The State design pattern clearly delineates behaviour belonging to each state, improving code readability.
6. If the program is extended to support some new behaviour (changing your password, say), then this can be added as a method to the abstract parent State class. This must then be implemented explicitly in all subclasses, which makes bugs related to changing your password while not being logged in less likely.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choices: 3, 4, 5 and 6**, matching the supplied quiz feedback.

The State pattern does more than record a status: it places state-dependent behaviour in separate classes and lets the context delegate to its current state.

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | False | Extra interfaces/classes often increase code length. Organisation, not fewer lines, is the benefit. |
| 2 | False | Both approaches can react to the same external event. A handler can set a boolean to false or transition to a logged-out state. Receiving the event is a separate concern. |
| 3 | True, for a single flag | A boolean has only true/false. State classes can represent LoggedOut, LoggedIn, Locked, AwaitingTwoFactor, etc. An enum or multiple flags can also represent more states; the pattern is not the only solution. |
| 4 | True, with proper implementation | One current state and well-encapsulated state-specific data reduce contradictory combinations. The pattern does not automatically eliminate every invalid state. |
| 5 | True | Logged-in behaviour lives together; logged-out behaviour lives together. This reduces repeated state checks across methods. |
| 6 | True, with an abstract operation | Adding an abstract operation requires each concrete state to supply or inherit an implementation. With no inherited implementation, an omitted method causes a compilation error, prompting the developer to handle that state explicitly. It does not prove the implementation is correct. |

### Simple example: one action, two behaviours

Assume this application permits password changes only while logged in. These examples illustrate the design, not actual authentication or password storage.

With a boolean:

```java
void changePassword() {
    if (loggedIn) {
        System.out.println("Password change allowed");
    } else {
        System.out.println("Please log in first");
    }
}
```

Other operations such as posting or viewing private data may need similar checks.

With the State pattern:

```java
abstract class UserState {
    abstract void changePassword();
}

class LoggedOut extends UserState {
    @Override
    void changePassword() {
        System.out.println("Please log in first");
    }
}

class LoggedIn extends UserState {
    @Override
    void changePassword() {
        System.out.println("Password change allowed");
    }
}

class UserSession {
    private UserState state = new LoggedOut();

    void onLoginSucceeded() {
        state = new LoggedIn();
    }

    void forceLogout() {
        state = new LoggedOut();
    }

    void changePassword() {
        state.changePassword();
    }
}
```

Example calls inside a method:

```java
UserSession session = new UserSession();
session.changePassword();       // Please log in first
session.onLoginSucceeded();      // Assume authentication already succeeded.
session.changePassword();       // Password change allowed
session.forceLogout();
session.changePassword();       // Please log in first
```

The **same call** behaves differently because the current state object changes. UserSession is the context; UserState defines the operations; LoggedIn and LoggedOut implement them.

### Why option 2 is the trap

For a remote logout event, the boolean version can simply handle it with:

```java
void onRemoteLogout() {
    loggedIn = false;
}
```

The State version can call `session.forceLogout()`. If other session data exists, it must also be handled consistently. Both designs still need a mechanism to receive the remote event.

Slide U p. 69 says State can respond to internal or external events. That is a capability, but it is **not an exclusive advantage over a boolean**.

### Why option 4 says "under proper implementation"

Suppose separate variables are intended to describe mutually exclusive states:

```java
boolean loggedIn = true;
boolean awaitingTwoFactor = true;  // Contradictory under this application's rules.
```

Or a bug leaves `loggedIn == true` while the required user data is missing.

A better design can store one current state object and keep the authenticated user's data inside LoggedIn, validated when that state is constructed. LoggedOut then does not carry authenticated-user data.

This stronger data-encapsulation example is an explanatory extension, not a claim that the lecture code guarantees it: the slides keep username in UserSession (U pp. 76–77), so transitions still need to update that field consistently.

### Why option 6 matters

If `changePassword()` is newly added as an **abstract** method, the developer must decide what each concrete state should do. LoggedIn permits the action; LoggedOut refuses it. A default implementation that permits the action would lose this benefit.

For only a simple two-state check, a boolean can be perfectly reasonable. State becomes more useful when many operations depend on the state or the state model grows.

</details>

## Memory aid

**State = one current state object, with behaviour organised by state.**

It helps with more states, consistent data, clearer behaviour, and explicit handling of new operations. It does not guarantee shorter code or uniquely enable external events.

## Sources and related notes

PDF viewer pages, counting the cover as page 1:

- **U p. 69:** intent and internal/external events.
- **U p. 71:** abstract UserState operations.
- **U pp. 72–75:** SessionState and NoSessionState behaviours and transitions.
- **U pp. 76–77:** UserSession delegates to its current state.
- The selected answers are confirmed by the user-supplied quiz feedback.
- [UML and design-pattern notes](../notes/week-02-uml-and-design-patterns.md)
- [Back to quiz index](README.md)
