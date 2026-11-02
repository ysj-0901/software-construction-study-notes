# Quiz 003 — Amdahl's Law and Overall Latency

- **Topic:** Performance
- **Original label:** Question 5 (quiz week not supplied)
- **Question source:** Quiz screenshot shared for revision
- **Reviewed:** 3 November 2026
- **Lecture source:** **F**, `Week 08 - Performance - COMP2100_6442 - Semester 2 2026-1(1).pdf`
- **Exact location:** PDF p. 4 gives the formula; p. 5 has the same type of question with 70% and a factor of 2. Count the cover as page 1.

## Question

Your program has one very slow procedure that consumes 80% of the total time. Next, you improve it by a factor of 5. What is the performance gain in the overall latency?

- A. 2.777778
- B. 3.777778
- C. 1.777779
- D. 0.44444445
- E. Not possible to know without the performance of the other procedures.

Letter labels are added for reference; the option order matches the screenshot.

<details>
<summary><strong>Answer and explanation</strong></summary>

## Correct answer: A — 2.777778

The whole program is approximately **2.78 times as fast**. Its new runtime is **36% of its original runtime**, a **64% reduction in latency**.

Here, "performance gain" means the overall speedup factor, as defined by $S$ on lecture PDF p. 4.

## 1. Use a 100-second example

Assume the original program takes 100 seconds. This convenient choice does not change the ratio.

| Portion | Original time | Change | New time |
| --- | ---: | --- | ---: |
| Slow procedure | 80 s | Five times as fast | 80 / 5 = 16 s |
| All other procedures combined | 20 s | Unchanged | 20 s |
| **Whole program** | **100 s** | | **36 s** |

The improved procedure takes one-fifth as long. Only that portion changes:

$$
T_{\text{new}}=20+\frac{80}{5}=36\text{ s}.
$$

Then divide **old total time by new total time**:

$$
S=\frac{T_{\text{old}}}{T_{\text{new}}}
=\frac{100}{36}
\approx \boxed{2.777778}.
$$

## 2. Apply the lecture formula

Amdahl's law on **F, PDF p. 4** is:

$$
S=\frac{1}{(1-\alpha)+\alpha/k}.
$$

| Symbol | Meaning | This question |
| --- | --- | ---: |
| $\alpha$ | Fraction of original total runtime occupied by the improved part | 0.8 |
| $k$ | Speedup factor of that part | 5 |
| $1-\alpha$ | Unchanged fraction of original runtime | 0.2 |

Therefore:

$$
S=\frac{1}{(1-0.8)+0.8/5}
=\frac{1}{0.2+0.16}
=\frac{1}{0.36}
\approx 2.777778.
$$

The denominator is the **new runtime as a fraction of the old runtime**, not the speedup itself.

## 3. Why the other procedures' individual times are unnecessary

If the improved procedure originally accounts for **80% of the total**, everything else together accounts for **20%**. That is enough information.

We do not need to know how the remaining 20% is divided between procedures, because none of those times changes in this model. Nor do we need the absolute original runtime: it cancels in the ratio.

For any original total time $T$:

$$
\frac{T}{0.2T+0.8T/5}
=\frac{1}{0.36}.
$$

This assumes the same workload, only the stated procedure is improved, and other execution time remains unchanged, as in the lecture's Amdahl model.

## 4. Check the options and the quantity being asked for

| Option | Assessment |
| --- | --- |
| A. 2.777778 | Correct: old total runtime / new total runtime. |
| B. 3.777778 | Does not equal the speedup; there is no additional +1 after taking old/new. |
| C. 1.777779 | Approximately speedup minus 1, not the speedup factor requested here. |
| D. 0.44444445 | Does not equal the speedup or the new runtime fraction, which is 0.36. |
| E. Insufficient information | Incorrect: the unchanged procedures collectively account for the known remaining 20%. |

Keep these measures distinct:

- **Overall speedup:** $100/36\approx2.78\times$.
- **Remaining runtime:** $36/100=36\%$.
- **Latency reduction:** $(100-36)/100=64\%$.

A useful sanity check: improving only part of the program by 5× cannot make this whole program 5× faster. Even making the 80% portion instantaneous would leave the other 20%, giving a maximum speedup of $1/0.2=5$. With a finite 5× improvement, the actual speedup is below that ceiling.

### Memory aid

**Keep the unchanged time, divide the improved time, add them, then calculate old / new.**

</details>

## Related revision

- [Performance and Amdahl's law](../notes/week-08-performance.md)
- [Exact lecture filenames and page index](../notes/lecture-source-index.md)

[Back to quiz index](README.md)
