package com.colak.springtutorial.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class MyService {

    public static final String HELLO_MESSAGE = "Hello";
    public static final String FALLBACK_MESSAGE = "Fallback response: Service is down";

    @CircuitBreaker(name = "helloServiceCircuitBreaker", fallbackMethod = "fallback")
    public String sayHello(boolean throwException) {
        // Simulate a failure
        if (throwException) {
            throw new RuntimeException("Service is currently unavailable");
        }
        return HELLO_MESSAGE;
    }

    public String fallback(boolean throwException, Throwable throwable) {
        log.info("Fallback");
        return FALLBACK_MESSAGE;
    }
}
