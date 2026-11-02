# Week 3 — Data Structures

Source: **D = `week03-data-structures(3).pdf`**. References such as **D17** mean **PDF page 17, counting the cover as page 1**. See the [lecture source index](lecture-source-index.md). Supplemental examples and implementation reminders below are labelled; they are not copied assessment solutions.

## 1. Data structures and tree vocabulary

Choose a data structure for the operations and constraints of the problem, not just the programming language. The lecture contrasts linear structures (elements in sequence) with non-linear structures (branching relationships). A tree has no cycles. A root has no parent; siblings share a parent; a leaf has no real children; a subtree contains a node and its descendants. **Height** is the maximum number of edges from a node to a descendant leaf. [D7–16,78]

| Structure | Ordering / balance | Search, insert, delete |
| --- | --- | --- |
| Ordinary BST | Ordered subtrees; no balance guarantee | `O(h)`; worst case `O(n)` |
| AVL | BST + every node's subtree heights differ by at most 1 | `O(log n)` worst case |
| Red-black | BST + colour and black-height constraints | `O(log n)` worst case |
| B-tree | Balanced multiway search tree; all leaves at one depth | `O(log n)` for fixed order; see [Week 7](week-07-refactoring-and-b-trees.md) |

The first three rows follow D77–78,105–106,113–115,127–131. The B-tree comparison is a cross-lecture summary of Week 7, PDF pp60–80. Traversing all `n` nodes takes `O(n)` regardless of whether the tree is balanced.

## 2. Binary Search Tree (BST)

### Ordering, search and insertion

For **every node** `x`, every key in its left subtree is at most `x.key`, and every key in its right subtree is at least `x.key`. This condition concerns entire subtrees, not just immediate children. The formal slide allows equal values; use the duplicate policy specified in a question or implementation. [D15–17]

- **Search:** compare the target with the current key; go left for smaller, right for larger; stop on a match or null.
- **Minimum / maximum:** follow left / right children until no further child exists.
- **Insert:** follow the search path and attach at the first appropriate null child. A new node starts as a leaf. [D36–38]
- A sorted insertion sequence can create a chain: `h=n−1`. An ordinary BST does not repair this automatically. [D77–78]

### Traversals

| Traversal | Order | Useful interpretation |
| --- | --- | --- |
| Inorder | Left, Node, Right | Keys in sorted order in a BST |
| Preorder | Node, Left, Right | Process parent before descendants |
| Postorder | Left, Right, Node | Process descendants before parent |

[D18–35]

**Supplemental example:** root `20`, left child `10`, right child `30` gives inorder `10,20,30`, preorder `20,10,30`, postorder `10,30,20`.

### Predecessor and successor

For distinct keys, **predecessor** is the largest key smaller than `x`, and **successor** is the smallest key greater than `x`: the previous/next value in sorted order. With duplicates, distinguish the next *node* in inorder from the next *strictly greater key*. [D56–57,71; duplicate clarification]

| Operation | Subtree exists | Subtree absent |
| --- | --- | --- |
| Successor | Go right once, then left as far as possible | Climb while coming from a right child; first parent reached from its left child is the successor |
| Predecessor | Go left once, then right as far as possible | Climb while coming from a left child; first parent reached from its right child is the predecessor |

The maximum has no successor and the minimum has no predecessor: return `NIL`/`null`.

**Memory trick:** successor = **right, then left**; predecessor = **left, then right**.

### Deletion: three cases

| Children of `x` | Action |
| --- | --- |
| 0 | Unlink the leaf |
| 1 | Connect `x`'s parent directly to `x`'s child |
| 2 | Replace `x` with its successor; delete that successor from its old position in the right subtree |

The successor has no left child, so deleting it reduces to the 0- or 1-child case. The lecture uses the successor; using the predecessor symmetrically is also valid when implemented consistently. Update the root if deleting the root, and keep parent links consistent. For key–value nodes, replacing a key requires preserving its associated value. [D72–75; implementation reminders]

**Supplemental example:** deleting root `20` with children `10` and `30` gives root `30` with left child `10`; the original `30` node is removed from its former position.

## 3. Rotations: the shared mechanism

A rotation changes local structure while preserving **inorder key order**. It takes `O(1)` pointer changes; it does not traverse a whole subtree or swap all its keys. [D94–95,116–126]

**Supplemental structural pseudocode** (parent pointers and height maintenance omitted here):

```text
rightRotate(y):
    x = y.left
    beta = x.right
    y.left = beta
    x.right = y
    return x                 // new subtree root

leftRotate(x):
    y = x.right
    beta = y.left
    x.right = beta
    y.left = x
    return y                 // new subtree root
```

Reconnect the returned subtree root to the old parent, or make it the tree root. Update `beta.parent` if present and the moved nodes' parents. For AVL, recompute the lower node's height **before** its new parent's height. Rotation alone does not select colours for a red-black tree.

For full diagrams showing why `beta` moves, see [rotation mechanics in the red-black supplement](week-03-red-black-insertion.md#3-what-does-a-rotation-actually-do).

## 4. Red-Black Tree

### Properties

A red-black tree is a BST with the following constraints:

1. Every node is red or black.
2. The root and all **NIL leaves** are black.
3. A red node has black children: no consecutive red nodes on a path.
4. From **any given node**, every path to a descendant NIL contains the same number of black nodes.

A real node with no real children can be red; its two missing children are black NIL leaves. Equal black-height does not require equal path length. [D79–81]

### Insertion and rotation

See [Red-Black Tree Insertion: Worked Diagrams, Java and Exercises](week-03-red-black-insertion.md) for preserved worked examples and a runnable teaching implementation.

Insert as in a BST and colour the new node red. Let `Z` be the node being checked, `P` its parent, `G` its grandparent, and `U` the uncle (`P`'s sibling). [D89,98–103]

| Situation | Repair |
| --- | --- |
| `Z` is root | Make it black |
| `P` is black | No red–red conflict; finish |
| `P` and `U` are red | Make `P,U` black and `G` red; set `Z=G` and continue upwards |
| `P` red, `U` black/NIL, outer LL | Make `P` black and `G` red; right-rotate `G` |
| `P` red, `U` black/NIL, outer RR | Make `P` black and `G` red; left-rotate `G` |
| `P` red, `U` black/NIL, inner LR | Left-rotate original `P`, reidentify node roles, then apply the LL repair |
| `P` red, `U` black/NIL, inner RL | Right-rotate original `P`, reidentify node roles, then apply the RR repair |

**Check uncle colour before choosing a rotation.** Finish with a black root. Recolouring can propagate; after an inner rotation, the old parent is not the parent used for the final recolouring. [D99–103]

### Black-height and complexity

`bh(x)` counts black nodes on a path **below `x`**, including the terminal NIL but excluding `x`; `bh(NIL)=0`. [D105]

- No consecutive red nodes implies at least half the height is black: `bh ≥ h/2`.
- A subtree of black-height `bh` has at least `2^bh − 1` real nodes.
- Hence `h ≤ 2 log₂(n+1)` and height is `O(log n)`.

Search, minimum, maximum, successor, predecessor, insertion and deletion are `O(log n)` worst case. Insertion/deletion must preserve colour constraints as well as BST ordering. **The lecture explicitly does not cover the red-black deletion algorithm**; ordinary BST deletion alone is insufficient to preserve black-height. [D104–106]

## 5. AVL Tree

An AVL tree is a BST in which, at **every node**, left and right subtree heights differ by at most one. It is more strictly height-balanced than a red-black tree. [D113–115]

### Balance factor and height convention

$$
BF(v)=h(v.left)-h(v.right)\in\{-1,0,1\}.
$$

- `+1`: left subtree is one level taller.
- `0`: equal height.
- `−1`: right subtree is one level taller.
- `+2` or `−2` after an update: rebalance that node.

For the implementation reminders below, use **`h(null)=−1`, `h(leaf)=0`**, so `h(v)=1+max(h(left),h(right))`. Other consistent conventions give the same balance factors. This explicit null convention is a supplement to the lecture's edge-based height definition.

### Choose the repair

At the first unbalanced node `z`, inspect the balance factor of its heavy child:

| `BF(z)` | Heavy child's BF | Case | Repair |
| --- | --- | --- | --- |
| `+2` | `BF(z.left) ≥ 0` | LL | `rightRotate(z)` |
| `+2` | `BF(z.left) < 0` | LR | `z.left = leftRotate(z.left)`, then `rightRotate(z)` |
| `−2` | `BF(z.right) ≤ 0` | RR | `leftRotate(z)` |
| `−2` | `BF(z.right) > 0` | RL | `z.right = rightRotate(z.right)`, then `leftRotate(z)` |

The rotation directions follow D116–127. The child-BF formulation is an implementation clarification that also handles deletion, including the **child BF = 0** cases.

**Supplemental insertion examples:** `30,20,10` needs LL; `10,20,30` needs RR; `30,10,20` needs LR; `10,30,20` needs RL. Each finishes with root `20`, children `10` and `30`.

### Insertion procedure

1. Insert the new leaf using BST ordering.
2. Walk back towards the root (or return through recursive calls).
3. Update each ancestor's height and balance factor.
4. Repair the **first** unbalanced ancestor using the table.
5. Reconnect the new subtree root. After this repair, no further rotation is required for that insertion; keep stored metadata consistent.

[D127]

### Deletion procedure

1. Perform ordinary BST deletion: unlink a leaf, splice one child, or replace a two-child node by its successor/predecessor and remove that replacement from its former position.
2. Starting above the physically removed node, update heights and balance factors towards the root.
3. Use the same repair table whenever `|BF|>1`.
4. Continue upwards: unlike insertion, one repair may leave a higher ancestor unbalanced. Up to `O(log n)` rotations can be needed.

[D128–129]

**Supplemental deletion example:** root `4` has left subtree `2(1,3)` and right leaf `5`. Deleting `5` gives `BF(4)=+2` and `BF(2)=0`; right-rotate `4`. The result has root `2`, left child `1`, and right subtree `4(3,null)`. This is why the LL condition is `BF(left) ≥ 0`, not only `>0`.

### Retained study diagrams

The following diagrams were already in these notes; they are retained as study aids alongside the now-explicit rotation rules. For the source lecture figures, consult D116–126.

![Retained AVL study diagram 1](../image/Pasted%20image%2020260818004615.png)

![Retained AVL study diagram 2](../image/Pasted%20image%2020260818004716.png)

![Retained AVL study diagram 3](../image/Pasted%20image%2020260818004637.png)

![Retained AVL study diagram 4](../image/Pasted%20image%2020260818012635.png)

### Why AVL height is logarithmic

Let `N(h)` be the minimum number of nodes at height `h`. To minimise size while retaining height `h`, use subtree heights `h−1` and `h−2`, which differ by exactly one:

$$
N(h)=1+N(h-1)+N(h-2).
$$

With the convention above, `N(−1)=0` and `N(0)=1`. This Fibonacci-like growth makes height logarithmic. The lecture gives an approximate worst-case growth of `1.44 log₂ n`, compared with roughly `2 log₂ n` for red-black trees. These are approximate comparisons; the exact red-black bound is `2 log₂(n+1)`. [D105,131]

AVL search, insertion and deletion are `O(log n)`. Stricter balance can improve lookup depth, while updates can require more rebalancing; it does not guarantee that every concrete AVL implementation is faster than every red-black implementation.

## 6. Exam checks

- [ ] Check ordering over whole subtrees, not just parent–child pairs.
- [ ] State the duplicate-key and height conventions.
- [ ] Identify successor/predecessor correctly when a subtree is absent.
- [ ] Handle root replacement and parent pointers during deletion/rotation.
- [ ] In AVL, check **every** node and use the correct BF sign.
- [ ] Distinguish AVL insertion's first repair from deletion's possible cascading repairs.
- [ ] In red-black insertion, inspect uncle colour before selecting LL/RR/LR/RL.
- [ ] Count NIL as black; do not confuse real leaf nodes with NIL leaves.
- [ ] After a rotation, verify inorder ordering and the tree-specific balance constraints separately.
