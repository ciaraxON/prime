package com.example.prime.service.algorithm;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SieveOfEratosthenes implements PrimeAlgorithm{
    @Override
    public List<Integer> generatePrimes(int limit) {
        if (limit < 2) {
            return List.of();
        }
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
    @Override
    public AlgorithmType getType() {
        return AlgorithmType.SIEVE_OF_ERATOSTHENES;
    }
}
