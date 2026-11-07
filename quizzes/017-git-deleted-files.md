# Quiz 017: Finding deleted files in Git history

**Original label:** Question number not visible in the supplied screenshots; quiz week not supplied.  
**Topic:** File deletion history and commit inspection.  
[Back to quiz index](README.md)

## Question

For the following Git repository, what file was deleted from this Git repository?

Repository named in the question: https://gitlab.cecs.anu.edu.au/comp2100-2024-s2-marker/git-quiz-project

1. Git does not provide functionality to view the names of deleted files as a security measure.
2. Of all the files added in any commit to this repository, none of them were later deleted.
3. The file “CatPicture.png” was briefly included on the factorial-implementation branch.
4. The file “MyPasswords.txt” was added in a commit, but the commit itself was later deleted.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer: option 2.** The supplied quiz feedback confirms that none of the files added in the exercise's history were subsequently deleted.

This answer is grounded in the supplied feedback. The live GitLab repository could not be inspected during this review.

### What the history screenshot does and does not show

The screenshot displays commit messages about adding a skeleton, tests, an implementation, bug fixes, a comment, a merge and a README.

Those titles alone do **not** establish whether a file was deleted. A commit called “Fix bug” could also delete a file; commit messages are human-written summaries.

**Check the file changes inside commits, not just their titles.**

### Inspecting this in an IDE

1. Select a commit in the Git history.
2. Inspect its changed-file list and diff.
3. Look for a file marked as deleted.
4. Check the relevant branch histories, including changes associated with merges.

Deleting lines inside a file is different from deleting the entire file.

### Command-line check

From inside a local clone:

```bash
git log --all --diff-merges=separate --diff-filter=D --name-status
```

| Part | Meaning |
| --- | --- |
| `git log` | Inspect commit history. |
| `--all` | Traverse history reachable from locally available refs and HEAD. |
| `--diff-merges=separate` | Compare merge commits against each parent. |
| `--diff-filter=D` | Select file deletions. |
| `--name-status` | Show changed filenames with status letters. |

If a deletion is found, a changed-file entry might look like:

```text
D    old-notes.txt
```

This is an illustrative example, not output from the quiz repository.

No deletion entries means none were found within the available history examined. It cannot establish what happened in unavailable history, such as commits omitted from a shallow clone or no longer reachable from the available refs.

These flags are documented in [Git's log manual](https://git-scm.com/docs/git-log).

### Common status letters

| Letter | Meaning |
| --- | --- |
| A | Added |
| M | Modified |
| D | Deleted |
| R | Renamed |

### Why the other options are wrong

| Option | Explanation |
| --- | --- |
| 1: Git hides deleted filenames | False. Git records file changes, including deletions; deletion does not normally erase the earlier committed version. |
| 3: CatPicture.png | Not the answer according to the supplied quiz feedback. A plausible filename is not evidence that it was added or deleted. |
| 4: MyPasswords.txt and a deleted commit | Not the answer according to the supplied feedback. File deletion and removing a commit from visible history are different operations. |

History can be rewritten so a commit is no longer reachable from branch tips. That is separate from committing the deletion of a file, and the screenshot does not demonstrate such a rewrite.

### Memory aid

**A = added; M = modified; D = deleted.**  
**To prove deletion, inspect the diff—not just the commit message.**

</details>

Related: [Reading a commit hash](004-git-history-short-hash.md) · [Merge direction](014-git-merge-direction.md).
