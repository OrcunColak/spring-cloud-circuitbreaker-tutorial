# Read Me

The original idea is from  
https://nirajtechi.medium.com/circuit-breaker-in-microservices-and-spring-boot-example-4ad76c7a33e6

# Actuator

The original idea is from
https://medium.com/@truongbui95/circuit-breaker-pattern-in-spring-boot-d2d258b75042

Go to  
http://localhost:8080/actuator/health
or
http://localhost:8080/actuator/circuitbreakers
CLOSED case

```
{
  "circuitBreakers": {
    "status": "UP",
    "details": {
      "order-service": {
        "status": "UP",
        "details": {
          "failureRate": "-1.0%",  // Ratio of failedCalls to bufferedCalls (failedCalls/bufferedCalls) * 100
          "failureRateThreshold": "50.0%",
          "slowCallRate": "-1.0%",
          "slowCallRateThreshold": "100.0%",
          "bufferedCalls": 2,  // Total number of API calls from order-service to address-service
          "slowCalls": 0,
          "slowFailedCalls": 0,
          "failedCalls": 0,  // Number of failed API calls  from order-service to address-service
          "notPermittedCalls": 0,
          "state": "CLOSED"  // CircuitBreaker state
        }
      }
    }
  }
}
```

OPEN case

```
{
  "circuitBreakers": {
    "status": "UNKNOWN",
    "details": {
      "order-service": {
        "status": "CIRCUIT_OPEN",  // CircuitBreaker is triggered
        "details": {
          "failureRate": "60.0%",  // this rate now is greater than "failureRateThreshold"     
          "failureRateThreshold": "50.0%",
          "slowCallRate": "0.0%",
          "slowCallRateThreshold": "100.0%",
          "bufferedCalls": 5,  // Total number of API calls from order-service to address-service
          "slowCalls": 0,
          "slowFailedCalls": 0,
          "failedCalls": 3,  // Number of failed API calls  from order-service to address-service
          "notPermittedCalls": 0,
          "state": "OPEN"
        }
      }
    }
  }
}
```

