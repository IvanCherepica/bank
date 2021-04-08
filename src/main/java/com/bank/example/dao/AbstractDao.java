package com.bank.example.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;


public abstract class AbstractDao<PK extends Serializable, T> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:1000}")
    private int batchSize;

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return entityManager.find(persistentClass, key);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public List<T> getAll() {
        return entityManager.createQuery("SELECT pc FROM " + persistentClass.getName() + " pc", persistentClass).getResultList();
    }

    public List<T> getAllByIds(List<PK> ids) {
        return entityManager.createQuery(
                "SELECT pc FROM " + persistentClass.getName() + " pc WHERE pc.id IN :ids",
                persistentClass
        )
                .setParameter("ids", ids)
                .getResultList();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(Collection<T> entities) {
        int i = 0;
        for (T entity : entities) {
            entityManager.persist(entity);
            i++;
            if (i % batchSize == 0) {
                entityManager.flush();
            }
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateAll(Collection<T> entities) {
        int i = 0;
        for (T entity : entities) {
            entityManager.merge(entity);
            i++;
            if (i % batchSize == 0) {
                entityManager.flush();
            }
        }
    }
}