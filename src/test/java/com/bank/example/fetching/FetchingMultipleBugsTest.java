package com.bank.example.fetching;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.model.Department;
import com.bank.example.model.Manager;
import com.bank.example.model.Operator;
import com.bank.example.model.OperatorRating;
import com.bank.example.model.fetching.SetDepartment;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = BankApplication.class)
public class FetchingMultipleBugsTest extends BaseTest {

    @Test
    void joinFetchInnerEntity() {

        List<Department> departmentList = em.createQuery(
                "SELECT d FROM Department d JOIN FETCH d.operators o JOIN FETCH d.managers",
                Department.class)
                .getResultList();

        for (Department department : departmentList) {
            List<Operator> operatorList = department.getOperators();

            for (Operator operator : operatorList) {

                List<OperatorRating> operatorRatingList = operator.getOperatorRatings();

                for (OperatorRating operatorRating : operatorRatingList) {
                    operatorRating.getRating();
                }
            }
        }
    }

    @Test
    void joinFetchInnerGraph() {

        EntityGraph<Department> entityGraph = em.createEntityGraph(Department.class);
        entityGraph.addAttributeNodes("operators");
        entityGraph.addAttributeNodes("managers");

        List<Department> departmentList = em.createQuery(
                "SELECT d FROM Department d",
                Department.class
        )
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        for (Department department : departmentList) {
            List<Operator> operatorList = department.getOperators();

            for (Operator operator : operatorList) {

                List<OperatorRating> operatorRatingList = operator.getOperatorRatings();

                for (OperatorRating operatorRating : operatorRatingList) {
                    operatorRating.getRating();
                }
            }
        }
    }

    @Test
    void testMultipleBugSetSolution() {

        List<SetDepartment> departmentList = em.createQuery(
                "SELECT DISTINCT d FROM com.bank.example.model.fetching.SetDepartment d " +
                        "JOIN FETCH d.managers JOIN FETCH d.operators",
                SetDepartment.class)
                .getResultList();

        for (SetDepartment department : departmentList) {
            List<Manager> managerList = department.getManagers();
            Set<Operator> operatorList = department.getOperators();

            for (Manager manager : managerList) {
                manager.getPhone();
            }

            for (Operator operator : operatorList) {
                operator.getFirstName();
            }
        }
    }

    @Test
    void testMultipleBugMultipleQuerySolution() {

        List<Department> departmentList = em.createQuery(
                "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.managers",
                Department.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        departmentList = em.createQuery(
                "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.operators WHERE d IN :departments",
                Department.class
        )
                .setParameter("departments", departmentList)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        for (Department department : departmentList) {
            List<Manager> managerList = department.getManagers();
            List<Operator> operatorList = department.getOperators();

            for (Manager manager : managerList) {
                manager.getPhone();
            }

            for (Operator operator : operatorList) {
                operator.getFirstName();
            }
        }
    }
}
