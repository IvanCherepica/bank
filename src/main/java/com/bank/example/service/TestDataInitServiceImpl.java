package com.bank.example.service;

import com.bank.example.model.*;
import com.bank.example.model.operation.Atm;
import com.bank.example.model.operation.AtmRefill;
import com.bank.example.model.operation.AtmWithdraw;
import com.bank.example.model.operation.CashBack;
import com.bank.example.model.operation.Interests;
import com.bank.example.service.operation.AtmRefillService;
import com.bank.example.service.operation.AtmService;
import com.bank.example.service.operation.AtmWithdrawService;
import com.bank.example.service.operation.CashBackService;
import com.bank.example.service.operation.InterestsService;
import com.bank.example.util.DateUtil;
import com.bank.example.util.NameUtil;
import com.bank.example.util.NumberUtil;
import com.bank.example.util.RandomUtil;
import com.bank.example.util.TransactionHolder;
import com.bank.example.util.dto.transaction.AtmTransactionUtilDto;
import com.bank.example.util.dto.transaction.OtherProfitUtilDto;
import com.bank.example.util.dto.transaction.TransactionUtilDto;
import com.bank.example.util.dto.transaction.TransactionUtilType;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.JoinColumn;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class TestDataInitServiceImpl {

    private final AccountService accountService;
    private final TariffService tariffService;
    private final SettingsService settingsService;
    private final DocumentScansService documentScansService;
    private final CloseRequestService closeRequestService;
    private final CardService cardService;
    private final DepositService depositService;
    private final OperatorService operatorService;
    private final ManagerService managerService;
    private final DepartmentService departmentService;
    private final OperatorRatingService operatorRatingService;
    private final CashBackCompanyService cashBackCompanyService;
    private final CashBackCategoryService cashBackCategoryService;
    private final AtmRefillService atmRefillService;
    private final AtmWithdrawService atmWithdrawService;
    private final CashBackService cashBackService;
    private final InterestsService interestsService;
    private final AtmService atmService;
    private final EventService eventService;

    private final int accountPerTariffCount = 5;
    private final int tariffCount = 5;
    private final int closeRequestDuplicateAmount = 1;
    private final int cardAmount = 2;
    private final int departmentAmount = 15;
    private final int operatorAmount = 3;
    private final int managerAmount = 30;
    private final int cashBackCategoryAmount = 4;
    private final int cashBackCompanyAmount = 4;
    private final int atmRefillAmount = 5;
    private final int atmWithdrawAmount = 5;
    private final int cashBackAmount = 5;
    private final int interestsAmount = 5;
    private final int atmAmount = 3;
    private final int ratingAmount = 5;
    private final int eventAmount = 1;

    private final List<String> cashBackCategoriesNames = new ArrayList<>(cashBackCategoryAmount);
    private final List<String> cashBackCompaniesNames = new ArrayList<>(cashBackCompanyAmount);

    private List<Account> accounts = new ArrayList<>();
    private List<Operator> operators = new ArrayList<>();
    private List<Manager> managers = new ArrayList<>();
    private List<CashBackCompany> cashBackCompanies = new ArrayList<>();
    private List<CashBackCategory> cashBackCategories = new ArrayList<>();
    private final Map<Account, List<Deposit>> accountDepositMap = new HashMap<>();
    private final Map<CashBackCompany, List<Account>> cashBackCompanyAccountMap = new HashMap<>();
    private final List<Pair<String, String>> accountsName = new ArrayList<>();
    private final List<Atm> atmList = new ArrayList<>();
    private final List<Event> eventList = new ArrayList<>();

    private final Set<Long> accountIdThatHasADefaultCard = new HashSet<>();


    public TestDataInitServiceImpl(AccountService accountService, TariffService tariffService, SettingsService settingsService, DocumentScansService documentScansService, CloseRequestService closeRequestService, CardService cardService, DepositService depositService, OperatorService operatorService, ManagerService managerService, DepartmentService departmentService, OperatorRatingService operatorRatingService, CashBackCompanyService cashBackCompanyService, CashBackCategoryService cashBackCategoryService, AtmRefillService atmRefillService, AtmWithdrawService atmWithdrawService, CashBackService cashBackService, InterestsService interestsService, AtmService atmService, EventService eventService) {
        this.accountService = accountService;
        this.tariffService = tariffService;
        this.settingsService = settingsService;
        this.documentScansService = documentScansService;
        this.closeRequestService = closeRequestService;
        this.cardService = cardService;
        this.depositService = depositService;
        this.operatorService = operatorService;
        this.managerService = managerService;
        this.departmentService = departmentService;
        this.operatorRatingService = operatorRatingService;
        this.cashBackCompanyService = cashBackCompanyService;
        this.cashBackCategoryService = cashBackCategoryService;
        this.atmRefillService = atmRefillService;
        this.atmWithdrawService = atmWithdrawService;
        this.cashBackService = cashBackService;
        this.interestsService = interestsService;
        this.atmService = atmService;
        this.eventService = eventService;

        cashBackCategoriesNames.add("Кафе");
        cashBackCategoriesNames.add("Цветы");
        cashBackCategoriesNames.add("Развлечения");
        cashBackCategoriesNames.add("Подарки");

        cashBackCompaniesNames.add("Клумба");//цветы+подарки
        cashBackCompaniesNames.add("Наполеон");//торты на заказ(подарки+кафе)
        cashBackCompaniesNames.add("Антикафе№1");//кафе+развлечения
        cashBackCompaniesNames.add("Небо");//кинотеарт(развлечения+кино)

        accountsName.add(Pair.of("Иван", "Иванов"));
        accountsName.add(Pair.of("Петр", "Петров"));
        accountsName.add(Pair.of("Антон", "Антонов"));
        accountsName.add(Pair.of("Илья", "Ильин"));
        accountsName.add(Pair.of("Владимир", "Владимиров"));
    }

    @Transactional
    public void init() {
        addTariff();
        addDepartment();
        addOperatorRating();
        addCashBackCategory();
        addCashBackCompany();
        attachCashBackCompanyToCategories();

        addDataForManyToManyQuery();

        addAtm();
        addRefill();
        addWithdraw();
        addInterests();
        addCashBack();

        addEvents();
    }

    public void insertErrorData() {
        JoinColumn annotation = getAnnotationByClassAndFieldNameAndAnnotationClass(CloseRequest.class, "account", JoinColumn.class);
        if (Objects.isNull(annotation) || !annotation.unique()) {
            Account account = accounts.get(0);

            CloseRequest closeRequestDuplicate = new CloseRequest();
            closeRequestDuplicate.setCause("Длительное время ответа на заявки");
            closeRequestDuplicate.setAccount(account);
            closeRequestService.persist(closeRequestDuplicate);
        }
    }

    private void addAtm() {
        for (int i = 0; i < atmAmount; i++) {
            Atm atm = new Atm(
                    55d + RandomUtil.getRandomNumber(0.0001, 0.9),
                    37d + RandomUtil.getRandomNumber(0.0001, 0.9),
                    true
            );

            atmService.persist(atm);
            atmList.add(atm);
        }
    }

    private void addTariff() {
        for (int i = 0; i < tariffCount; i++) {
            Tariff tariff = new Tariff();
            tariff.setName("Тариф №" + i);
            tariffService.persist(tariff);
            addAccount(tariff);
        }
    }

    private void addDepartment() {
        for (int i = 0; i < departmentAmount; i++) {
            Department department = new Department();
            department.setName("Департамент" + (i+1));
            departmentService.persist(department);
            addManager(department);
            addOperator(department);
        }
    }

    private void addManager(Department department) {
        for (int i = 0; i < managerAmount; i++) {
            Manager manager = new Manager();
            manager.setPhone("0-000-000-00-00");
            manager.setPassportNumber("000-000");
            manager.setDepartment(department);
            managerService.persist(manager);
            managers.add(manager);
        }
    }

    int operatorSeed = 1;

    private void addOperator(Department department) {

        for (int i = 0; i < operatorAmount; i++) {

            String lastPhoneDigits = operatorSeed > 9 ? String.valueOf(operatorSeed) : "0" + operatorSeed;
            String lastPassportDigits = operatorSeed > 9 ? "0" + operatorSeed : "00" + operatorSeed;

            Pair<String, String> namePair = NameUtil.forSpace("operator").getNamePair();

            Operator operator = new Operator();
            operator.setFirstName(namePair.getFirst());
            operator.setLastName(namePair.getSecond());
            operator.setPhone("8-999-888-77-" + lastPhoneDigits);
            operator.setPassportNumber("000-" + lastPassportDigits);
            operator.setDepartment(department);
            operatorService.persist(operator);
            operators.add(operator);

            operatorSeed++;
        }
    }

    private void addAccount(Tariff tariff) {
        for (int i = 0; i < accountPerTariffCount; i++) {
            Account account = new Account();
            account.setTariff(tariff);
            account.setFirstName(accountsName.get(i).getFirst());
            account.setLastName(accountsName.get(i).getSecond());
            accountService.persist(account);
            accounts.add(account);
            addSettings(account);
            addDocumentScans(account);
            addCloseRequest(account);
            addCard(account);
            addDeposit(account);
        }
    }

    private void addSettings(Account account) {
        Settings settings = new Settings();
        settings.setAccount(account);
        settingsService.persist(settings);
    }

    private void addDocumentScans(Account account) {
        DocumentScans documentScans = new DocumentScans();
        documentScans.setInsuranceNumber("/scans/insuranceNumber/" + account.getId());
        documentScans.setITN("/scans/ITN/" + account.getId());
        documentScans.setPassport("/scans/passport/" + account.getId());
        documentScans.setAccount(account);
        documentScansService.persist(documentScans);
    }

    private void addCloseRequest(Account account) {
        CloseRequest closeRequest = new CloseRequest();
        closeRequest.setCause("Слишком дорогое обслуживание");
        closeRequest.setAccount(account);
        closeRequestService.persist(closeRequest);
    }

    private void addCard(Account account) {
        for (int i = 0; i < cardAmount; i++) {
            Card card = new Card();
            card.setCVV(NumberUtil.forSpace("cardCVV").getNextNumberForRank(3));
            card.setPIN(NumberUtil.forSpace("cardPIN").getNextNumberForRank(4));
            card.setAccount(account);

            if (!accountIdThatHasADefaultCard.contains(account.getId())) {
                accountIdThatHasADefaultCard.add(account.getId());
                card.setIsDefaultCard(true);
            }

            cardService.persist(card);
        }
    }

    private void addDeposit(Account account) {
        if (doesClassContainField(Deposit.class, "account")) {
            saveUnidirectionalDeposit(account, getOutDatedDeposit());
            saveUnidirectionalDeposit(account, getNormalDeposit());
        } else if (doesClassContainField(Account.class, "deposits")) {
            saveNoDirectionalDeposit(account, getOutDatedDeposit());
            saveNoDirectionalDeposit(account, getNormalDeposit());
        } else {
            throw new RuntimeException("Невозможно проинициализировать данные. В классе Deposit должно быть поле account " +
                    "либо в классе Account должно быть поле deposits");
        }
    }

    private void addEvents() {

        for (int i = 0; i < eventAmount; i++) {
            Event event = new Event();
            event.setName("Корпоратив");
            event.setDateTime(DateUtil.forSpace("events").getNextDateTime());
            eventService.persist(event);
            eventList.add(event);
        }

        boolean isOperatorsAdded = false;
        boolean isManagerAdded = false;

        if (doesClassContainField(Operator.class, "event")) {
            addAllOperatorToAllEvent();
            isOperatorsAdded = true;
        }

        if (doesClassContainField(Manager.class, "event")) {
            addAllMangerToAllEvent();
            isManagerAdded = true;
        }

        if (doesClassContainField(Employee.class, "event")) {

            if (!isManagerAdded) {
                addAllMangerToAllEvent();
            }

            if (!isOperatorsAdded) {
                addAllOperatorToAllEvent();
            }
        }
    }

    private void addAllOperatorToAllEvent() {
        for (Operator operator : operators) {
            operator.setEvent(eventList.get(0));
            operatorService.update(operator);
        }
    }

    private void addAllMangerToAllEvent() {
        for (Manager manager : managers) {
            manager.setEvent(eventList.get(0));
            managerService.update(manager);
        }
    }

    private Deposit getOutDatedDeposit() {
        Deposit deposit = new Deposit();
        deposit.setCloseDate(LocalDate.now().minusDays(6));
        deposit.setOpenDate(LocalDate.now().minusDays(365));
        deposit.setRate(2F);
        deposit.setSum(10000.0);
        return deposit;
    }

    private Deposit getNormalDeposit() {
        Deposit deposit = new Deposit();
        deposit.setCloseDate(LocalDate.now());
        deposit.setOpenDate(LocalDate.now().minusDays(6));
        deposit.setRate(2F);
        deposit.setSum(10000.0);
        return deposit;
    }

    private void saveUnidirectionalDeposit(Account account, Deposit deposit) {
        deposit.setAccount(account);
        depositService.persist(deposit);
        if (!accountDepositMap.containsKey(account)) {
            accountDepositMap.put(account, new ArrayList<>());
        }
        accountDepositMap.get(account).add(deposit);
    }

    private void saveNoDirectionalDeposit(Account account, Deposit deposit) {
        depositService.persist(deposit);
        account.getDeposits().add(deposit);
        accountService.update(account);

        if (!accountDepositMap.containsKey(account)) {
            accountDepositMap.put(account, new ArrayList<>());
        }
        accountDepositMap.get(account).add(deposit);
    }

    private void addOperatorRating() {

        int minRatingAmount = Math.min(operators.size(), accounts.size());

        double rating = 0;
        double ratingStep = 5.0/minRatingAmount;

        for (int i = 0; i < minRatingAmount; i++) {
            rating += ratingStep;

            for (int j = 0; j < ratingAmount; j++) {
                OperatorRating operatorRating = new OperatorRating();
                operatorRating.setAccount(accounts.get(i));
                operatorRating.setOperator(operators.get(i));
                operatorRating.setRating((int) rating);
                operatorRatingService.persist(operatorRating);
            }
        }
    }

    private void addCashBackCategory() {
        for (int i = 0; i < cashBackCategoryAmount; i++) {
            CashBackCategory cashBackCategory = new CashBackCategory();
            Manager manager = managers.get(0);
            cashBackCategory.setName(cashBackCategoriesNames.get(i));
            cashBackCategory.setUploader(manager);
            cashBackCategory.setEditor(manager);
            cashBackCategory.addAccount(accounts.get(0));
            cashBackCategory.addAccount(accounts.get(1));
            cashBackCategory.addAccount(accounts.get(2));
            cashBackCategoryService.persist(cashBackCategory);
            cashBackCategories.add(cashBackCategory);
        }
        addOtherAccountToCategories();
    }

    private void addOtherAccountToCategories() {
        for (int i = 0; i < cashBackCategories.size() / 2; i++) {
            CashBackCategory category = cashBackCategories.get(i);
            category.addAccount(accounts.get(3));
            category.addAccount(accounts.get(4));
        }
    }

    private void addCashBackCompany() {
        for (int i = 0; i < cashBackCompanyAmount; i++) {
            CashBackCompany cashBackCompany = new CashBackCompany();
            Manager manager = managers.get(0);
            cashBackCompany.setName(cashBackCompaniesNames.get(i));
            cashBackCompany.setUploader(manager);
            cashBackCompany.setEditor(manager);
            cashBackCompany.addAccount(accounts.get(0));
            cashBackCompany.addAccount(accounts.get(1));
            cashBackCompany.addAccount(accounts.get(2));
            cashBackCompanyService.persist(cashBackCompany);
            cashBackCompanies.add(cashBackCompany);

            cashBackCompanyAccountMap.merge(
                    cashBackCompany,
                    Arrays.asList(accounts.get(0), accounts.get(1), accounts.get(2)),
                    (oldList, newList) -> {
                        List<Account> accountList = new ArrayList<>(oldList.size() + newList.size());
                        accountList.addAll(oldList);
                        accountList.addAll(newList);
                        return accountList;
                    }
            );
        }
        addOtherAccountToCompanies();
    }

    private void addOtherAccountToCompanies() {
        for (int i = 0; i < cashBackCompanies.size() / 2; i++) {
            CashBackCompany cashBackCompany = cashBackCompanies.get(i);
            cashBackCompany.getAccounts().add(accounts.get(3));
            cashBackCompany.getAccounts().add(accounts.get(4));

            cashBackCompanyAccountMap.merge(
                    cashBackCompany,
                    Arrays.asList(accounts.get(3), accounts.get(4)),
                    (oldList, newList) -> {
                        List<Account> accountList = new ArrayList<>(oldList.size() + newList.size());
                        accountList.addAll(oldList);
                        accountList.addAll(newList);
                        return accountList;
                    }
            );
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
            cashBackCategoryService.update(category);
        }
    }

    private void addDataForManyToManyQuery() {
        CashBackCompany cashBackCompany = new CashBackCompany();
        Manager manager = managers.get(0);
        cashBackCompany.setName("Октябрь");
        cashBackCompany.setUploader(manager);
        cashBackCompany.setEditor(manager);
        cashBackCompany.addAccount(accounts.get(0));
        cashBackCompanyService.persist(cashBackCompany);

        CashBackCategory cashBackCategory = new CashBackCategory();
        cashBackCategory.setName("Кино");
        cashBackCategory.setUploader(manager);
        cashBackCategory.setEditor(manager);
        cashBackCategory.addAccount(accounts.get(1));
        cashBackCategory.addAccount(accounts.get(2));
        cashBackCategory.addCashBackCompany(cashBackCompany);
        cashBackCategoryService.persist(cashBackCategory);
    }

    private void addRefill() {
        for (Account account : accounts) {
            BigDecimal amount = BigDecimal.valueOf(500000);
            LocalDateTime dateTime = DateUtil.forSpace("atmRefill").getNextDateTime();
            Atm atm = atmList.get(RandomUtil.getRandomNumber(0, atmList.size()));

            AtmRefill atmRefill = new AtmRefill(
                    amount,
                    dateTime,
                    account,
                    atm
            );

            atmRefillService.persist(atmRefill);

            AtmTransactionUtilDto atmDto = AtmTransactionUtilDto.builder()
                    .id(atmRefill.getId())
                    .atmTransactionType(TransactionUtilType.REFILL)
                    .amount(amount)
                    .dateTime(dateTime)
                    .accountId(account.getId())
                    .atmId(atm.getId())
                    .build();

            TransactionUtilDto transactionDto = TransactionUtilDto.builder()
                    .id(atmRefill.getId())
                    .transactionType(TransactionUtilType.REFILL)
                    .amount(amount)
                    .dateTime(dateTime)
                    .accountId(account.getId())
                    .build();

            TransactionHolder.addAtmTransactionDto(account.getId(), atmDto);
            TransactionHolder.addTransactionDto(account.getId(), transactionDto);
        }
    }

    private void addWithdraw() {
        for (Account account : accounts) {
            BigDecimal amount = BigDecimal.valueOf(270000);
            LocalDateTime dateTime = DateUtil.forSpace("atmWithdraw").getNextDateTime();
            Atm atm = atmList.get(RandomUtil.getRandomNumber(0, atmList.size()));

            AtmWithdraw atmWithdraw = new AtmWithdraw(
                    amount,
                    dateTime,
                    account,
                    atm
            );

            atmWithdrawService.persist(atmWithdraw);

            AtmTransactionUtilDto dto = AtmTransactionUtilDto.builder()
                    .id(atmWithdraw.getId())
                    .atmTransactionType(TransactionUtilType.WITHDRAW)
                    .amount(amount)
                    .dateTime(dateTime)
                    .accountId(account.getId())
                    .atmId(atm.getId())
                    .build();

            TransactionUtilDto transactionDto = TransactionUtilDto.builder()
                    .id(atmWithdraw.getId())
                    .transactionType(TransactionUtilType.WITHDRAW)
                    .amount(amount)
                    .dateTime(dateTime)
                    .accountId(account.getId())
                    .build();

            TransactionHolder.addAtmTransactionDto(account.getId(), dto);
            TransactionHolder.addTransactionDto(account.getId(), transactionDto);
        }
    }

    private void addInterests() {
        for (Map.Entry<Account, List<Deposit>> accountDepositsEntry : accountDepositMap.entrySet()) {
            Account account = accountDepositsEntry.getKey();
            List<Deposit> depositList = accountDepositsEntry.getValue();

            for (Deposit deposit : depositList) {
                BigDecimal amount = BigDecimal.valueOf(deposit.getSum() * (deposit.getRate() / 100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                LocalDateTime dateTime = DateUtil.forSpace("interests").getNextDateTime();
                Interests interests = new Interests(amount, dateTime, account, deposit);

                interestsService.persist(interests);

                OtherProfitUtilDto dto = OtherProfitUtilDto.builder()
                        .id(interests.getId())
                        .otherProfitType(TransactionUtilType.INTERESTS)
                        .amount(amount)
                        .dateTime(interests.getDateTime())
                        .accountId(account.getId())
                        .build();

                TransactionUtilDto transactionDto = TransactionUtilDto.builder()
                        .id(interests.getId())
                        .transactionType(TransactionUtilType.INTERESTS)
                        .amount(amount)
                        .dateTime(dateTime)
                        .accountId(account.getId())
                        .build();

                TransactionHolder.addOtherProfitDto(account.getId(), dto);
                TransactionHolder.addTransactionDto(account.getId(), transactionDto);
            }
        }
    }

    private void addCashBack() {
        for (Map.Entry<CashBackCompany, List<Account>> cashBackCompanyAccountEntry : cashBackCompanyAccountMap.entrySet()) {
            CashBackCompany cashBackCompany = cashBackCompanyAccountEntry.getKey();
            List<Account> accountList = cashBackCompanyAccountEntry.getValue();

            for (Account account : accountList) {

                for (int i = 0; i < cashBackAmount; i++) {
                    BigDecimal amount = BigDecimal.valueOf(10 + (double) i/10).setScale(0, BigDecimal.ROUND_HALF_UP);
                    LocalDateTime dateTime = DateUtil.forSpace("cashBack").getNextDateTime();
                    CashBack cashBack = new CashBack(amount, dateTime, account, cashBackCompany);

                    cashBackService.persist(cashBack);

                    OtherProfitUtilDto dto = OtherProfitUtilDto.builder()
                            .id(cashBack.getId())
                            .otherProfitType(TransactionUtilType.CASH_BACK)
                            .amount(cashBack.getAmount())
                            .dateTime(dateTime)
                            .accountId(account.getId())
                            .build();

                    TransactionUtilDto transactionDto = TransactionUtilDto.builder()
                            .id(cashBack.getId())
                            .transactionType(TransactionUtilType.CASH_BACK)
                            .amount(amount)
                            .dateTime(dateTime)
                            .accountId(account.getId())
                            .build();

                    TransactionHolder.addOtherProfitDto(account.getId(), dto);
                    TransactionHolder.addTransactionDto(account.getId(), transactionDto);
                }
            }
        }
    }

//    private void addCashBack(Account account) {
//        Deposit deposit = accountDepositMap.get(account);
//    }


    public boolean doesClassContainField(Class<?> objectClass, String fieldName) {
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    private <T extends Annotation> T getAnnotationByClassAndFieldNameAndAnnotationClass(Class<?> objectClass, String fieldName, Class<T> annotationClass) {
        try {
            return objectClass.getDeclaredField(fieldName).getDeclaredAnnotation(annotationClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
