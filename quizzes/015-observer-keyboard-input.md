# Quiz 015: Observer, keyboard input and subscriptions

**Original label:** Question 4; quiz week not supplied.  
**Topic:** Event-driven input handling with the Observer pattern.  
[Back to quiz index](README.md)

## Question

A UI framework allows visual components, which all extend a class called Component, to receive user input whenever the user presses a key. This can be implemented by having the Components frequently check a central InputManager class for input. Alternatively, the InputManager class could call a function in all Components when a key is pressed. However, both these methods are slow. Which design pattern could be used to implement a better solution?

- DAO
- Observer
- Facade
- Iterator

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: Observer.** The supplied screenshot confirms full credit.

### The missing idea: subscribe to the event

A component that wants keyboard input **registers with InputManager**. When a key is pressed, InputManager calls the registered observers. A component can unregister when it no longer needs these events.

This matches the lecture's **attach → notify → detach** mechanism.

### Compare the three approaches

Imagine 100 visual components, but only 3 currently need keyboard events. These numbers are an illustrative assumption, not supplied in the question.

| Approach | What happens? | Unnecessary work |
| --- | --- | --- |
| Polling | Components repeatedly ask InputManager whether a key has been pressed. | Checks continue even when no key is pressed. |
| Notify every component | Each key press triggers a callback on all 100 components. | 97 uninterested components also receive calls. |
| Observer with selective registration | The 3 interested components subscribe; a key press triggers their callbacks. | Avoids repeated component polling and calls to unsubscribed components. |

Observer does **not** mean “never loop”. InputManager can still loop over the **subscriber list**, rather than the complete component list.

The underlying system must still detect keyboard input. The change concerns how UI components receive it.

### Who plays each role?

| Scenario element | Observer role |
| --- | --- |
| InputManager | Subject / publisher |
| Interested UI components | Observers / subscribers |
| KeyObserver | Common callback interface |
| attach / detach | Start / stop receiving notifications |
| onKey(key) | Notification callback |

Extending Component gives classes a shared UI base. It does not mean every component must always subscribe. An interested class can extend Component **and** implement KeyObserver.

### Small Java example

This is a simplified illustration; it is not a complete UI framework. The subscriber list is assumed not to change during notification.

```java
interface KeyObserver {
    void onKey(char key);
}

class InputManager {
    private final java.util.List<KeyObserver> observers =
        new java.util.ArrayList<>();

    void attach(KeyObserver observer) {
        observers.add(observer);
    }

    void detach(KeyObserver observer) {
        observers.remove(observer);
    }

    // Called by the input system when a key is pressed.
    void keyPressed(char key) {
        for (KeyObserver observer : observers) {
            observer.onKey(key);
        }
    }
}
```

For example, with editor and shortcutPanel implementing KeyObserver:

```java
input.attach(editor);
input.attach(shortcutPanel);

input.keyPressed('A');  // Notifies those two subscribers.

input.detach(editor);  // Editor stops receiving notifications.
```

InputManager relies on the KeyObserver interface, so it does not need a separate hard-coded call for every concrete component class.

### Why the other answers do not fit

| Pattern | What it solves | Why it is not the answer |
| --- | --- | --- |
| DAO | Separating business logic from data storage | The requirement is input notification, not persistence. |
| Facade | Providing a simpler interface to a subsystem | A simpler entry point does not establish subscriptions and event delivery. |
| Iterator | Traversing a collection without exposing its representation | It may help traverse observers, but does not itself define registration or event notification. |

### Important nuance: the question is loosely worded

The second proposed method already resembles an Observer notification loop. The intended improvement is **explicit registration of interested components**, rather than indiscriminately notifying every visual component.

**Observer does not automatically guarantee better performance.** If every component subscribes to every key event, the callback count remains the same as broadcasting to all components. Selective registration makes the illustrated reduction possible; interface-based notification also reduces coupling.

For N components and k interested subscribers, notifying everyone takes N callback invocations; selective notification takes k. If k = N, there is no callback-count reduction.

### Memory aid

**Interested objects subscribe → an event occurs → the subject notifies current subscribers.**

**Polling: “Anything happened yet?” repeatedly.**  
**Observer: “Let me know when it happens.”**

</details>

## Lecture references

Source supplied for this review: `week02-uml-dp.pdf`. Page numbers are **PDF viewer pages, with the cover as page 1**.

- **PDF p. 63:** one-to-many notification and loose coupling.
- **PDF p. 65:** Subject attach/detach/notify methods and the observer collection.
- **PDF pp. 66–67:** Observer interface, concrete customers and selective registration.
- **PDF p. 68:** classroom activity; only current subscribers respond to the subject.

These numbers match the existing U source, `week02-uml-dp(6).pdf`. The keyboard code, 100-versus-3 comparison and performance qualification are explanatory additions.

Related: [Choosing between Factory, State, Template and Observer](013-factory-platform-save-dialog.md) · [Iterator versus array conversion](008-iterator-vs-array-conversion.md) · [Design-pattern notes](../notes/week-02-uml-and-design-patterns.md).
