package com.bank.example.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GenericDao<PK extends Serializable, T> {

    void persist(T entity);

    T getByKey(PK id);

    void update(T e);

    void remove(T entity);

    List<T> getAll();

    List<T> getAllByIds(List<PK> ids);

    void persistAll(Collection<T> entities);

    void updateAll(Collection<T> entities);
}
