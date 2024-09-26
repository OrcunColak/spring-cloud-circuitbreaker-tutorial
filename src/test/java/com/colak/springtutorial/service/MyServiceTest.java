package com.colak.springtutorial.service;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Slf4j
class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    void testSayHello() {
        AtomicInteger counter = new AtomicInteger();
        Awaitility.await()
                .until(() -> {
                    log.info("Call : {}", counter.getAndIncrement());

                    boolean result = false;
                    try {
                        String str = myService.sayHello();
                        result = "Fallback: Hello from service".equals(str);
                    } catch (Exception _) {
                    }
                    return result;
                });

        for (int index = 0; index < 20; index++) {
            myService.sayHello();
        }

    }
}
