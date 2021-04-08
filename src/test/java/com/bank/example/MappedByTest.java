package com.bank.example;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.service.AccountService;
import com.bank.example.service.CashBackCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest(classes = BankApplication.class)
class MappedByTest extends BaseTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CashBackCategoryService cashBackCategoryService;


    private Account initAccount() {
        Account account = new Account();
        account.setFirstName("Иван");
        account.setLastName("Иванов");

        accountService.persist(account);
        return account;
    }

    private CashBackCategory initCashBackCategory() {
        CashBackCategory category = new CashBackCategory();
        category.setName("Летние туры");

        cashBackCategoryService.persist(category);
        return category;
    }


    @Test
    @Commit
    void addCategoryToAccount() {
        Account account = initAccount();
        CashBackCategory category = initCashBackCategory();

        account.getCashBackCategories().add(category);
    }

    @Test
    @Commit
    void addAccountToCategory() {
        Account account = initAccount();
        CashBackCategory category = initCashBackCategory();

        category.getAccounts().add(account);
    }

    @Test
    @Commit
    void addAccountToCategoryUseAddMethod() {
        Account account = initAccount();
        CashBackCategory category = initCashBackCategory();

        category.addAccount(account);
    }
}
