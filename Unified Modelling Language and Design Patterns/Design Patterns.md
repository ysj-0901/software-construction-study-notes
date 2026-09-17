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

**Key idea:**  
> Choose what object to create.

---

### 2. Singleton
**Purpose:** Ensure that only one instance of a class exists.

- Constructor is usually private.
- Provides a shared/global access point.

**Key idea:**  
> Only one object.

---

## II. Behavioural Patterns

### 1. Observer
**Purpose:** Automatically notify dependent objects when another object changes.

Example:
- YouTube channel = Subject
- Subscribers = Observers

**Key idea:**  
> Subscribe and notify.

---

### 2. State
**Purpose:** Change an object's behaviour depending on its current state.

Example:

```text
Guest → Member → Admin
```

The same method may behave differently depending on the current state.

**Key idea:**  
> Behaviour changes with state.

---

### 3. Template Method
**Purpose:** Define the general structure of an algorithm while allowing subclasses to customise some steps.

Example:

```text
prepare()
    step1()
    step2()
    step3()
```

The overall process stays fixed, but some steps can be overridden.

**Key idea:**  
> Fixed recipe, custom steps.

---

### 4. Iterator
**Purpose:** Traverse elements in a collection without exposing its internal structure.

Common methods:

```java
hasNext()
next()
```

The caller does not need to know whether the collection is an array, tree, list, etc.

**Key idea:**  
> Move through items.

---

## III. Structural Patterns

### 1. DAO — Data Access Object
**Purpose:** Separate data-access logic from the rest of the program.

Example:

```text
Application
    ↓
UserDAO
    ↓
Database / File
```

The application does not need to know how the data is actually stored.

**Key idea:**  
> Hide data access.

---

### 2. Façade
**Purpose:** Provide a simple interface to a complicated subsystem.

Instead of:

```text
Client
 ↓
A
 ↓
B
 ↓
C
 ↓
D
```

Use:

```text
Client
 ↓
Facade
 ↓
A + B + C + D
```

**Key idea:**  
> Simplify complexity.

---

### 3. Decorator
**Purpose:** Add behaviour to an individual object dynamically by wrapping it.

Example:

```text
OrangeJuice
    ↓
SugarDecorator
    ↓
CarrotDecorator
```

Avoids creating many subclasses such as:

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

| Pattern | Main Purpose |
|---|---|
| Factory Method | Create objects |
| Singleton | Restrict object creation to one instance |
| Observer | Notify dependent objects |
| State | Change behaviour based on state |
| Template Method | Define an algorithm structure |
| Iterator | Traverse a collection |
| DAO | Separate data access |
| Façade | Simplify a complex subsystem |
| Decorator | Dynamically add functionality |

## One-line Memory Trick

> **Factory creates, Singleton restricts, Observer notifies, State switches, Template defines, Iterator traverses, DAO accesses, Façade simplifies, Decorator extends.**
