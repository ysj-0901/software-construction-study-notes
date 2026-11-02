# Quiz 007: Factory UML, dependencies and notification selection

**Original label:** Question 4; quiz week not supplied.  
**Topic:** UML class diagrams and the course's Factory Method example.  
[Back to quiz index](README.md)

## Question

Based on the supplied UML diagram, which statements are correct? Select all that apply.

The numbered elements are:

| Item | Element |
| --- | --- |
| 1 | `Notification` interface, declaring `notifyUser()` |
| 2 | `NotificationService`, containing `main()` |
| 3 | `SMSNotification` |
| 4 | `EmailNotification` |
| 5 | `PushNotification` |
| 6 | `NotificationFactory`, containing `createNotification(): Notification` |

Items 3–5 implement Item 1. Item 2 uses Item 6. The original diagram labels the factory's connection to the product group “creates”.

1. Item 6 represents the factory method.
2. Item 6 has a dependency with Item 1.
3. Item 1 is an interface.
4. Item 2 represents a client of the system.
5. The use case diagram represents the Factory Method design pattern.
6. Items 2, 3 and 4 represent different notification methods.
7. Items 2, 3, 4, 5 and 6 are concrete classes.
8. Items 3, 4 and 5 implement a notification method following the interface specification (Item 1).

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct options according to the quiz feedback: 1, 2, 3, 4, 7 and 8.**

| Option | Verdict | Explanation |
| --- | --- | --- |
| 1 | Correct in the quiz | Item 6 is the factory **class** containing the factory **method**, `createNotification()`. The quiz uses “factory method” loosely for the class. |
| 2 | Correct | The factory uses `Notification` as its return type, so Item 6 depends on Item 1. |
| 3 | Correct | Item 1 is explicitly marked as an interface. |
| 4 | Correct | `NotificationService` is the client that requests and uses a notification. |
| 5 | Incorrect | This is a **class diagram**, not a use case diagram. |
| 6 | Incorrect | The notification implementations are Items **3, 4 and 5**. Item 2 is the client. These boxes represent classes, not individual methods. |
| 7 | Correct | Items 2–6 are shown as ordinary concrete classes, without abstract markings. |
| 8 | Correct | Items 3–5 implement `notifyUser()`, as required by the interface. |

**Review mistake:** the selected answers correctly identified the dependency, interface, client and concrete implementations, but omitted option 1.

### Dependency: who needs whom?

A dependency arrow points **from the client to the supplier**. Read it as **“needs/uses”**.

- `NotificationFactory → Notification`: the factory uses the interface as its return type.
- `NotificationFactory → EmailNotification`: the factory needs the concrete class to construct an email notification.
- The same creation dependency applies to the other concrete product classes.

**Supplier does not mean manufacturer.** It means the element depended upon. Creating an object is one specific reason to depend on its class.

In standard UML notation, a dependency uses a dashed line with an open arrowhead; a creation dependency may be labelled `«create»`. The original slide's solid “creates” arrow to an enclosing group is a simplified illustration.

The relationship from each concrete notification to `Notification` is **realisation**: a dashed line with a hollow triangle pointing to the interface.

### Class diagram versus use case diagram

| Diagram | Main question | Notification example |
| --- | --- | --- |
| Class diagram | How is the software structured? | `EmailNotification` implements `Notification`. |
| Use case diagram | What goals can actors accomplish using the system? | A user changes notification preferences; an administrator sends a notification. |

A use case diagram typically shows actors, oval use cases and a system boundary. A client **class**, such as `NotificationService`, is not automatically a human actor.

### Worked example: the user selects Email

This is an illustrative simplification, not a verbatim copy of the lecture code. It adds a single choice parameter to make the selection explicit and prints messages rather than sending real notifications.

Save the complete example as `NotificationService.java`:

```java
interface Notification {
    void notifyUser();
}

class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an email notification!");
    }
}

class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS notification!");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a push notification!");
    }
}

class NotificationFactory {
    public Notification createNotification(String choice) {
        switch (choice) {
            case "Email": return new EmailNotification();
            case "SMS":   return new SMSNotification();
            case "Push":  return new PushNotification();
            default:
                throw new IllegalArgumentException("Unknown choice");
        }
    }
}

public class NotificationService {
    public static void main(String[] args) {
        String choice = "Email"; // Imagine this comes from the UI.
        NotificationFactory factory = new NotificationFactory();
        Notification notification = factory.createNotification(choice);
        notification.notifyUser();
    }
}
```

Execution:

1. The client passes `"Email"` to the factory.
2. The factory constructs and returns an `EmailNotification`.
3. The client stores it in a variable declared as `Notification`.
4. Calling `notification.notifyUser()` runs `EmailNotification.notifyUser()`.

Output:

```text
Sending an email notification!
```

**Declared type:** `Notification` determines which operations the client can access.  
**Actual object type:** `EmailNotification` determines which overridden instance method runs.

Selecting SMS or Push changes the concrete object; the client still calls the same `notification.notifyUser()`.

### Terminology note

Use the course's **Factory Method** label for this quiz. The conditional factory illustrated here is commonly called a **Simple Factory**. The canonical GoF Factory Method formulation lets creator subclasses decide which product to instantiate; the lecture states that formulation before presenting this notification example.

### Memory aid

**Client requests → factory creates → product interface is returned → concrete product supplies behaviour.**

**Dependency arrow: “I need/use that” → that.**

</details>

## Lecture references

All page numbers are **PDF viewer pages, with the cover as page 1**.

Source: `week02-uml-dp(6).pdf` (**U**).

- **PDF pp. 15–21:** UML relationship notation.
- **PDF pp. 30–34:** reference types, runtime types and method dispatch.
- **PDF p. 53:** canonical Factory Method intent.
- **PDF p. 54:** notification UML example.
- **PDF p. 55:** interface and concrete notification implementations.
- **PDF p. 56:** factory selects and constructs a notification.
- **PDF p. 57:** client requests a notification and calls `notifyUser()`.

The quiz's answer key comes from the supplied feedback screenshots. The email walkthrough and diagram-type comparison are explanatory additions.

[Lecture source index](../notes/lecture-source-index.md) · [Exam revision guide](../notes/final-exam-revision.md)
