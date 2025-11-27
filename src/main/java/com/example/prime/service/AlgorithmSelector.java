package com.example.prime.service;

import com.example.prime.service.algorithm.AlgorithmType;
import org.springframework.stereotype.Component;

//selects algorithm based on the limit if no user input is given
@Component
public class AlgorithmSelector {

    private static final int TRIAL_DIVISION_THRESHOLD = 10_000;
    private static final int SIEVE_OF_ERATOSTHENES_THRESHOLD = 3_000_000;
    private static final int PARALLEL_ALGORITHM_THRESHOLD = 10_000_000;
    private static final int SEGMENTED_SIEVE_THRESHOLD = 1_000_000_000;

    public AlgorithmType select(int limit) {
        if (limit <= 0) return AlgorithmType.SIEVE_OF_ERATOSTHENES;
        if (limit < TRIAL_DIVISION_THRESHOLD) return AlgorithmType.TRIAL_DIVISION;
        if (limit < SIEVE_OF_ERATOSTHENES_THRESHOLD) return AlgorithmType.SIEVE_OF_ERATOSTHENES;
        if (limit < PARALLEL_ALGORITHM_THRESHOLD) return AlgorithmType.PARRALEL_ALGORITHM;
        if (limit <= SEGMENTED_SIEVE_THRESHOLD) return AlgorithmType.SEGMENTED_SIEVE;
        return AlgorithmType.SIEVE_OF_ERATOSTHENES;
    }

    public AlgorithmType selectAlgorithm(int limit) {
        return select(limit);
    }
}
