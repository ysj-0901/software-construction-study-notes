# Quiz 026: Centre a view on the edge of another view

**Original label:** Question 5. Quiz week not supplied.  
**Topic:** Android ConstraintLayout and horizontal anchors.  
[Back to quiz index](README.md)

## Question

In the Design view of a ConstraintLayout, element_2 has these constraints.

| Edge of element_2 | Target on element_1 | Margin |
| --- | --- | ---: |
| Start | End | 0 dp |
| End | End | 0 dp |
| Bottom | Top | 24 dp |

How is element_2 positioned horizontally with respect to element_1?

1. element_2's right side is in line with element_1's centre.
2. element_2's centre is in line with element_1's centre.
3. element_2's right side is in line with element_1's right side.
4. element_2's centre is in line with element_1's right side.

<details>
<summary><strong>Answer and explanation</strong></summary>

**Correct answer is option 4. The horizontal centre of element_2 aligns with the right edge of element_1.**

The quiz assumes a left-to-right layout, where Start means left and End means right, and a normally sized element_2 with non-zero width.

### Read the horizontal constraints together

Both horizontal constraints use the **same target**, the End of element_1. The zero margins leave that target at the edge itself. When the start and end of a sized view are constrained to the same horizontal position, ConstraintLayout centres the view on that position. The AndroidX solver's same-target centring equation places the two edges an equal distance from the target.

Suppose element_1's right edge is at x = 100 dp and element_2 is 40 dp wide.

| Position on element_2 | x coordinate |
| --- | ---: |
| Left edge | 80 dp |
| Centre | **100 dp** |
| Right edge | 120 dp |

Half of element_2's width lies on either side of the vertical line through element_1's right edge.

The **24 dp** margin belongs to the bottom-to-top constraint. It places element_2's bottom 24 dp above element_1's top and sets the vertical gap.

### Why the other answers differ

| Option | Reason |
| --- | --- |
| 1 | The common horizontal target is element_1's end, and element_2 aligns its centre to it. |
| 2 | The target is at the end of element_1. Its centre is a different position. |
| 3 | This describes the alignment the end-to-end constraint alone would give. The start-to-end constraint also contributes to the final position. |
| 4 | Both horizontal constraints target element_1's end, so the centre of element_2 aligns with that edge. |

</details>

## Sources and related notes

- User-supplied Question 5 screenshot and marked answer.
- [Week 6 Android notes](../notes/week-06-android.md). Android layout background in A, PDF pp. 15–16.
- [AndroidX ConstraintLayout documentation in source](https://github.com/androidx/constraintlayout/blob/main/constraintlayout/constraintlayout/src/main/java/androidx/constraintlayout/widget/ConstraintLayout.java). See the discussion of opposing constraints.
- [AndroidX centring solver](https://github.com/androidx/constraintlayout/blob/main/constraintlayout/core/src/main/java/androidx/constraintlayout/core/ArrayRow.java). See the same-target case in `createRowCentering`.
- [Back to quiz index](README.md)
