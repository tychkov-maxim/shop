package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.Dao;
import com.epam.tm.shop.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    public static final Logger log = LoggerFactory.getLogger(JdbcDao.class);
    private static final int ERROR_CODE_OF_NON_UNIQUE_FIELD = 23505;

    protected Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity) throws JdbcException, JdbcNonUniqueFieldException {
        String query;

        log.trace("start to save entity {}", entity);

        if (entity.getId() == null) {
            query = getInsertQuery();
        } else
            query = getUpdateQuery();

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);

            setPsFields(ps, entity);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next())
                entity.setId(generatedKeys.getInt(1));
            ps.close();

            log.trace("saving entity {} was finished successfully", entity);
        } catch (SQLException e) {
            if (e.getErrorCode() == ERROR_CODE_OF_NON_UNIQUE_FIELD)
                throw new JdbcNonUniqueFieldException(e);
            throw new JdbcException(MessageFormat.format("saving entity:{0} was failed", entity), e);
        }
        return entity;
    }

    @Override
    public T findById(int id) throws JdbcException, JdbcNoDataException {
        return findAllById(id, getSelectQueryById()).get(0);
    }


    @Override
    public void delete(T entity) throws JdbcException {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(int id) throws JdbcException {
        log.trace("start to delete entity by id {}", id);
        try {
            PreparedStatement ps = connection.prepareStatement(getDeleteQuery());
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            log.trace("deleting entity by id {} was finished successfully", id);
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("deleting entity by id = {0} was failed", id), e);
        }
    }

    protected List<T> findByString(String key, String query) throws JdbcException, JdbcNoDataException {
        List<T> entities;
        log.trace("start to find entities by parameter {}", key);
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            entities = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding entities by parameter: {} was finished successfully", key);
            return entities;
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("finding entity by parameter = {0} was failed", key), e);
        }
    }


    protected List<T> findAllById(int id, String query) throws JdbcException, JdbcNoDataException {
        List<T> entities;
        log.trace("start to find entities by id {}", id);
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            entities = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding entities by id {} was finished successfully", id);
            return entities;
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("finding entities by id = {0} was failed", id), e);
        }
    }

    protected List<T> findAll(String query) throws JdbcException, JdbcNoDataException {
        List<T> entities;
        log.trace("start to find entities");
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            entities = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding entities was finished successfully");
            return entities;
        } catch (SQLException e) {
            throw new JdbcException("finding entities was failed", e);
        }
    }

    protected abstract List<T> createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException, JdbcNoDataException;

    protected abstract void setPsFields(PreparedStatement ps, T entity) throws JdbcException;

    protected abstract String getSelectQueryById();

    protected abstract String getUpdateQuery();

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

}
