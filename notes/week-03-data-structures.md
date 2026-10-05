> **Image availability:** The original Obsidian notes reference diagrams whose image files were not supplied. Their positions are marked below.

# Week 3 — Data Structures


---
# 1. Data Structures

# 2. Binary Search Tree 
## Predecessor and Successor

> [!summary] Core idea

> In a **Binary Search Tree (BST)**:

> - **Predecessor of `x`** = the node with the **largest key smaller than `x`**

> - **Successor of `x`** = the node with the **smallest key greater than `x`**


> Another way to remember it:

> - **Predecessor = previous value in sorted order**

> - **Successor = next value in sorted order**
---
### Successor
The **successor** of `x` is the smallest key greater than `x`.

**Algorithm:**
1. If `x` has a **right subtree**:
   - go **right once**
   - then go **left as far as possible**
2. If `x` has **no right subtree**:
   - move **up** while `x` is a right child
   - the first parent where `x` comes from the left is the successor
3. The maximum node has no successor (`NIL`).

### Predecessor
The **predecessor** of `x` is the largest key smaller than `x`.

**Algorithm:**
1. If `x` has a **left subtree**:
   - go **left once**
   - then go **right as far as possible**
2. If `x` has **no left subtree**:
   - move **up** while `x` is a left child
   - the first parent where `x` comes from the right is the predecessor
3. The minimum node has no predecessor (`NIL`).

### Memory Trick

- **Successor:** Right → Left, Left, Left...
- **Predecessor:** Left → Right, Right, Right...

---
## Deletion in a BST

There are **3 cases** when deleting a node `x`:

### Case 0 — No children
- `x` is a leaf node.
- Simply **remove `x`**.

### Case 1 — One child
- Remove `x`.
- Connect `x`'s **parent directly to x's child**.

```text
parent → x → child

becomes

parent → child
```
### Case 2 — Two children

1. Find `x`'s **successor**.
2. Replace `x` with its successor.
3. Delete the successor from its original subtree.
    - This deletion will become **Case 0 or Case 1**.


The key one to remember is **Case 2**:

```text
delete x
   ↓
find successor
   ↓
replace x with successor
   ↓
delete old successor
```
---
# 3. Red-Black Tree
## Balanced Tree

A **balanced search tree** is a Binary Search Tree (BST) whose height is guaranteed to be `O(log n)` for `n` nodes.

- Height = maximum number of edges from the root to a node.
- Keeping the tree balanced prevents it from becoming a long chain.
- This allows operations such as search, insertion, and deletion to remain efficient.

**Examples:**
- AVL Tree: strictly height-balanced
- Red-Black Tree: close to balanced tree

## Red-Black Tree Properties:
1. Every node is RED or BLACK.

2. Root + NIL leaves are BLACK.

3. RED cannot have RED children.

4. Every path to NIL has the same number of BLACK nodes.

## Insertion and Rotation: See Slides

No consecutive reds
        ↓
At least half a path is black
        ↓
bh ≥ h/2
        ↓
A tree with black-height bh has ≥ 2^bh - 1 nodes
        ↓
h ≤ 2log₂(n + 1)
        ↓
Red-Black Tree height = O(log n)

## Red-Black Tree: Key Takeaway

- A Red-Black Tree has height **`O(log n)`**.
- Therefore, the following operations take **`O(log n)` worst-case time**:
  - `Minimum()`
  - `Maximum()`
  - `Successor()`
  - `Predecessor()`
  - `Search()`

- `Insert()` and `Delete()` also take **`O(log n)`** time.
  - However, they require **recolouring and/or rotations** because they modify the tree and must preserve Red-Black properties.

### Essence
A normal BST may be unbalanced, while a Red-Black Tree is a **close-to-balanced binary search tree with extra colour rules**.

==Red-Black Trees guarantee `O(log n)` height, so major BST operations remain `O(log n)` in the worst case.==

# 4. AVL Tree
- a stricter kind of balanced BST than a Red-Black Tree.
- **AVL Tree** = a **self-balancing Binary Search Tree (BST)**. 
- Named after **Adelson-Velskii and Landis**. 
- For every node, the heights of the left and right subtrees can differ by **at most 1**. 
- 
- ### Balance Factor 
- The **balance factor** of a node is: $$ bf(n) = h(n_{left}) - h(n_{right}) $$ where: 
- $h(n_{left})$ = height of the left subtree 
- $h(n_{right})$ = height of the right subtree 
- For a valid AVL Tree: $$ bf(n) \in \{-1, 0, 1\} $$ Meaning: - `bf = 1` → left subtree is 1 level taller - `bf = 0` → both subtrees have the same height - `bf = -1` → right subtree is 1 level taller 
- 
- If: $$ |bf(n)| > 1 $$ then the **AVL property is violated**, and the tree needs to be **rebalanced**.
- 

> *Diagram not included in this upload: `Pasted image 20260818004615.png`.*


- 

> *Diagram not included in this upload: `Pasted image 20260818004716.png`.*


> *Diagram not included in this upload: `Pasted image 20260818004637.png`.*


> *Diagram not included in this upload: `Pasted image 20260818012635.png`.*


## AVL Tree Height

Let `N(h)` be the **minimum number of nodes** in an AVL tree of height `h`.

Because an AVL tree must remain balanced, the smallest AVL tree of height `h` has:

- one subtree of height `h - 1`
- the other subtree of height `h - 2`

Therefore:

$$
N(h) = N(h-1) + N(h-2) + 1
$$
=> 
$$
N(h) + 1 = N(h-1) + 1 + N(h-2) + 1
$$
This recurrence is similar to the **Fibonacci sequence**, so the minimum number of nodes grows exponentially with height.

As a result:

$$
h \approx 1.44\log_2(n)
$$

### Key Takeaway

- AVL tree height is **O(log n)**.
- Therefore, search, insertion, and deletion can remain **O(log n)**.
- AVL trees are more strictly balanced than red-black trees.
- Approximate height bounds:
  - AVL: $1.44\log_2(n)$
  - Red-black tree: up to $2\log_2(n)$
- So AVL trees can provide **slightly faster lookup**, at the cost of potentially more rebalancing.

> **Why `h-1` and `h-2`?**  
> To minimise the number of nodes while still having height `h`, we make the two subtrees differ by the maximum AVL-allowed amount: **1 level**.
