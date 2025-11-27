package com.example.prime.unit;

import com.example.prime.service.algorithm.ParallelAlgorithm;
import com.example.prime.service.algorithm.SegmentedSieve;
import com.example.prime.service.algorithm.SieveOfEratosthenes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegmentedAndParallelAlgorithmsTest {

    private final SegmentedSieve segmented = new SegmentedSieve();
    private final ParallelAlgorithm parallel = new ParallelAlgorithm();
    private final SieveOfEratosthenes sieve = new SieveOfEratosthenes();

    @Test
    void segmentedMatchesSieveForSmallRange() {
        int limit = 200;
        List<Integer> expected = sieve.generatePrimes(limit);
        assertEquals(expected, segmented.generatePrimes(limit));
    }

    @Test
    void parallelMatchesSieveForSmallRange() {
        int limit = 200;
        List<Integer> expected = sieve.generatePrimes(limit);
        assertEquals(expected, parallel.generatePrimes(limit));
    }
}

//combined and simplified the two algorithms tests into one file as they both test against the Sieve of Eratosthenes for correctness
