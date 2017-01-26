package com.epam.tm.shop.dao;

import com.epam.tm.shop.exception.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.BaseEntity;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;

public interface Dao<T extends BaseEntity> {
    T save(T entity) throws DaoException, JdbcNonUniqueFieldException;

    T findById(int id) throws DaoException, DaoNoDataException;

    void delete(T entity) throws DaoException;

    void deleteById(int id) throws DaoException;
}
