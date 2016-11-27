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
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next())//fixme need to know
                entity.setId(generatedKeys.getInt(1));
            ps.close();
        } catch (SQLException e) {
            log.error("saving entity:{} was failed",entity,e);
            throw new JdbcException(e);
        }
        return entity;
    }

    @Override
    public T findById(int id) throws JdbcException {
        T entity = null;
        try {
            PreparedStatement ps = connection.prepareStatement(getSelectQueryById());
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            entity = createEntityFromResultSet(rs);
            ps.close();
        } catch (SQLException e) {
            log.error("finding entity by id = {} was failed",id,e);
            throw new JdbcException(e);
        }
        return entity;
    }

    @Override
    public void delete(T entity) throws JdbcException {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(int id) throws JdbcException {
        try {
            PreparedStatement ps = connection.prepareStatement(getDeleteQuery());
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            log.error("deleting entity by id = {} was failed",id,e);
            throw new JdbcException(e);
        }
    }

    protected abstract T createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException;
    protected abstract void setPsFields(PreparedStatement ps,T entity) throws JdbcException;
    protected abstract String getSelectQueryById();
    protected abstract String getUpdateQuery();
    protected abstract String getInsertQuery();
    protected abstract String getDeleteQuery();

}
