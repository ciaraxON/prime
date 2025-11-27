package com.example.prime.service;

import com.example.prime.service.algorithm.AlgorithmType;
import com.example.prime.service.algorithm.PrimeAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AlgorithmMapping {

    private final Map<AlgorithmType, PrimeAlgorithm> algorithms;

    //build mapping
    @Autowired
    public AlgorithmMapping(List<PrimeAlgorithm> implementations) {
        this.algorithms = implementations.stream()
                .collect(Collectors.toMap(PrimeAlgorithm::getType, impl -> impl));
    }

    //retrieve implementation based on type default to SIEVE_OF_ERATOSTHENES
    public PrimeAlgorithm getAlgorithm(AlgorithmType type) {
        return algorithms.getOrDefault(type, algorithms.get(AlgorithmType.SIEVE_OF_ERATOSTHENES));
    }

}
