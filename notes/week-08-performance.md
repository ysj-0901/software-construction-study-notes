# Week 8 — Performance and Amdahl's Law

Source: **F**, `Week 08 - Performance - COMP2100_6442 - Semester 2 2026-1(1).pdf`. References use **1-based PDF viewer pages**, including the cover; printed slide labels differ near the end. See the [lecture source index](lecture-source-index.md).

## 1. Define what is being improved

**Latency** is elapsed time to finish one job. **Throughput** is jobs completed per unit time. Memory use, bandwidth and concurrency are additional performance dimensions; an improvement in one does not automatically establish improvement in all. Analyse best, average and worst cases where relevant. **[F pp. 3, 10]**

Amdahl's Law models a **fixed workload** when only part of its original execution time is improved. **[F pp. 2–6]**

## 2. Improving one component

Let:

- `T` = original total time;
- `α` = fraction of the **original runtime** spent in the improved part;
- `k` = speedup of that part;
- `S` = overall speedup.

Then:

$$
T_{new}=T(1-\alpha)+\frac{T\alpha}{k}
$$

$$
S=\frac{T}{T_{new}}=\frac{1}{(1-\alpha)+\alpha/k}.
$$

The unimproved portion remains unchanged. **[F pp. 4–6]**

### Worked calculation

The lecture's motivating program has sequential parts A and B, each taking 10 minutes; B becomes five times faster. **[F p. 3]**

| Quantity | Calculation | Result |
| --- | --- | --- |
| Original time | `10 + 10` | 20 min |
| New B time | `10 / 5` | 2 min |
| New total time | `10 + 2` | 12 min |
| Overall speedup | `20 / 12` | About 1.67× |
| Time reduction | `(20 − 12) / 20` | 40% |

A **5× component speedup** is therefore not a **5× program speedup**.

For the 70%-of-runtime procedure improved by 2×, `S = 1/(0.3 + 0.7/2) ≈ 1.54×`. **[F p. 5]**

## 3. Parallel execution and its ceiling

Let `P` be the parallelisable fraction and `N` the processor count. With ideal division of that part:

$$
S(N)=\frac{1}{(1-P)+P/N},\qquad
\lim_{N\to\infty} S(N)=\frac{1}{1-P}.
$$

The ceiling applies when `P < 1`. If 5% is sequential, the ideal limit is `1/0.05 = 20×`, regardless of processor count. **[F pp. 7–9]**

The model omits practical bottlenecks such as memory and I/O bandwidth; coordination overhead and imperfect workload division can also reduce actual speedup. **[F p. 8; latter points are explanatory model limitations]**

## 4. Profiling and common mistakes

Profiling reveals where runtime resources are spent so that optimisation targets an actual bottleneck. The lecture lists JConsole, VisualVM and Eclipse Trace Compass. **[F pp. 11–12]**

When solving a question:

1. Identify the original runtime fractions; fractions must add to one.
2. Divide the improved time by its local speedup.
3. Add the unchanged time.
4. Compute old time divided by new time.
5. Check the limit imposed by the unchanged portion.

Do not substitute a fraction of source lines for a runtime fraction. Likewise, an instruction-count fraction is a runtime fraction only under suitable execution-cost assumptions. Keep **speedup**, **new runtime**, and **percentage time reduction** distinct. These are calculation reminders for the model in **F pp. 3–9**.
