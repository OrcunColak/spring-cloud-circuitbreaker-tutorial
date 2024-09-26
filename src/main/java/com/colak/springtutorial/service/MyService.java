package com.colak.springtutorial.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class MyService {

    @CircuitBreaker(name = "helloService", fallbackMethod = "fallbackHello")
    public String sayHello() {
        // Simulate a service call
        if (Math.random() > 0.9) {
            log.info("exception");
            throw new RuntimeException("Service call failed");
        }
        log.info("success");
        return "Hello from service";
    }

    public String fallbackHello(Throwable throwable) {
        log.info("Fallback");
        return "Fallback: Hello from service";
    }
}
