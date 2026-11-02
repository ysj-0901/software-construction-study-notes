# Lecture Source Index — COMP2100/6442, Semester 2, 2026

This index identifies the 12 lecture PDFs used for the November revision update. References such as **[U, pp. 53–58]** mean pages 53–58 in the PDF identified by **U** below.

**Use the PDF reader's page counter, with the cover as page 1. Printed slide numbers sometimes differ.** The original PDFs remain in the course materials; this repository contains study summaries and independently written examples.

## Files and navigation

| Key | Exact uploaded filename | PDF pages | Main note |
| --- | --- | ---: | --- |
| U | `week02-uml-dp(6).pdf` | 120 | [UML and design patterns](week-02-uml-and-design-patterns.md) |
| D | `week03-data-structures(3).pdf` | 133 | [Data structures](week-03-data-structures.md) |
| PD | `Week 04 - Persistent Data - COMP2100_6442 - Semester 2 2026 (1).pdf` | 34 | [Persistent data](week-04-persistent-data.md) |
| T | `week05-testing(2).pdf` | 83 | [Software testing](week-05-software-testing.md) |
| P | `lecture_parsing - comp21006442 - s2 - 2026(1).pdf` | 75 | [Tokenisers and parsers](tokenisers-and-parsers.md) |
| R | `Week 07 - B-trees Refactoring - COMP2100_6442 - Semester 2 2026 (2)(4).pdf` | 81 | [Refactoring and B-trees](week-07-refactoring-and-b-trees.md) |
| A | `week06-android(1).pdf` | 127 | [Android](week-06-android.md) |
| C | `Week 08 - Design by Contract - COMP2100_6442 - Semester 2 2026-1(1).pdf` | 46 | [Design by Contract](week-08-design-by-contract.md) |
| F | `Week 08 - Performance - COMP2100_6442 - Semester 2 2026-1(1).pdf` | 15 | [Performance](week-08-performance.md) |
| IP | `Intellectual Property - Software Construction - S2 - 2026.pdf` | 30 | [Professional ethics and intellectual property](professional-ethics-and-intellectual-property.md) |
| PAT | `ANU presentation_ 12 Oct 2026.pdf` | 22 | [Professional ethics and intellectual property](professional-ethics-and-intellectual-property.md) |
| E | `ACS Professional Ethics v2.0.pdf` | 22 | [Professional ethics and intellectual property](professional-ethics-and-intellectual-property.md) |

## Fast page lookup

| Topic | PDF reference |
| --- | --- |
| Class notation, visibility, relationships, multiplicity | U, pp. 9–22 |
| Polymorphism and overriding | U, pp. 30–34 |
| Factory Method; Singleton | U, pp. 53–58; 59–61 |
| Observer; State | U, pp. 63–68; 69–77 |
| Template Method; Iterator | U, pp. 78–86; 87–93 |
| DAO; Façade; Decorator | U, pp. 95–104; 105–109; 110–116 |
| BST ordering/traversals; search/insertion | D, pp. 15–38 |
| BST successor/predecessor; deletion | D, pp. 56–57, 71; 71–75 |
| Red-black invariants, insertion and height | D, pp. 80, 89, 94–106 |
| AVL balance and rotations | D, pp. 113–131 |
| B-tree order, occupancy and height | R, pp. 60–67 |
| B-tree search/insertion; deletion | R, pp. 68–73; 75–80 |
| Persistence choices/relational storage | PD, pp. 6–14 |
| Java serialisation; XML/DOM/SAX; JSON | PD, pp. 16–19; 21–27; 28–30 |
| JUnit 4; test selection | T, pp. 18–38 |
| Integration; black-/white-box testing | T, p. 42; pp. 43–48 |
| Statement; branch; condition; course multiple-condition; path coverage | T, pp. 51–57; 58–61; 62–64; 65–67; 68–71 |
| Tokenisation; numeric scanner | P, pp. 22–32; 37–39 |
| Grammar/ambiguity; precedence/recursive descent | P, pp. 42–58; 59–69 |
| Refactoring; SOLID; DRY/KISS; smells | R, pp. 6–12; 14–41; 42–43; 44–57 |
| Android components/intents/lifecycle | A, pp. 4–10 |
| Android UI; navigation; configuration; permissions | A, pp. 12–24; 60–87; 93–103; 104–125 |
| Contracts; JML | C, pp. 13–19, 32; 23, 28–29, 32, 34–36, 40 |
| Profiling and Amdahl's law | F, pp. 4–11 |
| Copyright, trademarks and licences | IP, pp. 4, 7, 9, 13 |
| Patent requirements, claims and computing inventions | PAT, pp. 6–7, 11–13, 18–21 |
| ACS values and case studies | E, pp. 5–6, 9–13 |

## Reading the notes accurately

- **Factory terminology:** U labels its conditional `NotificationFactory` example “Factory Method”; the canonical subclass-based formulation and the concrete lecture implementation are distinguished in the notes.
- **B-tree order:** R uses order *m* as the **maximum number of children**. Use the minimum-key invariant on R, pp. 64, 66 when reasoning about underflow; do not treat *m* as the maximum number of keys.
- **Red-black scope:** D, p. 104 explicitly says deletion is not covered in class. The detailed supplement focuses on insertion.
- **Testing:** the notes account for Java short-circuit evaluation and distinguish the lecture's “multiple condition” convention from a full truth table.
- **Parsing:** the precedence grammar is right-recursive. Practical token-consumption and input-validation reminders complete the abbreviated slide pseudocode.
- **Source versus illustration:** added worked examples explain the lecture concepts; they are not claims about official examination questions or marking schemes.

[Return to the revision guide](final-exam-revision.md) · [Repository home](../README.md)
