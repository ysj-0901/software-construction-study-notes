# Week 2 — UML and Design Patterns

## Unified Modelling Language (UML)

UML is a standard way of modelling and visualising software systems.

Its main building blocks are:

- **Things** — the elements being modelled
- **Relationships** — how those elements are connected
- **Diagrams** — graphical representations of things and their relationships

## Class Diagrams

A class diagram represents classes, interfaces, and the relationships between them.

A typical UML class contains three sections:

```text
┌─────────────────────────┐
│       ClassName         │
├─────────────────────────┤
│ attributes              │
├─────────────────────────┤
│ operations / methods    │
└─────────────────────────┘

### Visibility

| Symbol | Visibility |
| --- | --- |
| `+` | public |
| `-` | private |
| `#` | protected |
| `~` | package |

If no access modifier is specified in Java, the default visibility is **package**.

### Attributes

General format:

```text
[visibility] attributeName: Type
```

Examples:

```text
+ year: Integer
- make: String
- passengers: Person[0..N]
+ mileage: Double = 0.0
```

A Java `final` field may be represented as:

```text
{readOnly}
```

### Operations

General format:

```text
[visibility] methodName(parameters): ReturnType
```

Examples:

```text
+ start(): Boolean
+ stop(): void
```

Static attributes and methods are usually **underlined** in UML.

---

# UML Relationships

UML relationships describe how classes or objects are connected.

## 1. Generalisation / Inheritance

**Meaning:** one class is a specialised version of another.

**Relationship:** `is-a`

The subclass inherits the superclass's:

- attributes
- methods / operations
- relationships

Example:

```text
Car is a Vehicle
```

UML notation:

```text
solid line + hollow triangle pointing to the superclass
```

Conceptually:

```text
      Vehicle
         △
         │
        Car
```

---

## 2. Association

**Meaning:** a structural relationship between classes or objects.

The objects are connected for an ongoing purpose.

Example:

```text
Employee works for Organization
```

Think:

> **"has a connection with"**

Association is usually represented with a **solid line**.

It can also include multiplicity.

```text
Employee ───────── Organization
          works for
```

---

## 3. Dependency

**Meaning:** one class temporarily uses or depends on another.

Dependency commonly occurs through:

- method parameters
- local variables
- return values
- object creation

Relationship:

```text
uses-a
depends-on
```

Example:

```text
Printer uses Report
```

Dependency is generally weaker than association.

UML notation:

```text
dashed arrow
```

Example in Java:

```java
public class CarFactory {

    public void produceCar(String model) {
        Car car = new Car(model);
        car.assemble();
    }
}
```

`CarFactory` depends on `Car` because `Car` is created and used locally.

---

## 4. Aggregation

**Meaning:** a weak whole-part relationship.

Relationship:

```text
has-a
is-part-of
```

The important feature is:

> The part can exist independently of the whole.

Example:

```text
Car has an Engine
```

UML notation:

```text
hollow diamond on the whole side
```

```text
Car ◇──────── Engine
```

Example:

```java
class Engine {
    private String type;
}

class Car {
    private String model;
    private Engine engine;
}
```

The `Engine` can exist without the `Car`.

---

## 5. Composition

**Meaning:** a strong whole-part relationship.

The whole owns the part, and the part's lifecycle depends on the whole.

> Parts live and die with the whole.

Example:

```text
Order contains Items
```

UML notation:

```text
filled diamond on the whole side
```

```text
Order ◆──────── Item
```

Example:

```java
public class Order {
    private List<Item> items = new ArrayList<>();

    public void addItem(String name, double price) {
        Item item = new Item(name, price);
        items.add(item);
    }
}
```

Here, the `Order` creates and owns its `Item` objects.

---

# Multiplicity

Multiplicity describes how many instances participate in a relationship.

| Multiplicity | Meaning |
| --- | --- |
| `0..1` | zero or one |
| `0..*` or `*` | zero or more |
| `1` | exactly one |
| `1..*` | one or more |
| `n..m` | between `n` and `m` |

Examples:

```text
Employee 0..* ───── 1 Organization
```

Many employees may work for one organisation.

```text
Car 1 ◇──── 1 Engine
```

One car has one engine.

```text
Order 1 ◆──── * Item
```

One order contains many items.

---

# Quick Comparison

| Relationship | Key Idea | Example |
| --- | --- | --- |
| **Inheritance** | `is-a` | Car → Vehicle |
| **Association** | connected-to | Employee → Organization |
| **Dependency** | temporarily uses | Printer → Report |
| **Aggregation** | has-a, independent part | Car → Engine |
| **Composition** | owns part + lifecycle | Order → Item |

## Key Takeaway

A useful way to distinguish the relationships is:

```text
Inheritance  → "is-a"
Association  → "is connected to"
Dependency   → "temporarily uses"
Aggregation  → "has-a, but the part can exist independently"
Composition  → "owns-a, and the part depends on the whole"
```
