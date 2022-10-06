package com.spring.qa.autoGb.lesson5.petstore.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.spring.qa.autoGb.lesson5.restassured.petstore")
public class PetStoreTestConfig {
}
