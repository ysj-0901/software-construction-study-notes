> **Image availability:** The original Obsidian notes reference diagrams whose image files were not supplied. Their positions are marked below.

# Unified Modelling Language - Building Blocks
Things (abstraction/elements that will be modelled)
Relationships (relate things/tie them together)
Diagrams (graphical representation of a set of elements (things and relationships))

## Diagrams
### Class Diagram
#### object-oriented approach 
A class diagram shows a set of classes, interfaces, and collaborations and their relationships.

e.g. 


> *Diagram not included in this upload: `Pasted image 20260817002843.png`.*


> *Diagram not included in this upload: `Pasted image 20260817002903.png`.*


> *Diagram not included in this upload: `Pasted image 20260817003039.png`.*


## UML Relationships

UML relationships describe **how classes or objects are connected**.

### 1. Generalisation / Inheritance
- **Meaning:** one class is a specialised version of another.
- **Relationship:** `is-a`
- Subclass inherits the superclass's:
  - attributes
  - methods/operations
  - relationships
- Example: `Car is a Vehicle`
- UML: solid line + hollow triangle pointing to superclass


> *Diagram not included in this upload: `Pasted image 20260817004048.png`.*


> *Diagram not included in this upload: `Pasted image 20260817004340.png`.*


### 2. Association
- **Meaning:** a structural relationship between objects/classes.
- Objects are connected for an ongoing purpose.
- Often includes **multiplicity** (`1`, `0..*`, `1..*`, etc.).
- Example: `Employee works for Organization`
- Think: **"has a connection with"**


> *Diagram not included in this upload: `Pasted image 20260817005358.png`.*


### 3. Dependency
- **Meaning:** one class temporarily **uses/depends on** another.
- Usually through:
  - method parameters
  - local variables
  - return values / object creation
- Relationship: `uses-a / depends-on`
- Example: `Printer uses Report`
- Generally weaker than association.

A temporary, weak relationship where one class uses another to perform a function, usually as a method parameter, local variable, or method return.
```java
public class CarFactory {

    // Dependency: Car is instantiated and used as a local variable
    public void produceCar(String model) {
        Car car = new Car(model);
        car.assemble();
    }
}
```


> *Diagram not included in this upload: `Pasted image 20260817005830.png`.*


### 4. Aggregation
- **Meaning:** weak whole–part relationship.
- Relationship: `has-a / is-part-of`
- The part **can exist independently** of the whole.
- Example: `Car has an Engine`
- UML: hollow diamond on the **whole** side.

e.g. 
```java
class Engine { 
	private String type; // e.g. "V8" 
} 

class Car { 
	private String model; // e.g. "Mustang" 
    private Engine engine; // aggregated Engine 
}
```


> *Diagram not included in this upload: `Pasted image 20260817010209.png`.*


### 5. Composition
- **Meaning:** strong whole–part relationship.
- The part's lifecycle depends on the whole:
  **parts live and die with the whole**.
- Example: `Order contains Items`
- UML: filled diamond on the **whole** side.


> *Diagram not included in this upload: `Pasted image 20260817010120.png`.*


**Composition** is a strong whole–part relationship where the whole owns the parts, and the parts' lifecycle depends on the whole.

```java
public class Order {
    private List<Item> items = new ArrayList<>();

    public void addItem(String name, double price) {
        Item item = new Item(name, price); // Order creates and owns the Item
        items.add(item);
    }
}
```

### Summary


> *Diagram not included in this upload: `Pasted image 20260817011449.png`.*


### Multiplicity


> *Diagram not included in this upload: `Pasted image 20260817011113.png`.*


### Quick Comparison

| Relationship | Key idea                    | Example                 |
| ------------ | --------------------------- | ----------------------- |
| Inheritance  | **is-a**                    | Car → Vehicle           |
| Association  | **connected-to**            | Employee → Organization |
| Dependency   | **temporarily uses**        | Printer → Report        |
| Aggregation  | **has-a, independent part** | Car → Engine            |
| Composition  | **owns part + lifecycle**   | Order → Item            |

### Sequence Diagram
### Use Case Diagram
and many others!


# Design Patterns
**Design patterns are reusable, general solutions to common software design problems.** They are not ready-made code, but templates that guide how to structure a solution.

>main benefits:

- **Reusable solutions** — proven approaches can be applied to different problems and languages.
- **Faster development** — you can reuse established designs instead of inventing everything from scratch.
- **Knowledge transfer** — patterns capture experience from other developers and give teams a shared vocabulary.
- **Better readability** — when developers recognize a familiar pattern, the design is easier to understand.

#### Examples:

- **Singleton** → problem: “How do I ensure there is only one instance of a class?”
- **Observer** → problem: “How do I notify dependent objects when another object changes?”

> **Design pattern = common design problem + reusable generic solution.**

## Design Pattern Classification
- Creational
- Behavioural
- Structural


> *Diagram not included in this upload: `Pasted image 20260822013050.png`.*


## I. Creational Patterns
- Focus on **how objects are created**.
- Hide or abstract the object creation process from the rest of the system.
- Help make the system less dependent on specific classes or creation details.
- **Class-based creational patterns** use **inheritance** to decide what class gets instantiated.
- **Object-based creational patterns** delegate object creation to **another object**.
### 1. Factory Method

The **Factory Method** pattern defines an interface for creating objects while allowing the actual object type to be decided separately.

> **Main idea:** defer object creation instead of directly instantiating concrete classes throughout the program.

According to the GoF definition:

- Define an interface for creating an object, but let subclasses decide which class to instantiate.
- Factory Method allows a class to defer instantiation to subclasses.

##### When to Use Factory Method

Use the Factory Method pattern when:

- a class cannot anticipate which type of object it needs to create;
- a class wants subclasses to specify which objects should be created;
- several helper classes can perform a task, and you want to centralise the decision about which helper to use.

---

#### Example: Notification System

Suppose an application supports three notification types:

- SMS
- Email
- Push notification

Instead of letting the client create these objects directly:

```java
new SMSNotification();
new EmailNotification();
new PushNotification();
```

object creation is handled by a `NotificationFactory`.

Conceptually:

                    Notification

                    <<interface>>

                        ▲

            ┌────────────┼────────────┐

            │            │            │

   SMSNotification EmailNotification PushNotification

  

NotificationService

        │

        ▼

NotificationFactory

        │

        └── creates a Notification

The client therefore works mainly with the `Notification` interface rather than concrete notification classes.


#### 1) Product Interface

All notification types implement the same interface:
```java
public interface Notification {

    void notifyUser();

}

```
This gives the different notification objects a common type.

---

#### 2) Concrete Products

##### SMS Notification
```java

public class SMSNotification implements Notification {
    @Override
    public void notifyUser() {

        System.out.println("Sending an SMS Notification!");

    }

}
```


##### Push Notification

```java

public class PushNotification implements Notification {
    @Override
    public void notifyUser() {

        System.out.println("Sending a Push Notification!");

    }

}
```


##### Email Notification

```java

public class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an E-Mail Notification!");
    }
}
```
So all three classes can be treated as:
```java
Notification
```
even though their implementations are different.

---

##### Notification Factory

The factory contains the logic that decides which concrete notification object to create.
```java

public class NotificationFactory {
    public Notification createNotification(String msg, String c) {
        String channel = c;
        // If notification type is not defined,
        // randomly choose one.
        if (channel == null || channel.isEmpty()) {
            List<String> l =
                    Arrays.asList("SMS", "EMAIL", "PUSH");

            Random rand = new Random();
            channel = l.get(
                    rand.nextInt(l.size())
            );
        }
        if ("SMS".equalsIgnoreCase(channel)) {
            return new SMSNotification();
        }

        else if ("EMAIL".equalsIgnoreCase(channel)) {
            return new EmailNotification();
        }

        else if ("PUSH".equalsIgnoreCase(channel)) {
            return new PushNotification();

        }
        return null;
    }
}
```

The important part is:
```java
return new SMSNotification();

return new EmailNotification();

return new PushNotification();
```

These concrete creation decisions are **localised inside the factory**.
The rest of the program does not need to know exactly how each notification object is constructed.

##### Using the Factory

The client asks the factory to create a `Notification`, then uses the returned object through the common interface.

```java
NotificationFactory factory = new NotificationFactory();
Notification notification = factory.createNotification("Hi!", "EMAIL");
notification.notifyUser();
```

---


### 2. Singleton
intent: ensures a class only has exactly one instance, and provides a global point of access to it
```java

public class SingletonConnection {

    // Note that the constructor and instance variable are private
    private static SingletonConnection instance = null;

    private SingletonConnection() {}

    // Note that this is the only method that can be accessed
    public static SingletonConnection getInstance() {
        if (instance == null) {
            System.out.println("Instance created!!!!");
            instance = new SingletonConnection();
        } else {
            System.out.println("Instance has already been created!!!!");
        }

        return instance;
    }
}

```
The key Singleton idea here is that the constructor is `private`, so other classes cannot call `new SingletonConnection()` directly. They must use `getInstance()`.

```java
public class SingletonTest {

    public static void main(String args[]) {

        // We cannot instantiate it!
        // SingletonConnection db = new SingletonConnection();

        SingletonConnection.getInstance();
        SingletonConnection.getInstance();
        SingletonConnection.getInstance();
    }
}
```
## II. Behavioural Patterns
- Behavioural patterns organise how responsibilities and interactions are distributed between objects, helping avoid one giant class full of complicated control logic.
- **Behavioural class patterns** use inheritance to distribute behaviour between classes.
- **Behavioural object patterns** use composition, where several objects cooperate to perform a task.
### 1. Observer
Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

Use Observer when:
- one object's change should trigger updates in other objects;
- you don't know how many objects need to be notified;
- you want the subject and observers to remain **loosely coupled**.

#### Subject

```java
public interface Subject {

    // Register an observer
    void attach(Observer observer);

    // Remove an observer
    void detach(Observer observer);

    // Notify all registered observers
    void notifyAllObservers();
}
```

#### Concrete Subject

```java
import java.util.ArrayList;

public class Place implements Subject {

    // One Subject can have many Observers
    private ArrayList<Observer> observers;
    private String name;

    public Place(String name) {
        this.name = name;
        observers = new ArrayList<>();
    }

    // A state change causes all observers to be notified
    public void setCorona() {
        notifyAllObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer obs : observers) {
            obs.update(this.name + " has a confirmed case.");
        }
    }
}
```

#### Observer

```java
public interface Observer {

    // Called when the Subject notifies this observer
    void update(String msg);
}
```

#### Concrete Observer
Anyone that **implements `Observer` and therefore has `update()`** can subscribe.

```java
public class Customer implements Observer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String msg) {
        System.out.println(
            "Hey " + this.name + "! Message for you: " + msg
        );
    }
}
```

#### Demo

```java
public class ObserverDemo {

    public static void main(String[] args) {

        Customer c1 = new Customer("Dylan");
        Customer c2 = new Customer("John");
        Customer c3 = new Customer("Lisa");

        Place p1 = new Place("McDonalds");
        Place p2 = new Place("Zara");

        // Subscribe observers
        p1.attach(c1);
        p1.attach(c2);

        p2.attach(c2);
        p2.attach(c3);

        System.out.println("NEW CASE!");
        p1.setCorona();

        // Dylan unsubscribes from McDonalds
        p1.detach(c1);

        System.out.println("NEW CASE!");
        p1.setCorona();

        System.out.println("NEW CASE!");
        p2.setCorona();
    }
}
```

**Key Idea**

```text
Subject
   │
   ├── Observer 1
   ├── Observer 2
   └── Observer 3

Subject changes
      ↓
notifyAllObservers()
      ↓
each Observer.update()
```

The **Subject does not need to know the concrete type of its observers** — it only knows that they implement `Observer`. This keeps the objects loosely coupled.

In the Observer Pattern, the **observers depend on the subject for notifications**.
### 2. State
>The state pattern allows an object to change its behaviour based on internal or external events.

>Instead of writing lots of `if/else` or `switch` statements for different states, put each state’s behaviour into its own class.

For example, imagine a user in an app can be:

```
Guest
Member
Admin
```

Without the State pattern, you might write:

```
if (userType.equals("guest")) {
    ...
} else if (userType.equals("member")) {
    ...
} else if (userType.equals("admin")) {
    ...
}
```

and repeat this kind of logic everywhere.

With the State pattern, you instead create separate classes:

```
GuestState
MemberState
AdminState
```

that all implement or extend a common type, such as:

```
UserState
```

Conceptually:

```
            UserState
           /    |     \
          /     |      \
 GuestState MemberState AdminState
```

Then some main object, often called the **context**, stores the current state:

```
private UserState state;
```

and delegates behavior to it:

```
state.login(...);
state.logout(...);
state.addReply(...);
```

So the context itself doesn’t need to know all the rules.

##### State Design Pattern – Animal Example

###### Core Idea

The **State Design Pattern** allows an object to change its behaviour depending on its current state.

Instead of using lots of `if/else` statements, each state is represented by a separate class.

###### Example: Animal

Imagine an `Animal` can be in three states:

- `SleepingState`
    
- `HungryState`
    
- `PlayingState`
    
The same method:

```java
makeSound()
```

behaves differently depending on the animal's current state.

###### 1. State Interface

```java
interface AnimalState {
    void makeSound();
}
```

All concrete states must implement `makeSound()`.
###### 2. Concrete State Classes

```java
class SleepingState implements AnimalState {
    @Override
    public void makeSound() {
        System.out.println("Zzz...");
    }
}
```

```java
class HungryState implements AnimalState {
    @Override
    public void makeSound() {
        System.out.println("Feed me!");
    }
}
```

```java
class PlayingState implements AnimalState {
    @Override
    public void makeSound() {
        System.out.println("Woof! Let's play!");
    }
}
```

Each state provides its own behaviour for the same method.

###### 3. Context Class

The `Animal` is the **context**. It stores the current state.

```java
class Animal {
    private AnimalState state;

    public Animal(AnimalState state) {
        this.state = state;
    }

    public void setState(AnimalState state) {
        this.state = state;
    }

    public void makeSound() {
        state.makeSound();
    }
}
```

The important line is:

```java
state.makeSound();
```

`Animal` delegates the behaviour to its current state object.

###### 4. Using the States

```java
Animal dog = new Animal(new SleepingState());

dog.makeSound();
// Zzz...

dog.setState(new HungryState());

dog.makeSound();
// Feed me!

dog.setState(new PlayingState());

dog.makeSound();
// Woof! Let's play!
```

We always call:

```java
dog.makeSound();
```

but the behaviour changes because the current `state` object changes.

###### Structure

```text
             Animal
               |
               | has a
               v
          AnimalState
         /     |      \
        /      |       \
 Sleeping   Hungry    Playing
  State      State      State
```

- `Animal` → Context
    
- `AnimalState` → State interface
    
- `SleepingState`, `HungryState`, `PlayingState` → Concrete states
    

###### Without the State Pattern

```java
if (state.equals("sleeping")) {
    System.out.println("Zzz...");
} else if (state.equals("hungry")) {
    System.out.println("Feed me!");
} else if (state.equals("playing")) {
    System.out.println("Woof! Let's play!");
}
```

The State Pattern avoids repeatedly checking the state with `if/else` by moving each state's behaviour into its own class.

###### Key Takeaway

> **State Pattern:** Put different behaviours into separate state classes, then change an object's behaviour by changing its current state object.

```text
same object
+ same method call
+ different state
= different behaviour
```
### 3. Template Method
>Define the skeleton of an algorithm in an operation, deferring some steps to subclasses.

>Template Method lets subclasses redefine certain steps of an algorithm without changing the
algorithm's structure.


> *Diagram not included in this upload: `Pasted image 20260829234443.png`.*


example:
```java
abstract class AnimalCare {

    // Template method: defines the overall process
    public final void careForAnimal() {
        feed();
        clean();
        exercise();
    }

    // Common step
    void clean() {
        System.out.println("Clean the enclosure");
    }

    // Steps subclasses must define
    abstract void feed();
    abstract void exercise();
}

class DogCare extends AnimalCare {

    @Override
    void feed() {
        System.out.println("Feed dog food");
    }

    @Override
    void exercise() {
        System.out.println("Take the dog for a walk");
    }
}

class PenguinCare extends AnimalCare {

    @Override
    void feed() {
        System.out.println("Feed fish");
    }

    @Override
    void exercise() {
        System.out.println("Let the penguin swim");
    }
}

public class Main {
    public static void main(String[] args) {
        AnimalCare dog = new DogCare();
        dog.careForAnimal();

        System.out.println();

        AnimalCare penguin = new PenguinCare();
        penguin.careForAnimal();
    }
}
```
AnimalCare
    |
    |-- careForAnimal()   ← template method
          |
          |-- feed()      ← subclass decides
          |-- clean()     ← shared implementation
          |-- exercise()  ← subclass decides
          
### 4. Iterator
The **Iterator Pattern** allows a client to traverse elements in a collection **without exposing the collection's internal data structure**.

> Collection stores the data; Iterator controls how the data is traversed.

##### Core Interfaces

```java
public interface Iterator {
    public boolean hasNext();
    public Object next();
}

public interface IterableCollection {
    public Iterator createIterator();
}
```

- `hasNext()` → checks whether another element exists.
- `next()` → returns the next element.
- `createIterator()` → creates an iterator for the collection.

##### Concrete Collection + Iterator

```java
public class FriendsConcreteCollection implements IterableCollection {

    private String names[] = {
        "Friend_1",
        "Friend_2",
        "Friend_3"
    };

    public void addItem() {
        // ...
    }

    @Override
    public Iterator createIterator() {
        return new FriendsConcreteIterator();
    }

    // Inner iterator class
    private class FriendsConcreteIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (names != null && index < names.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return names[index++];
            }
            return null;
        }
    }
}
```

The collection keeps its internal array `names` private.

The iterator keeps track of the current traversal position using:

```java
int index;
```

This line:

```java
return names[index++];
```

means:

1. Return `names[index]`
2. Increment `index`
3. Move to the next element

So:

```text
index = 0 → Friend_1
index = 1 → Friend_2
index = 2 → Friend_3
```

##### Client Code

```java
public class IteratorDemo {

    public static void main(String[] args) {

        FriendsConcreteCollection f =
            new FriendsConcreteCollection();

        for (Iterator iter = f.createIterator();
             iter.hasNext();) {

            String name = (String) iter.next();
            System.out.println("Name : " + name);
        }
    }
}
```

Output:

```text
Name : Friend_1
Name : Friend_2
Name : Friend_3
```

##### How It Works

```text
Client
   |
   | createIterator()
   v
FriendsConcreteCollection
   |
   | creates
   v
FriendsConcreteIterator
   |
   | hasNext()
   | next()
   v
Friend_1 → Friend_2 → Friend_3
```

The client does **not** access:

```java
names[]
```

directly.

Instead, it only communicates with the iterator through:

```java
hasNext()
next()
```

##### Why Use Iterator?

Without Iterator:

```text
Client → directly accesses internal array/list/tree
```

This causes:
- High coupling
- Violation of information hiding
- Client code depending on the collection implementation
- More difficulty changing the internal data structure

With Iterator:

```text
Client → Iterator → Collection
```

The client does not need to know whether the collection uses an:

```text
Array
ArrayList
LinkedList
Tree
Graph
...
```

The internal representation can therefore change without requiring the client traversal code to change.

##### Key Takeaway

> **Iterator provides a standard way to access elements one by one without exposing the collection's internal representation.**

```text
Collection → stores data
Iterator   → traverses data
Client     → uses the iterator
```

## III. Structural Patterns
### 1. DAO (Data Access Object)
DAO handles reading and saving data so the rest of your app does not need to know the storage details. 

### 2. Façade
- **Client** → uses the **Facade**
- **Facade** → communicates with several subsystem classes
- The client does not need to understand or coordinate all those classes itself
- 
For example, starting a computer may require several operations:
```java
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;

    public void startComputer() {
        cpu.start();
        memory.load();
        hardDrive.read();
    }
}
```

The client only calls:
```java
computerFacade.startComputer();
```

A Facade provides one simple interface to a complicated collection of classes.
The subsystem still exists and does the real work; the facade merely makes it easier to use.
### 3. Decorator
The **Decorator Pattern** lets us **add or modify an individual object’s behaviour at runtime by wrapping it**, without changing the original class or interface.

Use it when you want to:

- add optional features dynamically;
- avoid creating many subclasses for every feature combination;
- follow the **Open/Closed Principle**;
- modify only specific objects, not every instance of a class.

**Key idea:**  
`original object → wrapped by decorator → gains extra functionality`

Example: `Juice → SugarDecorator → CarrotDecorator` instead of creating separate classes for every juice combination.

```java
// 1. Common interface
interface Juice {
    String getDescription();
    double getPrice();
}

// 2. Basic object
class OrangeJuice implements Juice {

    @Override
    public String getDescription() {
        return "Orange Juice";
    }

    @Override
    public double getPrice() {
        return 5.0;
    }
}

// 3. Base decorator
abstract class JuiceDecorator implements Juice {

    protected Juice juice;

    public JuiceDecorator(Juice juice) {
        this.juice = juice;
    }
}

// 4. Concrete decorator: add sugar
class SugarDecorator extends JuiceDecorator {

    public SugarDecorator(Juice juice) {
        super(juice);
    }

    @Override
    public String getDescription() {
        return juice.getDescription() + " + Sugar";
    }

    @Override
    public double getPrice() {
        return juice.getPrice() + 0.5;
    }
}

// 5. Concrete decorator: add carrot
class CarrotDecorator extends JuiceDecorator {

    public CarrotDecorator(Juice juice) {
        super(juice);
    }

    @Override
    public String getDescription() {
        return juice.getDescription() + " + Carrot";
    }

    @Override
    public double getPrice() {
        return juice.getPrice() + 1.0;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {

        Juice juice = new OrangeJuice();

        System.out.println(juice.getDescription());
        // Orange Juice

        juice = new SugarDecorator(juice);

        System.out.println(juice.getDescription());
        // Orange Juice + Sugar

        juice = new CarrotDecorator(juice);

        System.out.println(juice.getDescription());
        // Orange Juice + Sugar + Carrot

        System.out.println(juice.getPrice());
        // 6.5
    }
}
```


CarrotDecorator 
↓ wraps 
SugarDecorator
↓ wraps 
OrangeJuice
