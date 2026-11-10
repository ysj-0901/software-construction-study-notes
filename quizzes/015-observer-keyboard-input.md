# Quiz 015: Observer, keyboard input and subscriptions

**Original label:** Question 4; quiz week not supplied.  
**Topic:** Event-driven input handling with the Observer pattern.  
**Reviewed again:** 10 November 2026; the same Question 4 was supplied with Observer marked correct. This entry is reused rather than counted as a new question.  
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

The basic mechanism is **attach → notify → detach**.

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

### When is Observer useful?

**Subscription is the mechanism, not the only application scenario.** Use Observer when a change or event in one object should automatically notify other interested objects.

It is especially useful when the set of interested objects can change, and the subject should not depend on their concrete classes.

| Scenario | Change or event | Observers that receive notifications |
| --- | --- | --- |
| GUI event handling | A button is clicked, a key is pressed or the mouse moves | Registered event listeners |
| Data and UI synchronisation | A data model changes | Tables, charts or other views displaying that data |
| Task progress | A download progresses from 20% to 30% | A progress bar and percentage label |
| Game events | A player's health decreases | A health bar and low-health warning component |
| Messaging | A new message arrives | A chat window and unread-message counter |

### Example: a points-and-badges system

One possible design for a points-and-badges system is:

**Points change → notify the points display to refresh → notify the badge module to check achievement thresholds.**

Here, the points model is the **subject**. The display and badge module are **observers** that register for points-change events. The subject sends notifications through a common interface; each observer decides how to respond. The arrow sequence illustrates the flow, not a requirement that observers run in this particular order.

For example:

- The points display refreshes the number shown.
- The badge module checks whether the new total unlocks an achievement.

### Exam recognition clues

Look for **event, listener, notify, update, one-to-many**, or **changes automatically reflected in other objects**.

These are clues, not automatic rules: identify whether the design problem involves notifying interested objects about a change.

### Observer versus State

| Pattern | Main idea | Example |
| --- | --- | --- |
| **State** | An object's own behaviour changes with its current state. | An editor delegates editing actions differently in editing mode and read-only mode. |
| **Observer** | An object changes or an event occurs, and other registered objects are notified. | A document change notifies a preview panel and word counter. |

**State: change how I behave. Observer: notify others when something happens.**

### Memory aid

**Interested objects subscribe → an event occurs → the subject notifies current subscribers.**

**Polling: “Anything happened yet?” repeatedly.**  
**Observer: “Let me know when it happens.”**

</details>


Related: [Choosing between Factory, State, Template and Observer](013-factory-platform-save-dialog.md) · [Iterator versus array conversion](008-iterator-vs-array-conversion.md) · [Design-pattern notes](../notes/week-02-uml-and-design-patterns.md).
