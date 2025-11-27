package com.example.prime.service.algorithm;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParallelAlgorithm implements PrimeAlgorithm {

    @Override
    public List<Integer> generatePrimes(int limit) {
        if (limit < 2) {
            return List.of();
        }

        int sqrt = (int) Math.sqrt(limit);

        // Compute base primes up to sqrt(limit) (sequential, small)
        boolean[] baseIsPrime = new boolean[sqrt + 1];
        Arrays.fill(baseIsPrime, true);
        if (sqrt >= 0) baseIsPrime[0] = false;
        if (sqrt >= 1) baseIsPrime[1] = false;
        for (int i = 2; i * i <= sqrt; i++) {
            if (baseIsPrime[i]) {
                for (int j = i * i; j <= sqrt; j += i) {
                    baseIsPrime[j] = false;
                }
            }
        }
        List<Integer> basePrimes = new ArrayList<>();
        for (int i = 2; i <= sqrt; i++) {
            if (baseIsPrime[i]) basePrimes.add(i);
        }

        // Full range marking array
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        if (limit >= 1) isPrime[1] = false;

        // Mark multiples of base primes in parallel
        basePrimes.parallelStream().forEach(p -> {
            int start = p * p;
            for (int j = start; j <= limit; j += p) {
                isPrime[j] = false;
            }
        });

        // Collect primes
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) primes.add(i);
        }
        return primes;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.PARRALEL_ALGORITHM;
    }
}