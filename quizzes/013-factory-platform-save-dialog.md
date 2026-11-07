# Quiz 013: Creating a platform-specific save dialog

**Original label:** Question 2; quiz week not supplied.  
**Topic:** Choosing a design pattern from a scenario.  
[Back to quiz index](README.md)

## Question

Consider an text editor application. When the user closes the program without all open files saved, the application should instantiate and display a dialog box to ask if the user wishes to save their work. This dialog box will be a different class depending on the user's operating system. Which design pattern is most suited to this situation?

- State
- Template
- Factory
- Observer

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: Factory.** The supplied screenshot confirms the selected answer with full credit.

### Read the problem in three pieces

| Part of the question | What it tells us |
| --- | --- |
| The user closes the editor with unsaved files. | **When** a dialog is needed: the trigger. |
| Instantiate a dialog box. | The design problem involves **object creation**. |
| A different dialog class depending on the operating system. | The **concrete class to instantiate varies**. |

The decisive combination is **instantiate + different concrete classes**. The client should request a dialog through a common interface while the factory chooses the platform-specific implementation.

**Windows → WindowsDialog; macOS → MacDialog; both are used as Dialog.**

The presence of a condition such as “unsaved files” does not by itself make the answer State. Identify the responsibility that the question asks us to design.

### Compare all four options using the same text editor

| Option | Recognition question | When it would fit this editor |
| --- | --- | --- |
| **Factory** | **Which concrete class should be created?** | Create a Windows or macOS save dialog according to the OS. **This question.** |
| **State** | How should this object's behaviour change in its current mode? | The same editor delegates editing actions to an EditingState or ReadOnlyState and changes state when its mode changes. |
| **Template** (Template Method) | Which steps vary inside a fixed algorithm? | A superclass fixes a save procedure, while subclasses implement a format-specific writing step. |
| **Observer** | Which subscribed objects should be notified about a change? | A document change notifies a subscribed preview panel and word counter. |

These patterns can coexist in a real editor. The best answer here follows the explicitly stated creation requirement.

### Small Java example

This is an illustrative **conditional factory**, following the style of the lecture's NotificationFactory example. Surrounding classes and imports are omitted.

Assume WindowsDialog and MacDialog both implement Dialog:

```java
interface Dialog {
    void show();
}

class DialogFactory {
    static Dialog create(String os) {
        if ("Windows".equals(os)) {
            return new WindowsDialog();
        }
        if ("macOS".equals(os)) {
            return new MacDialog();
        }
        throw new IllegalArgumentException("Unsupported OS");
    }
}
```

Client code:

```java
if (hasUnsavedFiles) {
    Dialog dialog = DialogFactory.create(os);
    dialog.show();
}
```

- The **if condition** decides whether a dialog is needed.
- The **factory** decides which concrete dialog to construct.
- The client uses the **Dialog interface** and calls the same show() operation.

### Link to the lecture example

The lecture chooses between SMSNotification, EmailNotification and PushNotification according to a notification channel. Replace “channel” with “operating system” and “notification” with “dialog”; the creation responsibility is the same.

**Terminology:** the course labels this section **Factory Method**. Its conditional NotificationFactory implementation is commonly called a **Simple Factory**; the canonical GoF Factory Method lets creator subclasses decide which product to instantiate. The quiz option is simply **Factory**, so this terminology distinction does not change the answer.

### Memory aid

**Factory creates · State switches behaviour · Template fixes steps · Observer notifies.**

Do not classify a scenario by one incidental word (“state”, “change”, “close”). Ask **what responsibility the design pattern must handle**.

</details>

## Lecture references

Source supplied for this review: `week02-uml-dp.pdf`. All numbers are **PDF viewer pages, with the cover as page 1**.

- **PDF pp. 52–58:** creational patterns, Factory Method intent, notification factory and client.
- **PDF pp. 63–68:** Observer and one-to-many notification.
- **PDF pp. 69–77:** State, delegation and state-dependent behaviour.
- **PDF pp. 78–86:** Template Method and a fixed algorithm with specialised steps.

These page numbers also match the repository's existing **U** source, `week02-uml-dp(6).pdf`. The editor comparison and Java dialog example are explanatory additions, not verbatim lecture examples.

Related: [Factory UML and notifications](007-factory-uml-notifications.md) · [State versus a boolean flag](006-state-pattern-vs-boolean.md) · [UML and design-pattern notes](../notes/week-02-uml-and-design-patterns.md).
