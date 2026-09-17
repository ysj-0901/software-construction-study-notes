# Design Patterns Summary

Design patterns are reusable solutions to common software design problems.

A simple way to classify them:

- **Creational** → how objects are created
- **Behavioural** → how objects behave and interact
- **Structural** → how classes and objects are organised

---

## I. Creational Patterns

### 1. Factory Method
**Purpose:** Create objects without tightly coupling the code to a specific class.

- Moves object creation into a separate method/class.
- Useful when the exact object type may vary.

**Concrete example:** A notification system creates either an `EmailNotification` or `SMSNotification` depending on the requested type.

```java
Notification notification = NotificationFactory.create("email");
notification.send("Hello");
```

The client asks for a `Notification` without needing to construct a specific implementation directly.

**Key idea:**  
> Choose what object to create.

---

### 2. Singleton
**Purpose:** Ensure that only one instance of a class exists.

- Constructor is usually private.
- Provides a shared/global access point.

**Concrete example:** An application uses one shared configuration manager.

```java
ConfigManager config = ConfigManager.getInstance();
```

Every part of the program receives the same `ConfigManager` instance rather than creating separate copies.

**Key idea:**  
> Only one object.

---

## II. Behavioural Patterns

### 1. Observer
**Purpose:** Automatically notify dependent objects when another object changes.

**Concrete example:** A YouTube channel is the **subject**, and its subscribers are **observers**. When a new video is uploaded, all subscribers are notified automatically.

```java
channel.subscribe(alice);
channel.subscribe(bob);
channel.uploadVideo("Design Patterns");
```

Here, `alice` and `bob` do not repeatedly check the channel; the channel notifies them when its state changes.

**Key idea:**  
> Subscribe and notify.

---

### 2. State
**Purpose:** Change an object's behaviour depending on its current state.

**Concrete example:** A user can be a `Guest`, `Member`, or `Admin`. The same operation behaves differently depending on the current state.

```java
user.setState(new GuestState());
user.editPost();      // denied

user.setState(new AdminState());
user.editPost();      // allowed
```

Instead of putting many `if/else` checks inside `User`, the behaviour is delegated to separate state objects.

**Key idea:**  
> Behaviour changes with state.

---

### 3. Template Method
**Purpose:** Define the general structure of an algorithm while allowing subclasses to customise some steps.

**Concrete example:** Both CSV and JSON importers follow the same overall import process, but parse the data differently.

```java
abstract class DataImporter {
    public final void importData() {
        readFile();
        parseData();
        saveData();
    }

    abstract void parseData();
}
```

`CSVImporter` and `JSONImporter` can override `parseData()`, while the overall algorithm remains fixed.

**Key idea:**  
> Fixed recipe, custom steps.

---

### 4. Iterator
**Purpose:** Traverse elements in a collection without exposing its internal structure.

**Concrete example:** A playlist can provide an iterator so the client can visit each song without knowing how the playlist stores them internally.

```java
Iterator<Song> iterator = playlist.iterator();

while (iterator.hasNext()) {
    Song song = iterator.next();
    System.out.println(song.getTitle());
}
```

The caller only needs `hasNext()` and `next()`; it does not need to know whether the songs are stored in an array, list, tree, etc.

**Key idea:**  
> Move through items.

---

## III. Structural Patterns

### 1. DAO — Data Access Object
**Purpose:** Separate data-access logic from the rest of the program.

**Concrete example:** `UserDAO` handles loading and saving users, so the rest of the application does not need to know whether the data comes from a database or file.

```java
UserDAO userDAO = new UserDAO();
User user = userDAO.findById(101);
userDAO.save(user);
```

Conceptually:

```text
Application
    ↓
UserDAO
    ↓
Database / File
```

**Key idea:**  
> Hide data access.

---

### 2. Façade
**Purpose:** Provide a simple interface to a complicated subsystem.

**Concrete example:** An online store's checkout process may involve payment, inventory, shipping, and email services. A `CheckoutFacade` hides those details behind one simple call.

```java
CheckoutFacade checkout = new CheckoutFacade();
checkout.placeOrder(order);
```

Internally, the façade may coordinate:

```text
CheckoutFacade
   ├── PaymentService
   ├── InventoryService
   ├── ShippingService
   └── EmailService
```

The client only interacts with the façade instead of coordinating all four services itself.

**Key idea:**  
> Simplify complexity.

---

### 3. Decorator
**Purpose:** Add behaviour to an individual object dynamically by wrapping it.

**Concrete example:** Start with an orange juice, then dynamically add sugar and carrot without creating a subclass for every possible combination.

```text
CarrotDecorator
    ↓ wraps
SugarDecorator
    ↓ wraps
OrangeJuice
```

This avoids classes such as:

```text
OrangeJuiceWithSugar
OrangeJuiceWithCarrot
OrangeJuiceWithSugarAndCarrot
```

**Key idea:**  
> Add features by wrapping.

### Java Example

```java
interface Juice {
    String getDescription();
    double getPrice();
}

class OrangeJuice implements Juice {
    public String getDescription() {
        return "Orange Juice";
    }

    public double getPrice() {
        return 5.0;
    }
}

abstract class JuiceDecorator implements Juice {
    protected Juice juice;

    public JuiceDecorator(Juice juice) {
        this.juice = juice;
    }
}

class SugarDecorator extends JuiceDecorator {
    public SugarDecorator(Juice juice) {
        super(juice);
    }

    public String getDescription() {
        return juice.getDescription() + " + Sugar";
    }

    public double getPrice() {
        return juice.getPrice() + 0.5;
    }
}

class CarrotDecorator extends JuiceDecorator {
    public CarrotDecorator(Juice juice) {
        super(juice);
    }

    public String getDescription() {
        return juice.getDescription() + " + Carrot";
    }

    public double getPrice() {
        return juice.getPrice() + 1.0;
    }
}
```

Usage:

```java
Juice juice = new OrangeJuice();
juice = new SugarDecorator(juice);
juice = new CarrotDecorator(juice);

System.out.println(juice.getDescription());
// Orange Juice + Sugar + Carrot
```

---

# Quick Comparison

| Pattern | Main Purpose | Concrete Example |
|---|---|---|
| Factory Method | Create objects | Create an email or SMS notification |
| Singleton | Restrict object creation to one instance | Shared configuration manager |
| Observer | Notify dependent objects | YouTube channel notifying subscribers |
| State | Change behaviour based on state | Guest / Member / Admin behaviour |
| Template Method | Define an algorithm structure | CSV and JSON importers |
| Iterator | Traverse a collection | Moving through songs in a playlist |
| DAO | Separate data access | `UserDAO` reading/writing user data |
| Façade | Simplify a complex subsystem | One checkout call coordinating several services |
| Decorator | Dynamically add functionality | Adding sugar and carrot to juice |

## One-line Memory Trick

> **Factory creates, Singleton restricts, Observer notifies, State switches, Template defines, Iterator traverses, DAO accesses, Façade simplifies, Decorator extends.**
