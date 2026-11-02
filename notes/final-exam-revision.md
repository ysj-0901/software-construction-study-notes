# Final Exam Revision Guide — COMP6442

This guide prioritises topics for revision and helps locate the relevant explanations during practice. It does not predict examination questions or marks.

**Source references use PDF viewer pages, with the cover as page 1.** See the [exact filenames and page index](lecture-source-index.md).

## Revision order

| Priority | Topic and notes | Be able to do |
| ---: | --- | --- |
| 1 | [UML and design patterns](week-02-uml-and-design-patterns.md) | Translate Java relationships into UML; read multiplicity; select and justify a pattern; identify participants and delegation/inheritance. |
| 2 | [BST, AVL and red-black trees](week-03-data-structures.md), [RB insertion examples](week-03-red-black-insertion.md), [B-trees](week-07-refactoring-and-b-trees.md) | Trace search/insert/delete, choose rotations/recolouring, enforce node capacity and verify invariants. |
| 3 | [Persistent data](week-04-persistent-data.md) | Choose a format; map fields to a saved representation; implement and explain a save/load round trip. |
| 4 | [Testing](week-05-software-testing.md) | Design normal/boundary/invalid tests; write JUnit assertions; distinguish coverage criteria and count feasible paths. |
| 5 | [Tokenisers and parsers](tokenisers-and-parsers.md) | Separate lexical and grammatical checks; apply precedence; trace recursive descent and token consumption. |
| 6 | [Refactoring](week-07-refactoring-and-b-trees.md) | Match a smell to a small behaviour-preserving change; explain SOLID using concrete dependencies. |
| 7 | [Android](week-06-android.md) | Explain components, intents, lifecycle transitions, UI callbacks and permission checks. |
| 8 | [Contracts](week-08-design-by-contract.md), [performance](week-08-performance.md), [IP and ethics](professional-ethics-and-intellectual-property.md) | Apply pre/postconditions, Amdahl's law and stakeholder-based reasoning; distinguish forms of IP protection. |

## Quick response checklists

### UML and pattern questions

1. Identify the objects, responsibilities and the variation the design must support.
2. Choose the relationship: inheritance, interface realisation, association, dependency, aggregation or composition.
3. Put triangles towards the parent/interface and diamonds at the whole. Read multiplicity at each end separately. **[U, pp. 15–22]**
4. For a pattern, name the participants, explain one request through the design, then state the benefit and cost. A pattern name alone is not an explanation. **[U, pp. 43, 48–116]**

**Common distinctions:** Observer broadcasts to subscribers; State changes behaviour with state; Template Method fixes a sequence of overridable steps; Iterator hides traversal details. DAO abstracts data access; Façade simplifies subsystem access; Decorator wraps the same interface to add behaviour.

### Tree operation questions

1. Perform the underlying BST or sorted-node operation.
2. Locate the first violated invariant.
3. Repair locally, then check whether the violation propagates upwards.
4. Check the root, parent/child links and global subtree ordering, not just immediate children.

| Structure | Final check | Source |
| --- | --- | --- |
| BST | Inorder traversal matches the sorted keys; duplicate policy is consistent. | D, pp. 15–35, 71–78 |
| AVL | Every node has balance factor in `{-1, 0, +1}`; stored heights agree. | D, pp. 113–131 |
| Red-black | Black root/NIL; no red-red edge; equal black counts to NIL. | D, pp. 80, 89, 94–106 |
| B-tree | Key limits, `children = keys + 1` for internal nodes, separator ranges, equal leaf depth. | R, pp. 62–80 |

For an order-3 B-tree, an internal node with one key has two children; with two keys it has three. Borrowing moves a separator **through the parent**. An AVL/RB rotation preserves inorder ordering. A B-tree split promotes a separator and may increase height at the root.

### Persistence questions: structure the work before coding

1. List the fields and identify which are persisted, derived or temporary.
2. Choose the representation: Java object stream, XML elements/attributes, JSON objects/arrays, or relational tables.
3. Sketch one saved object, including nested collections and optional values.
4. Implement writing and reading as corresponding operations; reconstruct the required object state.
5. Check resource closure, conversions, missing/malformed input and a round trip. **[PD, pp. 6–19, 21–30]**

For timed practice, take short conceptual subquestions when available, then allocate a defined block to a lengthy implementation. Reserve time in proportion to the marks rather than leaving it only whatever time remains. This is a study strategy, not a course assessment rule.

### Testing and parsing questions

- **Coverage:** mark which statements, decision outcomes and atomic conditions actually execute. A short-circuited operand contributes no observed outcome. Covering each statement does not imply both outcomes of each decision. **[T, pp. 51–71]**
- **Test quality:** an executed line without a useful assertion may reveal little. Include boundaries and invalid inputs as well as ordinary success cases. **[T, pp. 18–38]**
- **Parsing:** separate characters → tokens → grammatical structure → meaning. A string that contains valid tokens can still be grammatically invalid. Check that a recursive parser consumes tokens and eventually consumes the complete input. **[P, pp. 22–39, 42–69]**

## Final self-check

- Can I explain why I selected a particular pattern or repair operation?
- Can I trace one small example without relying on memorised prose?
- Can I find the corresponding PDF page quickly?
- Have I distinguished lecture content from an illustrative implementation detail?

[Repository home](../README.md)
