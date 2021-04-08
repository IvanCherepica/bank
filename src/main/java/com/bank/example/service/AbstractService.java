package com.bank.example.service;

import com.bank.example.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class AbstractService<PK extends Serializable, T> {

    private GenericDao<PK, T> genericDao;

    public AbstractService(GenericDao<PK, T> genericDao) {
        this.genericDao = genericDao;
    }

    @Transactional
    public void persist(T entity) {
        this.genericDao.persist(entity);
    }

    @Transactional(readOnly = true)
    public T getByKey(PK id) {
        return this.genericDao.getByKey(id);
    }

    @Transactional
    public void update(T e) {
        this.genericDao.update(e);
    }

    @Transactional
    public void remove(T entity) {
        this.genericDao.remove(entity);
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        return this.genericDao.getAll();
    }

    @Transactional
    public void persistAll(Collection<T> entities) {
        genericDao.persistAll(entities);
    }

    @Transactional
    public void updateAll(Collection<T> entities) {
        genericDao.updateAll(entities);
    }

    public List<T> getAllByIds(List<PK> ids) {
        return genericDao.getAllByIds(ids);
    }
}
