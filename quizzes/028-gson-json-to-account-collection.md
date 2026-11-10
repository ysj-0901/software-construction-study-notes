# Quiz 028: Parse JSON back into a collection of accounts

**Original label:** Question 2; quiz week not supplied.  
**Reviewed:** 10 November 2026.  
**Topic:** Gson deserialisation and generic type information.  
[Back to quiz index](README.md)

## Question

Suppose we have a variable `accounts` of type `Collection<Account>`. If `gson` is an instance of Gson, accounts can be written to a JSON string as:

```java
String data = gson.toJson(accounts);
```

Next, how can the data variable be parsed back to a `Collection<Account>`?

### Options as shown in the screenshot

1. `accounts = gson.fromJson(data, new TokenType<Collection<Account>>());`
2. `accounts = gson.fromJson(data);`
3. `accounts = gson.fromJson(data, new Collection<Account>());`
4. `accounts = gson.fromJson<Collection<Account>>(data);`

<details>
<summary><strong>Answer and explanation</strong></summary>

**The quiz accepts option 1. Its intended idea is correct, but its syntax is not valid for standard Gson as written.**

The screenshot says `TokenType`. Gson uses **`TypeToken`**, and the anonymous-subclass `{}` is needed in this idiom.

A corrected form is:

```java
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;

// Inside a method, with gson, data and accounts already defined:
Type accountCollectionType =
    new TypeToken<Collection<Account>>() {}.getType();
accounts = gson.fromJson(data, accountCollectionType);
```

Read the second argument as a **type label**: “Create a collection whose elements are Account objects.”

### Why supply a type?

JSON contains values, not Java class declarations. `Collection.class` alone does not describe the element type. Java's type erasure means the declared `Collection<Account>` variable does not automatically supply that information to Gson.

The `{}` creates an anonymous subclass capturing the full generic type; `.getType()` retrieves its type description.

For example, this JSON could reconstruct two Account objects with integer `id` fields:

```json
[{"id":101},{"id":102}]
```

### Compare the options

| Option | Issue |
| --- | --- |
| 1 | Intended answer; correct the name and type-token construction as above. |
| 2 | Missing target type. |
| 3 | Collection is an interface and cannot be instantiated directly; an ordinary collection instance is also not a type descriptor. |
| 4 | Invalid Java syntax and no runtime target-type argument. |

</details>

## Common mistake

Memorising the screenshot's `TokenType` spelling as working Gson code.

**Memory aid:** `toJson(accounts)` writes JSON; `fromJson(data, type)` reads JSON. For `Collection<Account>`, describe both the collection and its elements.

## Sources and related notes

- User-supplied Question 2 screenshot and marked answer, reviewed 10 November 2026.
- [Official Gson User Guide: Collections Examples](https://google.github.io/gson/UserGuide.html#collections-examples)
- [Official Gson User Guide: Generic Types](https://google.github.io/gson/UserGuide.html#serializing-and-deserializing-generic-types)
- [The reverse operation: collection to JSON](023-gson-collection-to-json.md)
- [Persistent data notes](../notes/week-04-persistent-data.md). PD, PDF pp. 28–30 provide JSON background; the Gson-specific syntax comes from its documentation.
