package com.example.prime.controller;

import jakarta.validation.constraints.Min;
import com.example.prime.model.PrimeResponse;
import com.example.prime.service.PrimeService;
import com.example.prime.service.algorithm.AlgorithmType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/primes")
public class PrimeController {

    private final PrimeService primeService;

    @Autowired
    public PrimeController(PrimeService primeService) {
        this.primeService = primeService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PrimeResponse> getPrimes(
            @RequestParam(name = "limit", required = true)
            //validations
            @NotNull(message = "cannot be null")
            @Min(value = 2, message = "must be greater than or equal to 2")
            Integer limit,
            //optional algorithm selection
            @RequestParam(name = "algorithm", required = false) AlgorithmType algorithm,
            //format parameter
            HttpServletRequest request) {

    //generate primes using service
    List<Integer> primes = primeService.generatePrimes(limit, algorithm);

    //get format parameter from request
    String format = request.getParameter("format");

    //build response
    PrimeResponse response = new PrimeResponse(limit, algorithm !=null ? algorithm.name() : null, format, primes);
    return ResponseEntity.ok(response);
    }
}


