# Quiz 001 — Android Activity Lifecycle

- **Topic:** Android
- **Original label:** Question 3 (quiz week not supplied)
- **Question source:** Quiz screenshot shared for revision
- **Reviewed:** 3 November 2026
- **Lecture source:** **A**, `week06-android(1).pdf`, **PDF pp. 8–10**, especially the lifecycle diagram on p. 9. Page counting includes the cover.

## Question

Which one of the following methods is **NOT** involved in the lifecycle of the Activity class in Android?

- A. `onClose()`
- B. `onCreate()`
- C. `onStop()`
- D. `onDestroy()`

The letter labels are added here for convenient reference; the option order matches the screenshot.

<details>
<summary><strong>Answer and explanation</strong></summary>

## Correct answer: A — `onClose()`

`onClose()` is **not a standard Android Activity lifecycle callback**. The other three methods belong to the Activity lifecycle.

An Activity usually represents a screen in an Android app. Lifecycle callbacks let the activity respond when Android creates it, changes its visibility or interaction state, or destroys that instance.

| Option | Activity lifecycle callback? | Explanation |
| --- | --- | --- |
| `onClose()` | No — correct selection | Android does not define this as an Activity lifecycle callback. A developer could write a custom method with this name, but it would not become a lifecycle callback. |
| `onCreate()` | Yes | Initialises the new activity instance; commonly sets up the layout, variables and listeners. |
| `onStop()` | Yes | Runs when the activity is no longer visible. The activity may return later. |
| `onDestroy()` | Yes | The callback associated with destruction of the activity instance. Its name may sound unusual, but it is a real lifecycle method. |

### Why choosing `onDestroy()` is incorrect

The question asks for the method that is **not** part of the lifecycle. Destruction is a lifecycle stage, so `onDestroy()` belongs in it.

**Stopped does not mean destroyed.** A stopped activity can return through:

`onRestart()` → `onStart()` → `onResume()`

Destroying one activity instance also does not necessarily mean that the entire app has ended.

### Memory aid: three pairs

| Pair | Main idea |
| --- | --- |
| **Create ↔ Destroy** | Lifetime of the activity instance |
| **Start ↔ Stop** | Visibility |
| **Resume ↔ Pause** | Foreground interaction |

For this question, remember: **Create, Start, Resume, Pause, Stop, Destroy — not Close.** `onRestart()` handles a stopped activity returning.

### Common mistake

Do not choose the option that merely sounds least familiar. Match the name to the lifecycle model, and notice the word **NOT** in the question.

</details>

## Related revision

- [Android notes](../notes/week-06-android.md)
- [Lecture source index](../notes/lecture-source-index.md)
- [Android Developers: Activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)

[Back to quiz index](README.md)
