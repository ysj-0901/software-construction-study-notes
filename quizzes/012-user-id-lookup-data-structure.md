# Quiz 012: Store users for frequent lookup by ID

[Back to quiz index](README.md)

- **Topic:** Data structures / lookup, insertion and deletion
- **Original question number:** Not visible in the supplied screenshot.
- **Answer status:** Confirmed by the user-supplied quiz feedback.
- **Added:** 5 November 2026.

## Question

A social media application needs to store instances of a User class with corresponding numerical IDs in a data structure. The IDs for each user are unique, starting at 1 for the first user and counting up. It is expected that new Users will be added to this data structure at a rate of about 100 000 per day, and removed at a similar rate, but it is necessary to retrieve users by their ID at a rate of 100 000 000 per day. Which data structure is most appropriate for this goal?

### Options

1. An ArrayList of Users
2. An AVL Tree with Integers as keys and Users as values
3. An Array of Users
4. A Map with Integers as keys and Users as values

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: 4 — A Map with Integers as keys and Users as values.**

The quiz feedback confirms the final displayed option. Use the unique ID as the key and the corresponding User object as the value. A HashMap is a suitable implementation for this workload.

For example, in Java:

```java
Map<Integer, User> usersById = new HashMap<>();

usersById.put(id, user);        // Insert.
User found = usersById.get(id); // Retrieve by ID.
usersById.remove(id);           // Remove by ID.
```

The snippet assumes the relevant imports and existing User, id and user declarations. The key is the unique ID; the value is the corresponding User instance.

### Map versus HashMap

- `Map<Integer, User>` describes the key-value interface.
- `HashMap<Integer, User>` is a hash-table implementation of that interface.
- `TreeMap<Integer, User>` is another Map implementation, based on a red-black tree.

Therefore, **Map does not itself guarantee O(1) lookup**. The constant-time analysis below concerns a hash-based implementation with suitable hash distribution.

### Compare the supplied options

| Option | Assessment |
| --- | --- |
| 1. ArrayList of Users | Appending and index access are fast, but a compact list's indices shift after removal. Direct indexing with null slots is possible, with the storage tradeoff explained below. |
| 2. AVL Tree keyed by ID | A valid dynamic key-value structure with O(log n) lookup, insertion and deletion. Balancing handles increasing IDs, but the question does not require ordered traversal or range queries. |
| 3. Array of Users | Direct indexing can be fast, but a fixed-size array requires capacity management as new IDs are allocated; retaining slots after deletion can waste space. |
| 4. Map keyed by ID | **Accepted answer.** Expresses the requested ID-to-User lookup directly; a hash-based implementation supports efficient lookup and updates. |

### Identify the dominant operation

| Operation | Approximate count per day |
| --- | ---: |
| Insert | 100,000 |
| Remove | 100,000 |
| Retrieve by ID | 100,000,000 |

There are **1,000 lookups per insertion**, and **500 lookups per combined insertion/removal operation**. Fast lookup matters greatly, while insertion and removal still need to be efficient.

Let n be the number of users currently stored.

| Structure / approach | Lookup by ID | Insertion | Removal |
| --- | --- | --- | --- |
| HashMap keyed by ID | Expected O(1) | Expected amortized O(1) | Expected O(1) |
| AVL tree or TreeMap / red-black tree keyed by ID | O(log n) | O(log n) | O(log n) |
| Compact ArrayList searched linearly | O(n) | Amortized O(1) when appending | O(n) in general |

Hash-map costs assume suitable hash distribution; they are not an unconditional worst-case O(1) guarantee. Resizing can make an individual insertion slower. A tree remains useful when sorted keys or range queries are required, but neither is requested here.

### Why the numerical, increasing IDs need care

Increasing IDs make **direct indexing** possible: reserve position id - 1 for each user. This can also give O(1) lookup.

However, calling `remove(index)` on a compact ArrayList shifts later elements left. Their indices then no longer match their IDs.

An alternative is to leave a null slot for each deleted user, using `set(id - 1, null)`. That avoids shifting and permits O(1) deletion. The tradeoff is retaining slots for old IDs: as IDs keep increasing without reuse, storage can follow the maximum ID ever allocated rather than the current live-user count.

Therefore, an array-based direct-address design is a genuine alternative under suitable space assumptions. Do not claim that every ArrayList lookup must scan, or that every array-based deletion must shift elements. **The confirmed quiz answer is the Map option; that does not mean direct indexing is impossible.**

</details>

## Common mistake

Choosing solely because IDs start at 1, without considering deletion, long-term ID growth and whether IDs remain aligned with list indices.

**Memory aid:** Exact-key lookup plus frequent updates suggests a hash map; ordering suggests a balanced tree; dense bounded integer keys can suit direct indexing.

## Sources and related notes

- User-supplied question screenshot and follow-up options screenshot confirming the Map option.
- [Java 17 HashMap documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html): key-value mapping, hashing assumptions and resizing.
- [Java 17 ArrayList documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html): get/set, appending and removal.
- [Java 17 TreeMap documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html): red-black tree implementation and logarithmic operations.
- [Data-structure notes](../notes/week-03-data-structures.md)
- No lecture PDF page is assigned because an exact slide reference has not been verified.

[Back to quiz index](README.md)
