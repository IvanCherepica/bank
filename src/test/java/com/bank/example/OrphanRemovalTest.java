package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.sqltracker.AssertSqlCount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = BankApplication.class)
public class OrphanRemovalTest extends BaseTest {

    @Test
    @Commit
    void testNoCascade() {
        Long accountId = initNoCascadeAccountAndGetId();

        em.clear();

        com.bank.example.model.orphanRemoval.Account account = em.find(com.bank.example.model.orphanRemoval.Account.class, accountId);

        com.bank.example.model.orphanRemoval.Deposit deposit = account.getDeposits().get(0);

        AssertSqlCount.reset();

        account.getDeposits().remove(deposit);
        deposit.setAccount(null);

        em.flush();

        AssertSqlCount.assertUpdateCount(1);
        AssertSqlCount.assertDeleteCount(0);
    }

    @Test
    @Commit
    void testPersistCascade() {
        Long accountId = initPersistCascadeAccountAndGetId();

        em.clear();

        com.bank.example.model.orphanRemoval.cascade.persist.Account account =
                em.find(com.bank.example.model.orphanRemoval.cascade.persist.Account.class, accountId);

        com.bank.example.model.orphanRemoval.cascade.persist.Deposit deposit = account.getDeposits().get(0);

        AssertSqlCount.reset();

        account.getDeposits().remove(deposit);
        deposit.setAccount(null);

        em.flush();

        AssertSqlCount.assertUpdateCount(0);
        AssertSqlCount.assertDeleteCount(1);
    }

    @Test
    @Commit
    void testAllCascade() {
        Long accountId = initAllCascadeAccountAndGetId();

        em.clear();

        com.bank.example.model.orphanRemoval.cascade.all.Account account =
                em.find(com.bank.example.model.orphanRemoval.cascade.all.Account.class, accountId);

        com.bank.example.model.orphanRemoval.cascade.all.Deposit deposit = account.getDeposits().get(0);

        AssertSqlCount.reset();

        account.getDeposits().remove(deposit);
        deposit.setAccount(null);

        em.flush();

        AssertSqlCount.assertUpdateCount(0);
        AssertSqlCount.assertDeleteCount(1);
    }

    private Long initNoCascadeAccountAndGetId() {
        com.bank.example.model.orphanRemoval.Account account = new com.bank.example.model.orphanRemoval.Account();
        account.setFirstName("Иван");
        account.setLastName("Иванов");

        em.persist(account);

        List<com.bank.example.model.orphanRemoval.Deposit> depositList = new ArrayList<>();
        com.bank.example.model.orphanRemoval.Deposit deposit1 = new com.bank.example.model.orphanRemoval.Deposit();
        com.bank.example.model.orphanRemoval.Deposit deposit2 = new com.bank.example.model.orphanRemoval.Deposit();
        com.bank.example.model.orphanRemoval.Deposit deposit3 = new com.bank.example.model.orphanRemoval.Deposit();

        depositList.add(deposit1);
        depositList.add(deposit2);
        depositList.add(deposit1);

        deposit1.setAccount(account);
        deposit2.setAccount(account);
        deposit3.setAccount(account);

        account.setDeposits(depositList);

        em.persist(deposit1);
        em.persist(deposit2);
        em.persist(deposit3);

        em.merge(account);

        em.flush();

        return account.getId();
    }

    private Long initPersistCascadeAccountAndGetId() {
        com.bank.example.model.orphanRemoval.cascade.persist.Account account =
                new com.bank.example.model.orphanRemoval.cascade.persist.Account();
        account.setFirstName("Иван");
        account.setLastName("Иванов");

        em.persist(account);

        List<com.bank.example.model.orphanRemoval.cascade.persist.Deposit> depositList = new ArrayList<>();
        com.bank.example.model.orphanRemoval.cascade.persist.Deposit deposit1 =
                new com.bank.example.model.orphanRemoval.cascade.persist.Deposit();
        com.bank.example.model.orphanRemoval.cascade.persist.Deposit deposit2 =
                new com.bank.example.model.orphanRemoval.cascade.persist.Deposit();
        com.bank.example.model.orphanRemoval.cascade.persist.Deposit deposit3 =
                new com.bank.example.model.orphanRemoval.cascade.persist.Deposit();

        depositList.add(deposit1);
        depositList.add(deposit2);
        depositList.add(deposit1);

        deposit1.setAccount(account);
        deposit2.setAccount(account);
        deposit3.setAccount(account);

        account.setDeposits(depositList);

        em.persist(deposit1);
        em.persist(deposit2);
        em.persist(deposit3);

        em.merge(account);

        em.flush();

        return account.getId();
    }

    private Long initAllCascadeAccountAndGetId() {
        com.bank.example.model.orphanRemoval.cascade.all.Account account =
                new com.bank.example.model.orphanRemoval.cascade.all.Account();
        account.setFirstName("Иван");
        account.setLastName("Иванов");

        em.persist(account);

        List<com.bank.example.model.orphanRemoval.cascade.all.Deposit> depositList = new ArrayList<>();
        com.bank.example.model.orphanRemoval.cascade.all.Deposit deposit1 =
                new com.bank.example.model.orphanRemoval.cascade.all.Deposit();
        com.bank.example.model.orphanRemoval.cascade.all.Deposit deposit2 =
                new com.bank.example.model.orphanRemoval.cascade.all.Deposit();
        com.bank.example.model.orphanRemoval.cascade.all.Deposit deposit3 =
                new com.bank.example.model.orphanRemoval.cascade.all.Deposit();

        depositList.add(deposit1);
        depositList.add(deposit2);
        depositList.add(deposit1);

        deposit1.setAccount(account);
        deposit2.setAccount(account);
        deposit3.setAccount(account);

        account.setDeposits(depositList);

        em.persist(deposit1);
        em.persist(deposit2);
        em.persist(deposit3);

        em.merge(account);

        em.flush();

        return account.getId();
    }
}