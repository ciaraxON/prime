package com.example.prime.unit;

import com.example.prime.service.AlgorithmSelector;
import com.example.prime.service.algorithm.AlgorithmType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmSelectorTest {

    private final AlgorithmSelector selector = new AlgorithmSelector();

    @Test
    void selectsCorrectAlgorithmAtBoundaries() {
        assertEquals(AlgorithmType.SIEVE_OF_ERATOSTHENES, selector.select(0));      // <= 0 -> SIEVE
        assertEquals(AlgorithmType.SIEVE_OF_ERATOSTHENES, selector.select(-5));     // negative -> SIEVE
        assertEquals(AlgorithmType.TRIAL_DIVISION, selector.select(2));             // small -> TRIAL_DIVISION
        assertEquals(AlgorithmType.TRIAL_DIVISION, selector.select(9_999));         // just below TRIAL threshold
        assertEquals(AlgorithmType.SIEVE_OF_ERATOSTHENES, selector.select(10_000)); // equals TRIAL threshold -> SIEVE
        assertEquals(AlgorithmType.PARRALEL_ALGORITHM, selector.select(3_000_000)); // equals SIEVE threshold -> PARALLEL (since logic uses <)
        assertEquals(AlgorithmType.SEGMENTED_SIEVE, selector.select(10_000_000));   // between parallel and segmented threshold
        assertEquals(AlgorithmType.SIEVE_OF_ERATOSTHENES, selector.select(Integer.MAX_VALUE)); // fallback
    }
}
