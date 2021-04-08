package com.bank.example.fetching;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.model.Account;
import com.bank.example.model.Department;
import com.bank.example.model.DocumentScans;
import com.bank.example.model.Manager;
import com.bank.example.model.Operator;
import com.bank.example.model.OperatorRating;
import com.bank.example.model.Tariff;
import com.bank.example.model.fetching.BatchDepartment;
import com.bank.example.model.fetching.EagerDepartment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = BankApplication.class)
public class FetchingTest extends BaseTest {

    @Test
    void eager() {

        EagerDepartment eagerDepartment = em.find(EagerDepartment.class, 1L);
    }

    @Test
    void lazy() {

        Department eagerDepartment = em.find(Department.class, 1L);
    }

    @Test
    void lazyProblem() {

        Department department = em.find(Department.class, 1L);

        List<Manager> managerList = department.getManagers();

        for (Manager manager : managerList) {
            manager.getPhone();
        }
    }

    @Test
    void lazyBigProblem() {

        List<Department> departmentList = em.createQuery("SELECT d FROM Department d",
                Department.class)
                .getResultList();

        for (Department department : departmentList) {
            List<Manager> managerList = department.getManagers();

            for (Manager manager : managerList) {
                manager.getPhone();
            }
        }
    }

    @Test
    void eagerProblem() {

        List<EagerDepartment> eagerDepartmentList = em.createQuery(
                "SELECT d FROM com.bank.example.model.fetching.EagerDepartment d",
                EagerDepartment.class)
                .getResultList();

        for (EagerDepartment eagerDepartment : eagerDepartmentList) {
            List<Manager> managerList = eagerDepartment.getManagers();

            for (Manager manager : managerList) {
                manager.getPhone();
            }
        }
    }

    @Test
    void joinFetch() {

        List<Department> departmentList = em.createQuery("SELECT d FROM Department d JOIN FETCH d.managers",
                Department.class)
                .getResultList();

        for (Department department : departmentList) {
            List<Manager> managerList = department.getManagers();

            for (Manager manager : managerList) {
                manager.getPhone();
            }
        }
    }

    @Test
    void entityGraph_1() {

        EntityGraph<Department> entityGraph = em.createEntityGraph(Department.class);
        entityGraph.addAttributeNodes("managers");

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.loadgraph", entityGraph);

        Department department = em.find(Department.class, 1L, hints);

        List<Manager> managerList = department.getManagers();

        for (Manager manager : managerList) {
            manager.getPhone();
        }
    }

    @Test
    void entityGraph_2() {

        EntityGraph<OperatorRating> entityGraph = em.createEntityGraph(OperatorRating.class);
        Subgraph<Account> subgraph = entityGraph.addSubgraph("account");
        subgraph.addAttributeNodes("tariff");

        List<OperatorRating> operatorRatingList = em.createQuery("SELECT o FROM OperatorRating o",
                OperatorRating.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        for (OperatorRating operatorRating : operatorRatingList) {
            Account account = operatorRating.getAccount();
            Tariff tariff = account.getTariff();
            tariff.getName();
        }
    }

    @Test
    void batchSize() {

        List<BatchDepartment> batchDepartmentList = em.createQuery(
                "SELECT d FROM com.bank.example.model.fetching.BatchDepartment d",
                BatchDepartment.class)
                .getResultList();

        for (BatchDepartment department : batchDepartmentList) {
            List<Manager> managerList = department.getManagers();

            for (Manager manager : managerList) {
                manager.getPhone();
            }
        }
    }
}
