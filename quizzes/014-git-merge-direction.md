# Quiz 014: Git merge direction and a diverged history

**Original label:** Question 3; quiz week not supplied.  
**Topic:** Reading a commit graph and identifying a plausible merge operation.  
[Back to quiz index](README.md)

## Question

For the linked Git repository, one git commit merges two branches together. Which explanation as to how the developer made this commit is the most plausible?

The repository hyperlink is partially cropped in the supplied screenshot; no URL is reconstructed here.

1. They checked out the main branch, ran `git merge factorial-implementation`, and selected which changes should be included.
2. They checked out the factorial-implementation branch, and ran `git merge main`. Because no changes had been made to the main branch since factorial-implementation was created, the merge was performed automatically.
3. They checked out the main branch, and ran `git merge factorial-implementation`. Because no changes had been made to the main branch since factorial-implementation was created, the merge was performed automatically.
4. They checked out the factorial-implementation branch, ran `git merge main`, and selected which changes should be included.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct according to the supplied quiz feedback: option 1.** Full credit is shown.

### Evidence in the supplied graph

Read the history from bottom to top:

- The paths diverge after **Add @Test annotation to all test cases**.
- The feature path contains **Add Preliminary Factorial Implementation** and two bug-fix commits.
- The mainline also advances: **Add comment to factorial constructor**.
- The paths join at **Merge remote-tracking branch 'origin/factorial-implementation'**.
- The mainline then continues to **Add README markdown to explain program**.

The screenshot labels the mainline **master**, whereas the answer options call it **main**. Use the branch name that actually exists. The feature tip remains marked on the purple path.

### Eliminate the alternatives

| Option | Verdict | Reason |
| --- | --- | --- |
| 1 | Quiz answer | Feature → mainline matches the displayed history. The wording about selecting changes is discussed below. |
| 2 | Incorrect | Reverses the intended direction; also claims the mainline did not advance, contradicting the constructor-comment commit. |
| 3 | Incorrect | Direction matches, but its “no changes to main” explanation contradicts the graph. |
| 4 | Incorrect | Describes mainline → feature, the opposite direction to the quiz's intended interpretation. |

### The command rule

**Check out the destination; merge the source.**

Using the quiz's names:

```bash
git checkout main
git merge factorial-implementation
```

A plausible command sequence matching the screenshot's names is:

```bash
git checkout master
git merge origin/factorial-implementation
```

The second sequence assumes the remote-tracking ref is already available. A commit graph does not prove the exact commands typed.

### Keep these cases separate

| Relationship before merging | Ordinary result |
| --- | --- |
| Current tip is an ancestor of source | Fast-forward: move the branch pointer; no extra merge commit. |
| Source is already an ancestor of current tip | Already up to date; no new commit. |
| Each side has commits absent from the other | A completed ordinary merge joins the histories with a merge commit. |

Fast-forward behaviour can be overridden with `--no-ff`.

**A merge commit does not prove a conflict occurred.** Git can combine diverged histories automatically when changes do not conflict. Manual resolution is needed for unresolved conflicts. Therefore, option 1 is the recorded answer, but its “selected which changes should be included” clause is **not independently established by the screenshot**.

### Memory aid

**Stay on the receiving branch; merge the incoming branch.**  
**Both sides advanced ≠ necessarily conflicted.**

</details>

## Sources

- Supplied Question 3 screenshots: question, four options and confirmed answer.
- Supplied IDE history screenshot: branch labels, commit messages and graph.
- [Official Git merge documentation](https://git-scm.com/docs/git-merge): merge direction, fast-forward, already-up-to-date and true-merge behaviour.

Related: [Commit hash from a history graph](004-git-history-short-hash.md) · [What a branch represents](010-git-branch-pointer.md) · [HEAD, branches and commits](011-git-head-branch-and-commit.md).
