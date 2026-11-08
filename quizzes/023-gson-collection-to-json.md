# Quiz 023: Convert a collection to a JSON string with Gson

**Original label:** Question 2. Quiz week not supplied.  
**Topic:** Persistent data and JSON serialisation.  
[Back to quiz index](README.md)

## Question

Suppose you have a variable `accounts` of type `Collection<Account>`. If `gson` is an instance of Gson, how can `accounts` be written to a JSON string?

### Options

1. `String data = gson.toJson<Account>(accounts);`
2. `String data = gson.toJson(accounts.stream());`
3. `String data = gson.toJson(accounts);`
4. `String data = gson.toJson(accounts, 0, accounts.size());`

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct choice is 3**, as marked in the quiz feedback.

```java
String data = gson.toJson(accounts);
```

Gson is a Java library. Its `toJson(Object src)` method accepts a Java object and returns the JSON representation as a `String`. Pass the collection itself. Gson represents the collection as a JSON array and serialises its elements.

For example, suppose there are two Account objects and their only serialised field is an integer `id`. The result could be:

```json
[{"id":101},{"id":102}]
```

The variable `data` holds this text in memory. Writing the string to a file would be a separate operation.

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | Incorrect | The placement of `<Account>` after the method name is invalid here. The one-argument `toJson` overload takes an Object, so pass `accounts` directly. |
| 2 | Incorrect | `accounts.stream()` creates a Java Stream. Pass the collection to serialise its contents as a JSON array. |
| 3 | Correct | The collection is the input and the result is a JSON string. |
| 4 | Incorrect | Gson has no `toJson` overload taking a collection, a start index and an element count. |

### Reading the JSON back

Going from a collection to JSON is **serialisation**. Going from JSON back to a collection is **deserialisation**. For the latter, Gson needs to know that the elements should be Account objects. A `TypeToken` can specify the full destination type:

```java
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

// Inside a method, with gson and data already defined:
Type accountType = new TypeToken<Collection<Account>>() {}.getType();
Collection<Account> restored = gson.fromJson(data, accountType);
```

The TypeToken example is supplementary. The quiz only asks how to write the JSON string.

</details>

## Common mistake

Reading `Collection<Account>` as an instruction to add `<Account>` to the method call. It tells you the type of the variable. The call passes that variable as its argument.

## Sources and related notes

- User-supplied Question 2 screenshot and its marked answer.
- [Week 4 persistent data notes](../notes/week-04-persistent-data.md). PD, PDF pp. 28–30 cover JSON concepts. The Gson call is documented by the library.
- [Official Gson User Guide, Collections Examples](https://github.com/google/gson/blob/main/UserGuide.md#collections-examples)
- [Gson source and method documentation](https://github.com/google/gson/blob/main/gson/src/main/java/com/google/gson/Gson.java)
- [Back to quiz index](README.md)
