package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcNoDataException;
import com.epam.tm.shop.dao.jdbc.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.BaseEntity;

public interface Dao<T extends BaseEntity> {
    T save(T entity) throws DaoException, JdbcNonUniqueFieldException;
    T findById(int id) throws DaoException, DaoNoDataException;
    void delete(T entity) throws DaoException;
    void deleteById(int id) throws DaoException;
}
