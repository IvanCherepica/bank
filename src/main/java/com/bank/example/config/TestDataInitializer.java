package com.bank.example.config;

import com.bank.example.service.TestDataInitServiceImpl;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class TestDataInitializer implements CommandLineRunner {

    private final TestDataInitServiceImpl testDataInitService;

    public TestDataInitializer(TestDataInitServiceImpl testDataInitService) {
        this.testDataInitService = testDataInitService;
    }

    @Override
    public void run(String... args) throws Exception {
        testDataInitService.init();
        try {
            testDataInitService.insertErrorData();
        } catch (Exception e) {}
    }
}
