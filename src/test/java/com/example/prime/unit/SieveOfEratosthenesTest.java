package com.example.prime.unit;

import com.example.prime.service.algorithm.SieveOfEratosthenes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SieveOfEratosthenesTest {

    private final SieveOfEratosthenes sieve = new SieveOfEratosthenes();

    @Test
    void returnsEmptyForLimitsBelowTwo() {
        assertEquals(List.of(), sieve.generatePrimes(0));
        assertEquals(List.of(), sieve.generatePrimes(1));
    }

    @Test
    void generatesExpectedPrimesForSmallLimit() {
        assertEquals(List.of(2, 3, 5, 7), sieve.generatePrimes(10));
        assertEquals(List.of(2, 3, 5, 7, 11), sieve.generatePrimes(11));
    }
}
