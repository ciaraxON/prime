package com.example.prime.service.algorithm;

import java.util.List;

public interface PrimeAlgorithm {
    List<Integer> generatePrimes(int limit);
    AlgorithmType getType();
}
