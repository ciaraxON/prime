package com.example.prime.unit;

import com.example.prime.service.AlgorithmMapping;
import com.example.prime.service.AlgorithmSelector;
import com.example.prime.service.PrimeService;
import com.example.prime.service.algorithm.AlgorithmType;
import com.example.prime.service.algorithm.PrimeAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrimeServiceTest {

    @Mock
    private AlgorithmMapping algorithmMapping;

    @Mock
    private AlgorithmSelector algorithmSelector;

    @InjectMocks
    private PrimeService primeService;

    @Test
    void nullLimitThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> primeService.generatePrimes(null, null));
    }

    @Test
    void usesProvidedAlgorithmWhenPresent() {
        PrimeAlgorithm alg = mock(PrimeAlgorithm.class);
        when(algorithmMapping.getAlgorithm(AlgorithmType.TRIAL_DIVISION)).thenReturn(alg);
        when(alg.generatePrimes(10)).thenReturn(List.of(2, 3, 5, 7));

        List<Integer> result = primeService.generatePrimes(10, AlgorithmType.TRIAL_DIVISION);
        assertEquals(List.of(2, 3, 5, 7), result);

        verify(algorithmMapping).getAlgorithm(AlgorithmType.TRIAL_DIVISION);
    }

    @Test
    void usesSelectorWhenAlgorithmIsNull() {
        when(algorithmSelector.selectAlgorithm(5)).thenReturn(AlgorithmType.TRIAL_DIVISION);
        PrimeAlgorithm alg = mock(PrimeAlgorithm.class);
        when(algorithmMapping.getAlgorithm(AlgorithmType.TRIAL_DIVISION)).thenReturn(alg);
        when(alg.generatePrimes(5)).thenReturn(List.of(2, 3, 5));

        List<Integer> result = primeService.generatePrimes(5, null);
        assertEquals(List.of(2, 3, 5), result);

        verify(algorithmSelector).selectAlgorithm(5);
        verify(algorithmMapping).getAlgorithm(AlgorithmType.TRIAL_DIVISION);
    }
}
