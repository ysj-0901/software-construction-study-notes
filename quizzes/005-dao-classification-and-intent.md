# Quiz 005 - DAO classification and primary intent

- **Topic:** UML / design patterns
- **Original label:** Question 1; quiz week not supplied.
- **Lecture source:** U, `week02-uml-dp(6).pdf`, PDF pp. 94–95.
- **Revision value:** Distinguish DAO, Observer, Façade, and automatic object-relational mapping.

## Question

Based strictly on the Design Patterns classification taught in the lecture slides, how is the DAO pattern categorised, and what is its primary intent?

### Options

1. It is categorised as a Behavioural pattern; its intent is to define a one-to-many dependency so that when one object changes state, all dependents are notified automatically.
2. It is categorised as a Structural pattern; its intent is to provide a unified, higher-level interface to a set of interfaces in a subsystem to make the subsystem easier to use.
3. It is categorised as an Enterprise pattern outside the standard GoF classifications; its intent is to automatically map domain objects to database tables and queries (transparent object-relational mapping).
4. It is categorised as a Structural pattern; its intent is to decouple domain logic from persistence mechanisms so as not to expose details of data storage (e.g. databases, flat files).

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 4.** The course places DAO under **Structural Patterns** on PDF p. 94 and states its data-access separation intent on PDF p. 95.

DAO means **Data Access Object**.

- **Domain logic:** rules about what the application does, such as calculating points or deciding which badge a user earns.
- **Persistence mechanisms:** how data is saved and loaded, such as database queries or reading and writing files.
- **Decouple:** make the business code depend on a data-access interface rather than on storage-specific operations.

### Intuitive example

Suppose a badge service needs to save a user's new points total:

```java
userDao.updatePoints(userId, newPoints);
```

This is an illustrative call, not a quotation from the lecture. The service decides the points total; the DAO implementation handles the storage operation. A database implementation might execute SQL, while a file implementation might update a file.

If both implementations satisfy the same DAO contract, the service can keep using the same operation when the storage implementation changes. The business rules remain outside the DAO.

### Why the other options are wrong

| Option | What it describes | Why it is not the answer |
| --- | --- | --- |
| 1 | Observer | One-to-many change notifications are Observer's intent, shown on U p. 63. |
| 2 | Façade | Simplifying access to a subsystem is Façade's intent, shown on U p. 105. Its Structural category alone is not enough to make it DAO. |
| 3 | Automatic object-relational mapping | DAO does not require automatic object-to-table mapping. It can hide file access as well as database access. The question explicitly requires the course classification. |
| 4 | DAO | Matches both the course's Structural category and its persistence-separation intent. |

**Exam wording matters:** use the classification explicitly taught in these slides. This answer does not claim that every design-pattern reference categorises DAO in the same way.

</details>

## Memory aid

**DAO hides storage details. Façade simplifies a subsystem. Observer notifies dependents.**

## Sources and related notes

All page numbers below are PDF viewer pages, counting the cover as page 1.

- **U p. 94:** Structural Patterns, explicitly listing DAO and Façade.
- **U p. 95:** DAO intent.
- **U pp. 98–104:** DAO interface and implementation example.
- **U p. 63:** Observer intent.
- **U p. 105:** Façade intent.
- [UML and design-pattern notes](../notes/week-02-uml-and-design-patterns.md)
- [Lecture source index](../notes/lecture-source-index.md)
- [Back to quiz index](README.md)
