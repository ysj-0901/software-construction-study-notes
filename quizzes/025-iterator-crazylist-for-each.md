# Quiz 025: Enable for-each traversal on CrazyList

**Original label:** Question 4. Quiz week not supplied.  
**Topic:** Iterator pattern and Java's enhanced for loop.  
[Back to quiz index](README.md)

## Question

A developer has invented a data structure called CrazyList. It offers substantial performance improvements over ArrayList in this scenario, but its internals are difficult for other developers to understand. The developer wants other developers to use a for-each loop on CrazyList. Which design pattern is most suitable?

1. Factory
2. Iterator
3. Facade
4. Template

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer is option 2, Iterator.**

An iterator tracks where traversal has reached and exposes operations for checking and retrieving the next element. Client code can traverse CrazyList while the collection and its iterator handle its storage and traversal details.

In Java, CrazyList can implement `Iterable<E>` and supply an `iterator()` method that returns an `Iterator<E>`. If `list` is a `CrazyList<String>`, callers can write:

```java
for (String value : list) {
    System.out.println(value);
}
```

Conceptually, this loop works as follows:

```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String value = it.next();
    System.out.println(value);
}
```

`Iterable<E>` supplies the iterator. `Iterator<E>` performs the traversal using `hasNext()` and `next()`.

| Option | Role |
| --- | --- |
| Factory | Organises how objects are created. |
| Iterator | Provides element-by-element traversal and fits the requested for-each syntax. |
| Facade | Provides a simpler entry point to a subsystem. |
| Template | Defines an algorithm skeleton with steps that subclasses can customise. |

The phrase **use a for-each loop** identifies the required operation. CrazyList's internal complexity explains why separating its traversal interface from its representation helps.

</details>

## Sources and related notes

- User-supplied Question 4 screenshot and marked answer.
- [Quiz 008 on Iterator versus array conversion](008-iterator-vs-array-conversion.md). Lecture U, PDF pp. 87–93 cover Iterator.
- [Back to quiz index](README.md)
