package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.sqltracker.AssertSqlCount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

import static com.bank.example.sqltracker.QueryCountInfoHolder.getQueryInfo;


@SpringBootTest(classes = BankApplication.class)
@Transactional
public abstract class BaseTest {

    @PersistenceContext
    protected EntityManager em;

    protected Session session;

    @BeforeEach
    public void dbAllSet() {
        AssertSqlCount.reset();
        session = em.unwrap(Session.class);
    }

    @AfterTransaction
    public void showSqlCount() {
        System.out.print(getQueryInfo().getReport());
    }

    protected SessionFactory getSessionFactory() {
        return session.getSessionFactory();
    }
}
