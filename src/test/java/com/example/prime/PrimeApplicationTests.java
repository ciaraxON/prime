package com.example.prime;

import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PrimeApplicationTests {

    @Autowired
    private ApplicationContext ctx;

	@Test
	void contextLoads() {
        assertNotNull(ctx.getBean(PrimeApplication.class));
	}

}
