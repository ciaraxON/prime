package com.example.prime.unit;

import com.example.prime.service.algorithm.SieveOfEratosthenes;
import com.example.prime.service.algorithm.TrialDivisionAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrialDivisionAlgorithmTest {

    private final TrialDivisionAlgorithm alg = new TrialDivisionAlgorithm();

    @Test
    void handlesEdgeCasesAndSmallLimits() {
        assertEquals(List.of(), alg.generatePrimes(0));
        assertEquals(List.of(), alg.generatePrimes(1));
        assertEquals(List.of(2), alg.generatePrimes(2));
        assertEquals(List.of(2, 3, 5), alg.generatePrimes(5));
    }

    @Test
    void matchesSieveForModerateRange() {
        assertEquals(new SieveOfEratosthenes().generatePrimes(100), alg.generatePrimes(100));
    }
}
