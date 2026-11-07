# Quiz 018: Template Method for route planning

**Original label:** Question 2; quiz week not supplied.  
**Topic:** A fixed workflow with customisable steps.  
[Back to quiz index](README.md)

## Question

Consider a logistics application used by a package delivery company. They have written a number of algorithms to find the optimal route for a number of deliveries, and a number of ways the output route can be displayed (e.g. directions in text, or graphically). Which design pattern is most suited to this situation?

1. State
2. Factory
3. Iterator
4. Template

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: Template (Template Method).** This is the answer confirmed by the supplied quiz feedback.

### Translate the scenario into two steps

The intended interpretation is a shared workflow:

1. Calculate a delivery route.
2. Display the calculated route.

The order stays the same, while the implementation of each step can vary.

| Part | Fixed or variable? | Example |
| --- | --- | --- |
| Overall workflow | Fixed | Calculate first, display second |
| Route calculation | Variable | Use algorithm A or algorithm B |
| Route display | Variable | Show text directions or draw a map |

The algorithm names below are illustrative placeholders, not algorithms specified by the question.

### Minimal Java example

```java
abstract class DeliveryPlanner {
    public final void run() {          // Template method
        String route = calculateRoute();
        displayRoute(route);
    }

    protected abstract String calculateRoute();
    protected abstract void displayRoute(String route);
}

class TextPlanner extends DeliveryPlanner {
    @Override
    protected String calculateRoute() {
        return "Depot -> A -> B";      // Stand-in for route calculation
    }

    @Override
    protected void displayRoute(String route) {
        System.out.println(route);
    }
}
```

Calling `new TextPlanner().run()` uses the parent's workflow and the child's implementations of its steps. The route is hard-coded only to keep this teaching example short.

- **Parent:** defines which steps run and in what order.
- **Child:** defines how the variable steps work.
- **`final`:** prevents overriding `run()` in this example, preserving the workflow.

A different subclass could calculate the route differently and draw it on a map. The shared `run()` method would stay the same.

### Why the other options do not fit

| Pattern | Main concern | Why it is not the intended answer |
| --- | --- | --- |
| State | Behaviour changes with an object's current state | The question is not about transitions such as pending, in transit and delivered. |
| Factory | Creating the appropriate kind of object | The focus is performing a workflow, not selecting which class to instantiate. |
| Iterator | Traversing elements of a collection | Although there are multiple deliveries, the question focuses on route calculation and presentation, not collection traversal. |
| Template Method | A shared workflow with overridable steps | Matches the intended calculate-then-display structure. |

### A useful qualification

The wording alone does not require inheritance or explicitly state a fixed workflow. Template Method is the intended answer **among these four options**, interpreting the application as a shared procedure with variable steps.

If calculation algorithms and display behaviours must be freely combined or switched at runtime, separate Strategy objects would also be a natural design. Multiple algorithms alone do not automatically imply Template Method.

### Memory aid

**Same steps and order, different implementations → Template Method.**

</details>
