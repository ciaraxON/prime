package com.example.prime.service.algorithm;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SegmentedSieve implements PrimeAlgorithm {

    private static final int DEFAULT_SEGMENT_SIZE = 1_000_000;

    @Override
    public List<Integer> generatePrimes(int limit) {
        if (limit < 2) {
            return List.of();
        }

        int sqrt = (int) Math.sqrt(limit);

        // simple sieve for base primes up to sqrt(limit)
        boolean[] isPrimeSmall = new boolean[sqrt + 1];
        Arrays.fill(isPrimeSmall, true);
        if (sqrt >= 0) isPrimeSmall[0] = false;
        if (sqrt >= 1) isPrimeSmall[1] = false;
        for (int i = 2; i * i <= sqrt; i++) {
            if (isPrimeSmall[i]) {
                for (int j = i * i; j <= sqrt; j += i) {
                    isPrimeSmall[j] = false;
                }
            }
        }
        List<Integer> basePrimes = new ArrayList<>();
        for (int i = 2; i <= sqrt; i++) {
            if (isPrimeSmall[i]) basePrimes.add(i);
        }

        List<Integer> primes = new ArrayList<>();
        // process in segments
        int segmentSize = Math.max(DEFAULT_SEGMENT_SIZE, sqrt + 1);
        for (int low = 2; low <= limit; low += segmentSize) {
            int high = Math.min(low + segmentSize - 1, limit);
            boolean[] segment = new boolean[high - low + 1];
            Arrays.fill(segment, true);

            for (int p : basePrimes) {
                long start = Math.max((long) p * p, ((long) ((low + p - 1) / p)) * p);
                for (long j = start; j <= high; j += p) {
                    segment[(int) (j - low)] = false;
                }
            }

            for (int i = 0; i < segment.length; i++) {
                if (segment[i]) {
                    primes.add(low + i);
                }
            }
        }

        return primes;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.SEGMENTED_SIEVE;
    }
}