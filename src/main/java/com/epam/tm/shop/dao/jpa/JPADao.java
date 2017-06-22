package com.epam.tm.shop.dao.jpa;

import com.epam.tm.shop.dao.Dao;
import com.epam.tm.shop.entity.BaseEntity;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.JdbcNonUniqueFieldException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public abstract class JPADao<T extends BaseEntity> implements Dao<T> {

    @PersistenceContext(name = "DEVMODE")
    protected EntityManager entityManager;

    @Override
    public T save(T entity) throws DaoException, JdbcNonUniqueFieldException {
        entityManager.persist(entity);
        return entity;
    }
}
