package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.model.Department;
import com.bank.example.model.Manager;
import com.bank.example.service.CashBackCategoryService;
import com.bank.example.service.DepartmentService;
import com.bank.example.service.ManagerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = BankApplication.class)
public class CascadeTest extends BaseTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ManagerService managerService;

    @Test
    @Commit
    void persistTest() {
        Department department = new Department("Отдел программы лояльности");
        Manager manager1 = new Manager("+79117885565", "6565");
        Manager manager2 = new Manager("+79145658896", "6060");
        department.addManager(manager1);
        department.addManager(manager2);
        departmentService.persist(department);
    }

    @Test
    @Commit
    void mergeTest() {
        Department department = initData(); //инициализируем данные
        List<Manager> managers = department.getManagers();
        em.flush(); //принудительно сбрасываем данные в базу
        em.clear(); //очищаем контекст персистентности

        //проверяем, department что сущность находится в состоянии detach
        Assert.assertFalse(em.contains(department));

        //проверяем что все связанные managers так же в detach
        for (Manager manager : managers) {
            Assert.assertFalse(em.contains(manager));
        }

        //меняем данные
        department.setName("(" + department.getName() + ") изменено после merge");
        for (Manager manager : managers) {
            manager.setPhone("(" + manager.getPhone() + ") изменено после merge");
            manager.setPassportNumber("(" + manager.getPassportNumber() + ") изменено после merge");
        }

        em.merge(department);
    }

    @Test
    @Commit
    void refreshTest() {
        Department department = initData(); //инициализируем данные
        List<Manager> managers = department.getManagers();
        em.flush(); //принудительно сбрасываем данные в базу

        //меняем данные так, будто хотим записать изменения в базу
        department.setName("(" + department.getName() + ") изменено перед refresh");
        for (Manager manager : managers) {
            manager.setPhone("(" + manager.getPhone() + ") изменено перед refresh");
            manager.setPassportNumber("(" + manager.getPassportNumber() + ") изменено перед refresh");
        }

        //стираем все внесённые выше изменения
        em.refresh(department);

        //в консоль выводятся именя, телефоны и паспортне номера, которые были записаны при инициализации данных
        System.out.println();
        System.out.println(department.getName());
        for (Manager manager : managers) {
            System.out.println(manager.getPhone() + " " + manager.getPassportNumber());
        }
    }

    @Test
    @Commit
    void removeTest() {
        Department department = initData(); //инициализируем данные
        List<Manager> managers = department.getManagers();
        em.flush(); //принудительно сбрасываем данные в базу

        departmentService.remove(department);

        Long departmentId = department.getId();
        List<Long> managerIds = managers.stream().map(Manager::getId).collect(Collectors.toList());

        Assert.assertNull(departmentService.getByKey(departmentId));
        Assert.assertTrue(managerService.getAllByIds(managerIds).isEmpty());
    }

    @Test
    @Commit
    void detachTest() {
        Department department = initData(); //инициализируем данные
        List<Manager> managers = department.getManagers();
        em.flush(); //принудительно сбрасываем данные в базу

        em.detach(department);

        //проверяем, department что сущность находится в состоянии detach
        Assert.assertFalse(em.contains(department));

        //проверяем что все связанные managers так же в detach
        for (Manager manager : managers) {
            Assert.assertFalse(em.contains(manager));
        }

        department.setName("(" + department.getName() + ") изменено после detach");
        for (Manager manager : managers) {
            manager.setPhone("(" + manager.getPhone() + ") изменено после detach");
            manager.setPassportNumber("(" + manager.getPassportNumber() + ") изменено после detach");
        }
    }

    @Test
    @Commit
    void cascadeRemoveTest() {
        saveData();
        em.flush();

        com.bank.example.model.cascade.CashBackCategory category = em.find(com.bank.example.model.cascade.CashBackCategory.class, 1L);
//        em.remove(category);
    }


    private Department initData() {
        Department department = new Department("Отдел программы лояльности");
        Manager manager1 = new Manager("+79117885565", "6565");
        Manager manager2 = new Manager("+79145658896", "6060");
        department.addManager(manager1);
        department.addManager(manager2);
        departmentService.persist(department);
        return department;
    }

    private static final List<String> cashBackCategoriesNames = new ArrayList<>(4);
    private static final List<String> cashBackCompaniesNames = new ArrayList<>(4);
    private static List<CashBackCategory> cashBackCategories = new ArrayList<>();
    private static List<CashBackCompany> cashBackCompanies = new ArrayList<>();


    static {
        cashBackCategoriesNames.add("Кафе");
        cashBackCategoriesNames.add("Цветы");
        cashBackCategoriesNames.add("Развлечения");
        cashBackCategoriesNames.add("Подарки");

        cashBackCompaniesNames.add("Клумба");//цветы+подарки
        cashBackCompaniesNames.add("Наполеон");//торты на заказ(подарки+кафе)
        cashBackCompaniesNames.add("Антикафе№1");//кафе+развлечения
        cashBackCompaniesNames.add("Небо");//кинотеарт(развлечения+кино)
    }

    private void saveData() {
        addCashBackCategory();
        addCashBackCompany();
        attachCashBackCompanyToCategories();
    }

    private void addCashBackCategory() {
        for (int i = 0; i < 4; i++) {
            CashBackCategory cashBackCategory = new CashBackCategory();
            cashBackCategory.setName(cashBackCategoriesNames.get(i));
            em.persist(cashBackCategory);
            cashBackCategories.add(cashBackCategory);
        }
    }

    private void addCashBackCompany() {
        for (int i = 0; i < 4; i++) {
            CashBackCompany cashBackCompany = new CashBackCompany();
            cashBackCompany.setName(cashBackCompaniesNames.get(i));
            em.persist(cashBackCompany);
            cashBackCompanies.add(cashBackCompany);
        }
    }

    private void attachCashBackCompanyToCategories() {

        for (CashBackCategory category : cashBackCategories) {
            switch (category.getName()) {
                case "Цветы":
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Клумба")).findFirst().get()
                    );
                    break;
                case "Подарки":
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Клумба")).findFirst().get()
                    );
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Наполеон")).findFirst().get()
                    );
                    break;
                case "Кафе":
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Наполеон")).findFirst().get()
                    );
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Антикафе№1")).findFirst().get()
                    );
                    break;
                case "Развлечения":
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Антикафе№1")).findFirst().get()
                    );
                    category.addCashBackCompany(
                            cashBackCompanies.stream().filter(c -> c.getName().equals("Небо")).findFirst().get()
                    );
                    break;

            }
            em.merge(category);
        }
    }
}
