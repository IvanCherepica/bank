package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BankApplication.class)
class BankApplicationTests {

	@Autowired
	private AccountService accountService;

	@Test
	void contextLoads() {
		for (long i = 1; i < 6; i++) {
			accountService.getByKey(i);
		}
	}

}
