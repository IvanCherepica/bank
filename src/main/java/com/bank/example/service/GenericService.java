package com.bank.example.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GenericService<PK extends Serializable, T> {

    void persist(T entity);

    T getByKey(PK id);

    void update(T e);

    void remove(T entity);

    List<T> getAll();

    void persistAll(Collection<T> entities);

    void updateAll(Collection<T> entities);

    List<T> getAllByIds(List<PK> ids);
}
