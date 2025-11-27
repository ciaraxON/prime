package com.example.prime.service;

import com.example.prime.service.algorithm.AlgorithmType;
import com.example.prime.service.algorithm.PrimeAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimeService {

    private final AlgorithmMapping algorithmMapping;
    private final AlgorithmSelector algorithmSelector;

    @Autowired
    public PrimeService(AlgorithmMapping algorithmMapping, AlgorithmSelector algorithmSelector) {
        this.algorithmMapping = algorithmMapping;
        this.algorithmSelector = algorithmSelector;
    }

    //cache results based on limit and algorithm type
    @Cacheable(value = "primeResults", key = "T(java.lang.String).valueOf(#limit) + '-' + (#algorithm != null ? #algorithm.name() : 'DEFAULT')")
    public List<Integer> generatePrimes(Integer limit, AlgorithmType algorithm) {
        if (limit == null) {
            throw new IllegalArgumentException("limit cannot be null");
        }

        int lim = limit;
        //use default algorithm if none is specified
        AlgorithmType selected = algorithm != null? algorithm : algorithmSelector.selectAlgorithm(limit);
        PrimeAlgorithm impl = algorithmMapping.getAlgorithm(selected);
        return impl.generatePrimes(limit);
    }
}
