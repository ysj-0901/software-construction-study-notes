# Quiz 030: Liskov substitution, vector inheritance and casting

**Original label:** Question 5; quiz week not supplied.  
**Reviewed:** 10–11 November 2026.  
**Topic:** LSP, stronger preconditions, overriding and reference casts.  
[Back to quiz index](README.md)

## Question

The quiz provides this class:

```java
class Vector2 {
    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Returns the distance from the origin to this vector.
    public double getLength() {
        return Math.sqrt(x*x + y*y);
    }

    // Scales the vectors together componentwise
    public Vector2 dilate(Vector2 other) {
        return new Vector2(x*other.x, y*other.y);
    }
}
```

Video game engines frequently use classes like the one partially shown above to represent a position in 2D space. A developer is tasked with extending this functionality by writing a Vector3 class to represent a position in 3D space by adding a z-axis.

The developer is considering implementing Vector3 as a subclass of Vector2. Why would this violate the Liskov substitution principle?

### Options

1. An implementation of Vector3 cannot satisfy the interface of Vector2's dilate method, as its argument must be of type Vector3.
2. If a client tries to cast a Vector3 into a Vector2, the information stored in the z component will be garbage collected.
3. Because x and y are private fields, they could not be modified or accessed from within Vector3.
4. Vector3 would need to override the behaviour of getLength() to account for the z-axis, which is prohibited by the Liskov substitution principle.

<details>
<summary><strong>Answer and explanation</strong></summary>

**The quiz accepts option 1.** The supplied screenshot marks it correct.

### 1. The intended LSP issue: requiring more from the caller

LSP requires a subtype to remain usable wherever its parent type is expected, while honouring the parent's behavioural contract.

Vector2's method accepts another Vector2. Ordinary non-null Vector2 arguments are valid:

```java
Vector2 a = new Vector2(1, 2);
a.dilate(new Vector2(4, 5));
```

Under the quiz's intended three-dimensional, componentwise operation, Vector3 wants another vector with a z component. If its implementation rejects ordinary Vector2 arguments and accepts only Vector3, it **strengthens the precondition**.

A caller using the parent type would then encounter a new restriction:

```java
Vector2 a = new Vector3(1, 2, 3);
a.dilate(new Vector2(4, 5)); // A valid parent-type input must remain supported.
```

The violation is: **the parent accepts an input that the subtype refuses.**

**Wording nuance:** Java does not prohibit declaring Vector3 as a subclass of Vector2. Option 1 assumes the intended 3D operation requires a Vector3 operand and provides no contract-compatible rule for mixed dimensions. Its word “cannot” should not be read as a universal impossibility. The LSP problem arises from restricting or otherwise breaking the inherited contract.

### 2. Overriding versus overloading

For the non-generic method in this question, an override keeps the parameter type Vector2. The parameter name may change.

Assume Vector3 extends Vector2. These are alternative declarations, not methods to declare together:

| Subclass declaration | Meaning |
| --- | --- |
| `public Vector2 dilate(Vector2 other)` | Override with the same return type. |
| `public Vector3 dilate(Vector2 other)` | Override with a covariant return type. |
| `public Vector2 dilate(Vector3 other)` | Overload: the parameter type has changed. |

Adding `@Override` to the third declaration produces a compile-time error. A Vector2 parameter can still receive a Vector3 object.

Merely adding an overload leaves the inherited `dilate(Vector2)` available; the overload alone does not prove an LSP violation. A method can also keep the correct signature yet violate LSP by rejecting parent-valid inputs inside its body.

**Memory aid:** Preserve the parameter type when overriding here; the return type may be more specific. Matching the signature is not enough to prove behavioural substitutability.

### 3. Why casting does not delete z

Option 2 is factually wrong. Upcasting changes how a reference is typed; it does not remove fields or create a smaller object.

Use the original Vector2 above with this supplementary subclass:

```java
class Vector3 extends Vector2 {
    private double z;

    public Vector3(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }
}
```

This subclass demonstrates casting only. It does not implement full 3D vector behaviour or solve the dilate contract problem.

Inside a method:

```java
Vector3 v3 = new Vector3(1, 2, 3);
Vector2 v2 = v3;

System.out.println(v3.getZ());             // 3.0
// v2.getZ();                              // Compile-time error.
System.out.println(((Vector3) v2).getZ());  // 3.0

Vector3 back = (Vector3) v2;
System.out.println(back == v3);            // true: the same object.
```

| Expression | Explanation |
| --- | --- |
| `v3.getZ()` | Vector3 declares getZ(), so the call is available. |
| `v2.getZ()` | The reference type Vector2 does not declare getZ(). |
| `((Vector3) v2).getZ()` | The cast succeeds because this object actually is a Vector3; z still equals 3.0. |
| `back == v3` | Both references identify the original object. |

The z value was never deleted or recreated. Casting a plain Vector2 object to Vector3 would instead fail at run time; a cast cannot add missing state.

**Memory aid:** A member unavailable through a parent-typed reference is not a deleted member.

### 4. Evaluate the remaining distractors

| Option | Evaluation |
| --- | --- |
| 3: private x and y | A separate subclass cannot directly access these private fields. That access restriction is real, but it is not itself an LSP violation. Parent methods can operate on their own private state. |
| 4: overriding getLength | LSP does not ban overriding. An override must preserve the parent's contract; whether a changed length calculation is appropriate depends on that contract. |

Therefore, “a statement with some truth that does not answer the LSP question” fits **option 3's direct-access observation**, not option 2's garbage-collection claim.

</details>

## Common mistakes

- Confusing a narrower return type, which Java permits here, with a narrower parameter type, which creates an overload.
- Treating a valid method signature as proof that the implementation respects LSP.
- Thinking upcasting deletes subclass fields.
- Treating private access or overriding itself as an LSP violation.

**Exam reminder:** Subtypes must require no more and promise no less.

## Sources and related notes

- User-supplied Question 5 code, options and marked answer, plus the follow-up discussion on 10–11 November 2026.
- [SOLID and Liskov substitution notes](../notes/week-07-refactoring-and-b-trees.md): R, PDF pp. 28–34.
- [Design by Contract notes](../notes/week-08-design-by-contract.md): preconditions and postconditions.
- [Java Language Specification, reference conversions](https://docs.oracle.com/javase/specs/jls/se25/html/jls-5.html#jls-5.1.5)
- [Java Language Specification, overriding requirements](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.8.3)
- [Java Language Specification, overloading](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.9)
