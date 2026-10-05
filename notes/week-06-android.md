# Week 6 — Android

## 1. Android App Basics
- Android apps can be written in **Java, Kotlin, or C++**.
- Compiled apps are packaged as an **APK**.
- Android uses a **permission-based security model**.
- By default, each app only gets access to what it needs.

## 2. App Components
Android apps are built from several components:

- **Activity**
  - Represents one screen / user interaction.
  - Usually the main thing users interact with.

- **Service**
  - Runs tasks in the background.
  - No user interface.

- **Broadcast Receiver**
  - Responds to system-wide events.
  - Example: low battery, screen turned off.

- **Content Provider**
  - Manages and shares persistent app data.

## 3. Activity
- An Android app does not normally start from `main()`.
- Android creates an `Activity` and calls lifecycle methods.

### Important lifecycle methods
- `onCreate()` → initialise the activity.
- `onStart()` → activity becomes visible.
- `onResume()` → user can interact with it.
- `onPause()` → activity loses foreground focus.
- `onStop()` → activity is no longer visible.
- `onDestroy()` → activity is destroyed.

### Important idea
```text
Android system
    ↓
Activity
    ↓
Lifecycle callbacks
```
## 4. Intent

An **Intent** is used to activate another component.

### Explicit Intent

You know exactly which Activity you want:

```
Intent intent =
    new Intent(getApplicationContext(), ActivityB.class);

startActivity(intent);
```

### Implicit Intent

- You specify an action rather than a particular Activity.
- Android finds an app/component capable of performing it.

```
Activity A
    ↓ Intent
Android System
    ↓
Activity B
```

## 5. User Interface

Android UI is mainly built from:

```
ViewGroup
├── View
├── View
└── ViewGroup
    ├── View
    └── View
```

- **View** → individual UI element
    - Button
    - TextView
    - Text box
- **ViewGroup** → container/layout holding Views.

## 6. XML Layouts

UI is commonly defined using XML.

Example:

```
<Button
    android:id="@+id/mybutton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Click me" />
```

Important properties:

- `id` → identifies the View.
- `match_parent` → fill the parent.
- `wrap_content` → only use as much space as needed.

Java can access the View using:

```
findViewById(R.id.mybutton);
```

## 7. Common Layouts

### LinearLayout

Arranges Views:

```
Vertical:
A
B
C
```

or

```
Horizontal:
A  B  C
```

### ConstraintLayout

- Positions Views relative to other Views.
- More flexible for complex interfaces.

## 8. Connecting Activity + XML

In `onCreate()`:

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
}
```

Mental model:

```
MainActivity.java
       ↓
setContentView()
       ↓
activity_main.xml
       ↓
Screen UI
```

## 9. Input Events

Android uses **event listeners**.

Common events:

- `onClick()`
- `onLongClick()`
- `onTouch()`
- `onKey()`
- `onFocusChange()`

Example:

```
button.setOnClickListener(v -> {
    // respond to click
});
```

So:

```
User clicks button
      ↓
Listener detects event
      ↓
onClick()
      ↓
Your Java code runs
```

## 10. Toast

A **Toast** is a temporary popup message.

```
Toast.makeText(
    getApplicationContext(),
    "Hello!",
    Toast.LENGTH_SHORT
).show();
```

## 11. Dynamic Layouts / Adapter

When UI content is not known in advance:

```
Data
 ↓
Adapter
 ↓
ListView / GridView
```

For arrays, Android can use:

```
ArrayAdapter
```

The adapter converts data into Views.

## 12. Android Studio Structure

Important files/folders:

```
app
├── java
│   └── MainActivity.java
│
└── res
    └── layout
        └── activity_main.xml
```

Think of it as:

```
Java → behaviour
XML  → appearance/layout
```

## 13. Pair Programming

Two people work together on the same code.

### Driver

- Uses the keyboard.
- Focuses on the immediate implementation.
- Tactical thinking.

### Navigator

- Reviews the code.
- Thinks about the bigger picture.
- Looks for bugs and next steps.
- Strategic thinking.

```
Driver = "How do we write this?"

Navigator = "Are we solving the right problem?"
```

### Ping-Pong Pair Programming

Uses TDD:

```
Developer A
↓
writes failing test

Developer B
↓
writes code to pass it

↓
switch roles
```

Often follows:

```
RED → GREEN → REFACTOR
```

---

# Core Mental Model

```
Android System
      ↓
   Activity
      ↓
  onCreate()
      ↓
Load XML Layout
      ↓
Views / Buttons
      ↓
User Interaction
      ↓
Event Listener
      ↓
Java Behaviour
      ↓
Intent → another Activity
```
