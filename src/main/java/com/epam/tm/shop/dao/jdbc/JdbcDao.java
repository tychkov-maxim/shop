package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.Dao;
import com.epam.tm.shop.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    public static final Logger log = LoggerFactory.getLogger(JdbcDao.class);

    protected Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity) throws JdbcException {
        String query;

        if (entity.getId() == null) {
            query = getInsertQuery();
        }else
            query = getUpdateQuery();

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);
            setPsFields(ps,entity);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            log.error("SQLException, when trying to save {}",entity,e);
            throw new JdbcException(e);
        }
        return entity;
    }

    @Override
    public T findById(int id) throws JdbcException {
        T entity = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getSelectQueryById(id));
            entity = createEntityFromResultSet(rs);
        } catch (SQLException e) {
            log.error("SQLException, when trying to find entity by id {}",id,e);
            throw new JdbcException(e);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {

    }

    abstract void setPsFields(PreparedStatement ps,T entity);
    abstract String getInsertQuery();
    abstract String getUpdateQuery();
    abstract String getSelectQueryById(int id);
    abstract T createEntityFromResultSet(ResultSet rs);
}
