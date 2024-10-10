package com.colak.springtutorial.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class MyServiceTest {

    @Autowired
    private MyService myService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    void testSayHello() {

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("helloServiceCircuitBreaker");

        for (int index = 0; index < 5; index++) {
            String result = myService.sayHello(true);
            assertEquals(MyService.FALLBACK_MESSAGE, result);
        }

        // The circuit should now be in OPEN state
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());

        // Wait for the circuit breaker to transition to HALF_OPEN state
        try {
            Thread.sleep(6000); // Wait for longer than the waitDurationInOpenState (5 seconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // The circuit should now be in HALF_OPEN state
        assertEquals(CircuitBreaker.State.HALF_OPEN, circuitBreaker.getState());

        // Make a successful calls to transition to CLOSED state
        for (int index = 0; index < 2; index++) {
            myService.sayHello(false); // Simulate a successful call
        }

        // The circuit should now transition back to CLOSED state
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());

    }
}
