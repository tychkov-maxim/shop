package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.BaseEntity;

public interface Dao<T extends BaseEntity> {
    T save(T entity) throws JdbcException;
    T findById(int id) throws JdbcException;
    void delete(T entity);
}
