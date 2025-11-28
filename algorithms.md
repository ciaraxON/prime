# Algorithms

This documents each prime-generation algorithm implemented in the project: purpose, complexity, memory, pros/cons and example request.

## Implementations
- `src/main/java/com/example/prime/service/algorithm/TrialDivisionAlgorithm.java`
- `src/main/java/com/example/prime/service/algorithm/SieveOfEratosthenes.java`
- `src/main/java/com/example/prime/service/algorithm/ParallelAlgorithm.java`
- `src/main/java/com/example/prime/service/algorithm/SegmentedSieve.java`

---

### Trial Division
- Source: `src/main/java/com/example/prime/service/algorithm/TrialDivisionAlgorithm.java`
- Purpose: Simple per-number primality checks for small limits.
- Complexity: Time O(n * sqrt(n)), Space O(1) (plus output).
  - Time: O(n * sqrt(n)) (checking each number up to n using trial division). With the odd-only optimization it’s about 1/2 of that in practice.
  - Space: O(1) (ignoring output list) — constant extra memory.
- Pros: Simple, low memory, good for very small limits.
- Cons: Slow for large limits.
- Example request:
  - `GET /api/primes?limit=100&algorithm=TRIAL_DIVISION`

### Sieve of Eratosthenes
- Source: `src/main/java/com/example/prime/service/algorithm/SieveOfEratosthenes.java`
- Purpose: Fast sieve for moderate limits.
- Complexity:
  - Time: O(n log log n) (classic sieve marking cost).
  - Space: O(n) for the boolean array plus O(#primes) output.
- Pros: Fast and simple, deterministic.
- Cons: Memory usage grows with limit.
- Example request:
  - `GET /api/primes?limit=1000000&algorithm=SIEVE_OF_ERATOSTHENES`

### Parallel Algorithm
- Source: `src/main/java/com/example/prime/service/algorithm/ParallelAlgorithm.java`
- Purpose: Parallel marking to utilize multiple cores.
- Complexity: Similar to sieve, but parallel marking overhead.
- Pros: Faster on multi-core machines.
- Cons: Higher memory and possible false-sharing writes; thread overhead.
- Example request:
  - `GET /api/primes?limit=5000000&algorithm=PARRALEL_ALGORITHM`

### Segmented Sieve
- Source: `src/main/java/com/example/prime/service/algorithm/SegmentedSieve.java`
- Purpose: Low-memory sieve for very large limits by segmenting.
- Complexity: Time ~ O(n log log n), Space O(segment size + sqrt(n)).
- Pros: Handles large limits with bounded memory.
- Cons: Slightly more complex implementation.
- Example request:
  - `GET /api/primes?limit=200000000&algorithm=SEGMENTED_SIEVE`
