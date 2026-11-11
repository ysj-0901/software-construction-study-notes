# COMP6442 Quiz Reviews

A growing collection of weekly quiz questions reviewed during exam preparation. All questions and explanations are recorded in English.

**Currently recorded: 39 questions.** This collection contains the questions shared for review so far, not every question from every weekly quiz.

## Browse by exam topic

Each question appears under its main exam topic. Use the [numbered question index](#question-index) for original question labels and lecture references.

[Git](#git-and-version-control) · [UML and patterns](#uml-and-design-patterns) · [SOLID and contracts](#solid-and-design-by-contract) · [Data structures](#data-structures-and-traversal) · [Persistent data](#persistent-data) · [Testing](#software-testing) · [Android](#android) · [Performance](#performance)

### Git and version control

| Exam focus | Questions |
| --- | --- |
| Read commit graphs and identify hashes | [004 Identify a commit's short hash from a history graph](004-git-history-short-hash.md) |
| Branch pointers, HEAD and creating branches | [010 What does a Git branch represent?](010-git-branch-pointer.md)<br>[011 HEAD after creating a branch and committing](011-git-head-branch-and-commit.md) |
| Merge direction and diverged history | [014 Merge direction and a diverged history](014-git-merge-direction.md) |
| Find deleted files in history | [017 Find deleted files in commit history](017-git-deleted-files.md) |

### UML and design patterns

| Exam focus | Questions |
| --- | --- |
| UML relationships in DAO, State and Iterator | [009 Identify relationships in DAO, State and Iterator](009-uml-relationships-dao-state-iterator.md) |
| DAO intent and classification | [005 DAO classification and primary intent](005-dao-classification-and-intent.md) |
| State objects and state-specific behaviour | [006 State pattern versus a boolean flag](006-state-pattern-vs-boolean.md) |
| Factory and platform-specific object creation | [007 Factory UML and selecting an email notification](007-factory-uml-notifications.md)<br>[013 Choose a platform-specific save dialog](013-factory-platform-save-dialog.md) |
| Iterator, array conversion and Java for-each | [008 Iterator versus array conversion](008-iterator-vs-array-conversion.md)<br>[025 Enable for-each traversal on CrazyList](025-iterator-crazylist-for-each.md) |
| Observer, event subscriptions and polling | [015 Observer for keyboard input: polling versus subscriptions](015-observer-keyboard-input.md) |
| Template Method and customisable algorithm steps | [018 Template Method for route calculation and display](018-template-route-planning.md) |
| Facade and package access modifiers | [034 Public facade with package-private helpers](034-facade-package-access-modifiers.md) |

### SOLID and design by contract

| Exam focus | Questions |
| --- | --- |
| LSP, preconditions, overriding and reference casts | [030 Vector inheritance and casting](030-liskov-vector-inheritance-and-casting.md) |
| Interface checks versus behavioural contracts | [035 Design by Contract and interface enforcement](035-design-by-contract-interface-enforcement.md) |
| JML assertions, pure methods and contract keywords | [036 Pure methods in JML preconditions](036-jml-pure-methods-in-preconditions.md) |

### Data structures and traversal

| Exam focus | Questions |
| --- | --- |
| Pre-order and post-order traversal, including interval keys | [002 Red-black interval keys and pre-order traversal](002-red-black-interval-preorder.md)<br>[019 Neighbours in a pre-order traversal](019-preorder-traversal-neighbours.md)<br>[024 Neighbours in a post-order traversal](024-postorder-traversal-neighbours.md) |
| Choose a data structure for lookup by ID | [012 Store users for frequent lookup by ID](012-user-id-lookup-data-structure.md) |
| BST deletion using successor or predecessor | [029 Delete a node with two children](029-bst-delete-node-with-two-children.md) |
| AVL insertion and single/double rotations | [033 Why inserting 6 needs a right-left rotation](033-avl-right-left-insertion.md) |

### Persistent data

| Exam focus | Questions |
| --- | --- |
| File-size comparison, text/binary encoding and primitive widths | [038 Serialized, CSV, JSON and XML sizes](038-persistent-format-size-comparison.md) |
| Store binary images as paths or encoded text | [016 Store a PNG profile picture: file path or encoded text](016-persistent-image-data.md) |
| Escape strings in JSON, XML and CSV | [020 Escape strings in JSON, XML and CSV](020-json-xml-csv-escaping.md) |
| OpenCSV field binding by position or header name | [032 Map the year column to yearMade](032-opencsv-year-field-binding.md) |
| Serialise and deserialise Java collections with Gson | [023 Convert a collection to a JSON string with Gson](023-gson-collection-to-json.md)<br>[028 Parse JSON back into a collection of accounts](028-gson-json-to-account-collection.md) |

### Software testing

| Exam focus | Questions |
| --- | --- |
| Coverage subsumption: path, branch and condition | [039 Coverage relationships](039-coverage-subsumption.md) |
| Branch coverage and minimum test cases | [021 Minimum tests for complete branch coverage](021-minimum-branch-coverage.md) |
| Path versus branch coverage percentages | [031 Why 70% path coverage does not guarantee 70% branch coverage](031-path-versus-branch-coverage-percentages.md) |
| JUnit 4 assertions, exceptions, timeouts and parameterised tests | [022 Review a JUnit 4 factorial test class](022-junit-factorial-test-review.md)<br>[027 Choose an assertion for string reversal](027-junit-string-reversal-assertion.md)<br>[037 Parameterised tests across algorithm implementations — priority review](037-junit-parameterised-algorithm-tests.md) |

### Android

| Exam focus | Questions |
| --- | --- |
| Activity lifecycle callbacks | [001 Which method is not an Activity lifecycle callback?](001-android-activity-lifecycle.md) |
| ConstraintLayout anchors and horizontal alignment | [026 Centre a view on the edge of another view](026-constraintlayout-same-horizontal-anchor.md) |

### Performance

| Exam focus | Questions |
| --- | --- |
| Amdahl's law and end-to-end latency | [003 Amdahl's law and overall latency](003-amdahls-law-latency.md) |

## Review session: 10 November 2026

| Supplied question | Saved entry | Result |
| --- | --- | --- |
| Question 1: string reversal assertion | [027 JUnit string equality](027-junit-string-reversal-assertion.md) | New entry |
| Question 2: JSON back to accounts | [028 Gson deserialisation](028-gson-json-to-account-collection.md) | New entry; screenshot syntax correction included |
| Question 3: delete M from the BST | [029 BST deletion](029-bst-delete-node-with-two-children.md) | New entry |
| Question 4: keyboard input notifications | [015 Observer](015-observer-keyboard-input.md) | Existing question reviewed again; not counted twice |

## Review continuation: 11 November 2026

Added Question 5 as [030 Liskov substitution, vector inheritance and casting](030-liskov-vector-inheritance-and-casting.md), including the follow-up example showing that upcasting preserves z.

Added [031 Path coverage versus branch coverage percentages](031-path-versus-branch-coverage-percentages.md), with an enumerated counterexample showing 70% path coverage and 56.25% branch coverage.

Added [032 OpenCSV year-field binding](032-opencsv-year-field-binding.md), explaining position 3, the year/yearMade mismatch, and the corrected name-based alternative.

Added [033 AVL right-left insertion](033-avl-right-left-insertion.md), with rotation definitions, the first unbalanced ancestor, and diagrams for both rotations.

Added [034 Facade and package access modifiers](034-facade-package-access-modifiers.md), explaining a public facade, package-private helpers, and the restriction on protected top-level classes.

Added [035 Design by Contract and interface enforcement](035-design-by-contract-interface-enforcement.md) and [036 JML pure methods in preconditions](036-jml-pure-methods-in-preconditions.md) from the Week 08 lecture exercises, including the incorrect-statement answers a and c, examples, and English summaries.

Added [037 JUnit 4 parameterised algorithm tests](037-junit-parameterised-algorithm-tests.md), marked for priority review, with corrected code, an execution trace, annotation roles and the static/non-static distinction.

Added [038 Persistent format size comparison](038-persistent-format-size-comparison.md), with matching CSV/JSON/XML examples, binary versus text numbers, primitive widths and the limits of the expected ordering.

Added [039 Coverage subsumption](039-coverage-subsumption.md), explaining why path coverage subsumes branch coverage and providing counterexamples for the other options.

## Question index

| Entry | Topic | Question | Original label | Lecture reference |
| --- | --- | --- | --- | --- |
| 001 | Android | [Which method is not an Activity lifecycle callback?](001-android-activity-lifecycle.md) | Question 3; quiz week not supplied | A, PDF pp. 8–10 |
| 002 | Data structures | [Red-black interval keys and pre-order traversal](002-red-black-interval-preorder.md) | Question 4; quiz week not supplied | D, PDF pp. 33–34, 94–103 |
| 003 | Performance | [Amdahl's law and overall latency](003-amdahls-law-latency.md) | Question 5; quiz week not supplied | F, PDF pp. 4–5 |
| 004 | Git / version control | [Identify a commit's short hash from a history graph](004-git-history-short-hash.md) | Number not visible; resource: week-02-quiz | Linked GitLab exercise; terminal log verified |
| 005 | Design patterns | [DAO classification and primary intent](005-dao-classification-and-intent.md) | Question 1; quiz week not supplied | U, PDF pp. 94–95 |
| 006 | Design patterns | [State pattern versus a boolean flag](006-state-pattern-vs-boolean.md) | Question 2; quiz week not supplied | U, PDF pp. 69–77 |
| 007 | UML / Design patterns | [Factory UML and selecting an email notification](007-factory-uml-notifications.md) | Question 4; quiz week not supplied | U, PDF pp. 15–21, 53–57 |
| 008 | Design patterns / Java collections | [Iterator versus array conversion](008-iterator-vs-array-conversion.md) | Question 3; quiz week not supplied | U, PDF pp. 87–93 |
| 009 | UML relationships | [Identify relationships in DAO, State and Iterator](009-uml-relationships-dao-state-iterator.md) | Question 5; quiz week not supplied | U, PDF pp. 15–22, 69–77, 87–93 |
| 010 | Git / version control | [What does a Git branch represent?](010-git-branch-pointer.md) | Question 1; quiz week not supplied | Supplied quiz; official Git book |
| 011 | Git / version control | [HEAD after creating a branch and committing](011-git-head-branch-and-commit.md) | Question 3; quiz week not supplied | Supplied quiz; official Git book |
| 012 | Data structures | [Store users for frequent lookup by ID](012-user-id-lookup-data-structure.md) | Number not visible; Map answer confirmed | Supplied quiz; official Java documentation |
| 013 | Design patterns | [Choose a platform-specific save dialog](013-factory-platform-save-dialog.md) | Question 2; quiz week not supplied | U, PDF pp. 52–58, 63–86 |
| 014 | Git / version control | [Merge direction and a diverged history](014-git-merge-direction.md) | Question 3; quiz week not supplied | Supplied history graph; official Git documentation |
| 015 | Design patterns | [Observer for keyboard input: polling versus subscriptions](015-observer-keyboard-input.md) | Question 4; quiz week not supplied | U, PDF pp. 63–68 |
| 016 | Persistent data | [Store a PNG profile picture: file path or encoded text](016-persistent-image-data.md) | Question number not visible; quiz week not supplied | Supplied quiz; Java and format documentation |
| 017 | Git / version control | [Find deleted files in commit history](017-git-deleted-files.md) | Question number not visible; quiz week not supplied | Supplied quiz feedback; Git log documentation |
| 018 | Design patterns | [Template Method for route calculation and display](018-template-route-planning.md) | Question 2; quiz week not supplied | Supplied quiz; U, PDF pp. 78–86 |
| 019 | Data structures | [Neighbours in a pre-order traversal](019-preorder-traversal-neighbours.md) | Question 3; quiz week not supplied | Supplied tree and quiz feedback |
| 020 | Persistent data | [Escape strings in JSON, XML and CSV](020-json-xml-csv-escaping.md) | Question 4; quiz week not supplied | Supplied quiz; JSON, XML and CSV specifications |
| 021 | Testing | [Minimum tests for complete branch coverage](021-minimum-branch-coverage.md) | Question 5; quiz week not supplied | Supplied control-flow graph and quiz feedback |
| 022 | Testing | [Review a JUnit 4 factorial test class](022-junit-factorial-test-review.md) | Question 1, quiz week not supplied | T, PDF pp. 18–38, 51–57 and supplied quiz feedback |
| 023 | Persistent data | [Convert a collection to a JSON string with Gson](023-gson-collection-to-json.md) | Question 2, quiz week not supplied | Supplied quiz and Gson User Guide. JSON background in PD, PDF pp. 28–30 |
| 024 | Data structures | [Neighbours in a post-order traversal](024-postorder-traversal-neighbours.md) | Question number and quiz week not visible | Supplied tree and quiz feedback |
| 025 | Design patterns / Java collections | [Enable for-each traversal on CrazyList](025-iterator-crazylist-for-each.md) | Question 4, quiz week not supplied | U, PDF pp. 87–93 and supplied quiz feedback |
| 026 | Android | [Centre a view on the edge of another view](026-constraintlayout-same-horizontal-anchor.md) | Question 5, quiz week not supplied | Supplied quiz and AndroidX ConstraintLayout. Layout background in A, PDF pp. 15–16 |
| 027 | Testing | [Choose an assertion for string reversal](027-junit-string-reversal-assertion.md) | Question 1; reviewed 10 Nov 2026, quiz week not supplied | Supplied quiz; JUnit Assert API; T, PDF pp. 18–38 |
| 028 | Persistent data | [Parse JSON back into a collection of accounts](028-gson-json-to-account-collection.md) | Question 2; reviewed 10 Nov 2026, quiz week not supplied | Supplied quiz; Gson User Guide; JSON background in PD, PDF pp. 28–30 |
| 029 | Data structures | [Delete a BST node with two children](029-bst-delete-node-with-two-children.md) | Question 3; reviewed 10 Nov 2026, quiz week not supplied | Supplied tree and quiz feedback; D, PDF pp. 56–57, 71–75 |
| 030 | SOLID / Java inheritance | [Liskov substitution, vector inheritance and casting](030-liskov-vector-inheritance-and-casting.md) | Question 5; reviewed 10–11 Nov 2026, quiz week not supplied | Supplied quiz; R, PDF pp. 28–34; Java Language Specification |
| 031 | Testing | [Path coverage versus branch coverage percentages](031-path-versus-branch-coverage-percentages.md) | Question 1; reviewed 11 Nov 2026, quiz week not supplied | Supplied quiz; T, PDF pp. 58–61, 68–71; independently checked counterexample |
| 032 | Persistent data | [Bind the CSV year column to yearMade with OpenCSV](032-opencsv-year-field-binding.md) | Question 2; reviewed 11 Nov 2026, quiz week not supplied | Supplied quiz feedback; official OpenCSV annotation documentation |
| 033 | Data structures | [AVL right-left rotation after inserting 6](033-avl-right-left-insertion.md) | Question number and quiz week not visible; reviewed 11 Nov 2026 | Supplied tree and quiz feedback; D, PDF pp. 113–127 |
| 034 | Design patterns / Java access | [Facade and Java package access modifiers](034-facade-package-access-modifiers.md) | Question 4; reviewed 11 Nov 2026, quiz week not supplied | Supplied quiz; U, PDF pp. 105–109; Java Language Specification |
| 035 | Design by Contract / Java interfaces | [Design by Contract and interface enforcement](035-design-by-contract-interface-enforcement.md) | Week 08 lecture exercise; reviewed 11 Nov 2026 | C, PDF p. 43; background pp. 7–8, 13–18, 26, 40 |
| 036 | Design by Contract / JML | [Pure methods in JML preconditions](036-jml-pure-methods-in-preconditions.md) | Week 08 lecture exercise; reviewed 11 Nov 2026 | C, PDF p. 44; background pp. 28–29, 34 |
| 037 | Testing / JUnit 4 | [Parameterised tests across algorithm implementations](037-junit-parameterised-algorithm-tests.md) | Question 1; reviewed 11 Nov 2026, quiz week not supplied; priority review | T, PDF pp. 32–34; supplied quiz feedback; JUnit 4 documentation |
| 038 | Persistent data | [Serialized, CSV, JSON and XML file sizes](038-persistent-format-size-comparison.md) | Question 2; reviewed 11 Nov 2026, quiz week not supplied | Supplied quiz feedback; PD background pp. 16–19, 21–30; Java serialization documentation |
| 039 | Testing / Coverage | [Coverage subsumption relationships](039-coverage-subsumption.md) | Question 1; reviewed 11 Nov 2026, quiz week not supplied | T, PDF pp. 58–64, 68–71; supplied quiz feedback |

Entry numbers follow the order in which questions are added. They are separate from the original quiz's question numbers. A topic's lecture week does not necessarily identify the quiz week.

## How to use this collection

1. Read the question and choose an answer before opening the solution.
2. Expand **Answer and explanation** to check your reasoning.
3. Review why each alternative is correct or incorrect.
4. Use the lecture reference or related note to revisit the concept.

Each entry records the question and available options, the correct answer, the reasoning, a common mistake and a short memory aid. Original quiz numbering is retained when available. Additional practice examples are labelled separately from the supplied question.

References use **PDF viewer pages, with the cover as page 1**, not necessarily the slide's printed number. See the [lecture source index](../notes/lecture-source-index.md) for exact filenames.

[Exam revision guide](../notes/final-exam-revision.md) · [Repository home](../README.md)
