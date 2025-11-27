package com.example.prime.service.algorithm;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TrialDivisionAlgorithm implements PrimeAlgorithm {

    @Override
    public List<Integer> generatePrimes(int limit) {
        if (limit < 2) {
            return List.of();
        }
        return IntStream.rangeClosed(2, limit)
                .filter(this::isPrime)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        if (number % 2 == 0) return number == 2;
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.TRIAL_DIVISION;
    }
}