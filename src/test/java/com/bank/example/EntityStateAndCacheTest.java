package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.model.Operator;
import com.bank.example.model.Tariff;
import com.bank.example.service.OperatorService;
import com.bank.example.service.TariffService;
import org.hibernate.Session;
import org.hibernate.internal.AbstractSessionImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = BankApplication.class)
class EntityStateAndCacheTest extends BaseTest {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OperatorService operatorService;

    private Tariff createTariff() {
        Tariff tariff = new Tariff();
        tariff.setName("New tariff");
        return tariff;
    }

    private Tariff persistTariffAndReturn() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        em.flush();
        return tariff;
    }

    private UUID getUUIDFromSession() {
        AbstractSessionImpl abstractSession = em.unwrap(AbstractSessionImpl.class);
        return abstractSession.getSessionIdentifier();
    }

    private Session getSession() {
        return em.unwrap(Session.class);
    }

    @Test
    @Commit
    void testTransientAndManagedStates() {
        UUID uuid = getUUIDFromSession();

        Tariff tariff = createTariff();
        boolean contains = em.contains(tariff);
        tariffService.persist(tariff); // breakpoint line
        em.flush();
        contains = em.contains(tariff);

        tariff.setName("TestManagedName"); // breakpoint line
    }

    @Test
    @Commit
    void testManagedAfterFindMethod() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        Long id = tariff.getId();
        em.flush();
        em.clear();

        tariff = tariffService.getByKey(id);
        tariff.setName("TestManagedName2");
    }

    @Test
    @Commit
    void testDetachedState() {
        UUID uuid = getUUIDFromSession();

        Tariff tariff = createTariff();

        tariffService.persist(tariff);
        em.flush();
        boolean contains = em.contains(tariff);
        em.detach(tariff); // breakpoint line
        contains = em.contains(tariff);
        tariff.setName("TestDetachName"); // breakpoint line
    }

    @Test
    @Commit
    void testDetachedStateWithClearMethod() {
        UUID uuid = getUUIDFromSession();

        Tariff tariff = createTariff();

        tariffService.persist(tariff);
        em.flush();
        boolean contains = em.contains(tariff);
        em.clear(); // breakpoint line
        contains = em.contains(tariff);
        tariff.setName("TestDetachName"); // breakpoint line
    }

    @Test
    @Commit
    void testRemovedState() {
        UUID uuid = getUUIDFromSession();

        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        em.flush();

        boolean contains = em.contains(tariff);
        tariffService.remove(tariff); //breakpoint line
        contains = em.contains(tariff);
        System.out.println(contains); //breakpoint line
    }

    @Test
    @Commit
    void exercise1_1() {
        Tariff tariff = createTariff();
    }

    @Test
    @Commit
    void exercise1_2() {
        Tariff tariff = persistTariffAndReturn();
        em.detach(tariff);
        tariff.setName("Actual name");
    }

    @Test
    @Commit
    void exercise1_3() {
        Tariff tariff = persistTariffAndReturn();
    }

    @Test
    @Commit
    void testManagedStateForCache() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        Long id = tariff.getId();
        em.flush();
        em.clear();

        tariff = tariffService.getByKey(id);

        tariff.setName("New name");
        tariff.setName("Name");
        tariff.setName("Last try");
    }

    @Test
    @Commit
    void testGetNotFromCache() {
        Tariff tariff1 = createTariff();
        Tariff tariff2 = createTariff();
        Tariff tariff3 = createTariff();
        tariffService.persist(tariff1);
        tariffService.persist(tariff2);
        tariffService.persist(tariff3);
        Long id1 = tariff1.getId();
        Long id2 = tariff2.getId();
        Long id3 = tariff3.getId();
        em.flush();
        em.clear();

        tariffService.getByKey(id1);
        tariffService.getByKey(id2); // breakpoint line
        tariffService.getByKey(id3); // breakpoint line
        System.out.println("The end"); // breakpoint line
    }

    @Test
    @Commit
    void testCreateReadDelete() {
        Session session = getSession();

        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        em.flush();

        UUID uuid = getUUIDFromSession();

        Tariff tariff2 = createTariff(); //breakpoint line
        tariffService.persist(tariff2);
        em.flush();

        UUID uuid2 = getUUIDFromSession();
        Assert.assertEquals(uuid, uuid2);

        Tariff tariff3 = createTariff(); //breakpoint line
        tariffService.persist(tariff3);
        em.flush();

        UUID uuid3 = getUUIDFromSession();
        Assert.assertEquals(uuid2, uuid3);

        List<Tariff> tariffList = tariffService.getAll(); //breakpoint line

        UUID uuid4 = getUUIDFromSession();
        Assert.assertEquals(uuid3, uuid4);

        for (Tariff tariffForFor : tariffList) { //breakpoint line
            tariffService.remove(tariffForFor);
        }

        UUID uuid5 = getUUIDFromSession();
        Assert.assertEquals(uuid4, uuid5); //breakpoint line
    }

    @Test
    @Commit
    void exercise2_1() {
        Tariff tariff = persistTariffAndReturn();
        tariff.setName("Second name");
        em.flush();
        tariff.setName("Third name");
        em.flush();
        tariff.setName("Last name");
    }

    @Test
    @Commit
    void exercise2_2() {
        Tariff tariff = persistTariffAndReturn();
        em.find(Tariff.class, tariff.getId());
    }

    @Test
    @Commit
    void exercise2_3() {
        Tariff tariff = persistTariffAndReturn();
        em.detach(tariff);
        tariff = em.find(Tariff.class, tariff.getId());
        em.clear();
        tariff = em.find(Tariff.class, tariff.getId());
        em.detach(tariff);
        em.find(Tariff.class, tariff.getId());
    }

    @Test
    @Commit
    void testGetEntityByFindMethod() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        boolean contains = em.contains(tariff);
        Long id = tariff.getId(); // breakpoint line
        em.flush();
        em.clear();
        contains = em.contains(tariff);

        tariff = em.find(Tariff.class, id); // breakpoint line
        contains = em.contains(tariff);

        tariff = em.find(Tariff.class, id); // breakpoint line
        tariff = em.find(Tariff.class, id);
    }

    @Test
    @Commit
    void testGetEntityByHQLQuery() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        boolean contains = em.contains(tariff);
        Long id = tariff.getId(); // breakpoint line
        em.flush();
        em.clear();
        contains = em.contains(tariff);

        tariff = em.createQuery("from Tariff t where t.id=:id", Tariff.class) // breakpoint line
                .setParameter("id", id)
                .getSingleResult();
        contains = em.contains(tariff);

        tariff = em.createQuery("from Tariff t where t.id=:id", Tariff.class) // breakpoint line
                .setParameter("id", id)
                .getSingleResult();
        tariff = em.createQuery("from Tariff t where t.id=:id", Tariff.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Test
    @Commit
    void testGetEntityBySQLQuery() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        boolean contains = em.contains(tariff);
        Long id = tariff.getId(); // breakpoint line
        em.flush();
        em.clear();
        contains = em.contains(tariff);

        tariff = (Tariff) em.createNativeQuery("SELECT * FROM tariff WHERE id=:id", Tariff.class) // breakpoint line
                .setParameter("id", id)
                .getSingleResult();
        contains = em.contains(tariff);

        tariff = (Tariff) em.createNativeQuery("SELECT * FROM tariff WHERE id=:id", Tariff.class) // breakpoint line
                .setParameter("id", id)
                .getSingleResult();
        tariff = (Tariff) em.createNativeQuery("SELECT * FROM tariff WHERE id=:id", Tariff.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Test
    @Commit
    void testGetEntityByFindMethodAndQueries() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);
        boolean contains = em.contains(tariff);
        Long id = tariff.getId();
        em.flush();
        em.clear();
        contains = em.contains(tariff);

        tariff = (Tariff) em.createNativeQuery("SELECT * FROM tariff WHERE id=:id", Tariff.class).setParameter("id", id).getSingleResult();
        contains = em.contains(tariff);

        tariff = em.createQuery("from Tariff t where t.id=:id", Tariff.class).setParameter("id", id).getSingleResult();
        tariff = em.find(Tariff.class, id);
    }

    @Test
    @Commit
    void exercise3_1() {
        Tariff tariff = persistTariffAndReturn();
        em.clear();
        Long id = tariff.getId();

        tariff = em.find(Tariff.class, id);

        tariff = (Tariff) em.createNativeQuery("SELECT * FROM tariff WHERE id=:id", Tariff.class).setParameter("id", id).getSingleResult();

        tariff = em.createQuery("from Tariff t where t.id=:id", Tariff.class).setParameter("id", id).getSingleResult();
    }

    @Test
    @Commit
    void testFlushWithExecutingHQLQuery() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);

        em.createQuery("select count(id) from Manager").getSingleResult();

        em.createQuery("select count(id) from Tariff").getSingleResult();
    }

    @Test
    @Commit
    void testFlushWithExecutingHQLQuery2() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);

        em.createQuery("select count(id) from Manager").getSingleResult();

        em.createQuery("select count(id) from Operator").getSingleResult();
    }

    @Test
    @Commit
    void testFlushWithExecutingHQLQueryFewPersistentEntities() {
        Operator operator = new Operator();
        operatorService.persist(operator);

        Tariff tariff = createTariff();
        tariffService.persist(tariff);

        Tariff tariff2 = createTariff();
        tariffService.persist(tariff2);
        Long id = tariff.getId();

        em.createQuery("select count(id) from Manager").getSingleResult();

        em.createQuery("select t from Tariff t where t.id = :id").setParameter("id", id).getSingleResult();
    }

    @Test
    @Commit
    void testFlushWithExecutingSQLQuery() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);

        em.createNativeQuery("SELECT count(id) FROM manager").getSingleResult();

        em.createNativeQuery("SELECT count(id) FROM tariff").getSingleResult();
    }

    @Test
    @Commit
    void testFlushWithExecutingHQLAndSQLQuery() {
        Tariff tariff = createTariff();
        tariffService.persist(tariff);

        em.createQuery("select count(id) from Manager").getSingleResult();

        em.createNativeQuery("SELECT count(id) FROM operator").getSingleResult();
    }

    @Test
    @Commit
    void exercise4_1() {
        Tariff tariff1 = createTariff();
        tariffService.persist(tariff1);
        Tariff tariff2 = createTariff();
        tariffService.persist(tariff2);

        em.createNativeQuery("SELECT count(id) FROM manager").getSingleResult();
    }
}
