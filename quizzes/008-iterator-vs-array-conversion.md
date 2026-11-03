# Quiz 008: Iterator versus array conversion

**Original label:** Question 3; quiz week not supplied.  
**Topic:** Iterator pattern, Java collections, references and modification during traversal.  
[Back to quiz index](README.md)

## Question

A developer is implementing a LinkedList and wants to iterate over the entire list. They could use the Iterator design pattern or provide a method that converts the list's contents to an array. What are the benefits of Iterator in this situation? Select all that apply.

**Correction to the original wording:** the question calls LinkedList “a type of resizable array data structure”. A linked list stores linked nodes; **ArrayList** is the resizable-array implementation.

Options, condensed from the supplied screenshots:

1. Early termination, such as using `break`, can give Iterator substantial performance benefits.
2. Iterator permits elements of different types, whereas an array can store only a single type.
3. Iterator supports jumping to a particular index, unlike a foreach loop over an array.
4. Iterator traverses the list without exposing its structure to the client, reducing coupling.
5. Array conversion must duplicate every element, approximately doubling memory usage when elements are large; Iterator avoids this.
6. If the list changes during the loop, Iterator can incorporate these changes much more easily.

<details>
<summary><strong>Answer and explanation</strong></summary>

**The supplied quiz feedback accepts options 1, 4 and 6.** Option 6 needs an implementation-dependent qualification.

**Review mistake:** options 1 and 4 were correctly selected; option 6 was omitted.

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | Correct in this comparison | A usual linked-list iterator can stop after visiting only the required elements. Converting to an array first traverses the whole list, even if the subsequent loop stops early. |
| 2 | Incorrect | Both arrays and generic collections can hold objects of different actual classes under a compatible declared element type, such as `Object`. |
| 3 | Incorrect | A basic iterator provides sequential traversal, not arbitrary index jumps. Arrays themselves support direct indexing. |
| 4 | Correct | Client code uses a traversal interface without knowing about internal nodes or links. A copied array can also hide nodes, but Iterator provides uniform traversal without requiring conversion. |
| 5 | Incorrect | Java object-array conversion copies **references**, not the complete objects. The extra array takes space, but large objects are not duplicated. |
| 6 | Accepted, with caveat | An iterator can work with the original collection and support coordinated changes. It does not necessarily incorporate arbitrary external modifications. Java's ordinary list iterators are fail-fast. |

## 1. Early stopping: avoid copying everything first

An iterator is like a cursor that remembers a position:

- `hasNext()`: is another element available?
- `next()`: return the next element and advance.

Illustrative Java snippets below assume the necessary `java.util` imports.

```java
LinkedList<String> names =
    new LinkedList<>(Arrays.asList("Alice", "Bob", "Charlie"));

Iterator<String> it = names.iterator();
while (it.hasNext()) {
    if (it.next().equals("Bob")) {
        break;
    }
}
```

This visits Alice and Bob, then stops. Compare:

```java
Object[] array = names.toArray(); // Copies ALL references first.
for (Object name : array) {
    if (name.equals("Bob")) {
        break;
    }
}
```

If Bob is second in a million-element list:

| Approach | Work |
| --- | --- |
| Usual linked-list iterator | Visit approximately two elements |
| Convert first | Copy one million references, then inspect two elements |

With a usual O(1)-per-step linked-list iterator, visiting only the first k elements takes O(k) time and O(1) iterator state. Conversion requires O(n) time and O(n) additional reference slots. These costs describe this implementation, not every possible iterator.

## 2. Declared element type versus actual object types

```java
Object[] values = {"Alice", 42, true};

ArrayList<Object> mixed = new ArrayList<>();
mixed.add("Alice");
mixed.add(42);
mixed.add(true);
```

Both containers have the declared element type `Object`. Their actual objects are `String`, `Integer` and `Boolean`; Java autoboxes the primitive values.

To restrict a list to strings:

```java
ArrayList<String> names = new ArrayList<String>();
// Equivalent shorter declaration:
// ArrayList<String> names = new ArrayList<>();

names.add("Alice");
// names.add(42);              // Compile-time error.
String first = names.get(0);   // No cast needed.
```

Retrieving from an Object-typed container instead gives an `Object`:

```java
Object value = values[0];
String name = (String) values[0]; // Valid because this object is a String.
```

Different runtime types are a feature of compatible types and polymorphism, not a special Iterator benefit.

## 3. Array conversion: separate container, shared objects

```java
ArrayList<String> names =
    new ArrayList<>(Arrays.asList("Alice", "Bob"));
Object[] snapshot = names.toArray();

names.add("Charlie");
names.remove("Alice");
```

| Container | Contents afterwards |
| --- | --- |
| `names` | Bob, Charlie |
| `snapshot` | Alice, Bob |

The array's slots are separate. They are not automatically synchronised with later list additions, removals or replacements.

However, both containers can still refer to the same mutable object:

```java
class Student {
    String name;
    Student(String name) {
        this.name = name;
    }
}

ArrayList<Student> students = new ArrayList<>();
students.add(new Student("Alice"));
Student[] snapshot = students.toArray(new Student[0]);

students.get(0).name = "Alicia";
System.out.println(snapshot[0].name); // Alicia
```

| Change through the list | Effect on the converted array |
| --- | --- |
| Add or remove an element | Array slots remain unchanged |
| Replace an element using `set()` | Array still refers to the old object |
| Modify a shared object's field | Change is visible through either reference |

**A copied reference is not a copied object.** The array preserves references captured during conversion, not a deep snapshot of every object's state.

## 4. Changes during iteration: what “fail-fast” means

Java's ordinary ArrayList and LinkedList iterators can detect unexpected structural modifications. The change itself succeeds; subsequent iterator use can throw `ConcurrentModificationException`.

```java
ArrayList<String> names =
    new ArrayList<>(Arrays.asList("Alice", "Bob"));
Iterator<String> it = names.iterator();

System.out.println(it.next()); // Alice
names.add("Charlie");          // Addition succeeds.
it.next();                    // Throws ConcurrentModificationException.
```

Conceptually, the iterator compares the collection's modification count with the count it expects:

```java
// Simplified explanation, not application code:
if (listModificationCount != expectedModificationCount) {
    throw new ConcurrentModificationException();
}
```

“External” means **outside this iterator**, even in the same thread. It does not necessarily mean another thread.

Using a supported iterator operation coordinates the change with traversal:

```java
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    if (it.next().equals("Bob")) {
        it.remove(); // Removes Bob from the original list.
    }
}
```

The iterator's own `remove()` updates its expected modification count. A `ListIterator` also supports adding elements through `add()`; a basic `Iterator` does not.

Fail-fast detection is best effort, not a concurrency-safety guarantee. Other iterator designs may use snapshots or different modification policies. Therefore, option 6 is the quiz's accepted answer, but **automatic support for arbitrary changes is not a universal property of Iterator**.

## Quick comparison

| Concept | Key fact |
| --- | --- |
| Array | Fixed length; direct indexing |
| ArrayList | Resizable array implementation |
| LinkedList | Linked nodes; indexed access requires traversal |
| `toArray()` | Separate array containing copied element references |
| Iterator | Common traversal interface that hides the underlying representation |

### Memory aid

**Iterator: one element at a time, hidden structure, no full array copy required.**  
**Modification support depends on the iterator's rules.**

</details>

## Sources

**Lecture:** `week02-uml-dp(6).pdf` (**U**), using PDF viewer pages with the cover as page 1:

- **PDF p. 87:** Iterator intent, representation hiding and reduced coupling.
- **PDF p. 88:** problems with directly exposing an internal array. This differs from returning a separate copied array.
- **PDF pp. 89–90:** pattern structure and iterator/collection interfaces.
- **PDF pp. 91–93:** collection, concrete iterator and client traversal example.

The lecture's small custom iterator illustrates the pattern; do not assume it has all the contracts or fail-fast behaviour of Java's standard iterators.

**Java implementation details:**

- [Oracle: LinkedList](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/LinkedList.html) — linked-node implementation, array conversion, list iterators and fail-fast policy.
- [Oracle: ArrayList](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/ArrayList.html) — resizable-array implementation and iterator behaviour.

The answer key comes from the supplied quiz feedback. The code snippets and collection comparisons are explanatory additions.

[Lecture source index](../notes/lecture-source-index.md) · [Exam revision guide](../notes/final-exam-revision.md)
