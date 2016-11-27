package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcProductDao extends JdbcDao<Product> implements ProductDao{

    public JdbcProductDao(Connection connection) {
        super(connection);
    }

    @Override
    protected Product createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException {
        try {
            if (rs.next()){

            }else {
                throw new JdbcException("no one product was found");
            }
        } catch (SQLException e) {
            log.error("creating product entity from result set was failed");
            throw new JdbcException(e);
        }

        return new Product();
    }

    @Override
    protected void setPsFields(PreparedStatement ps, Product entity) throws JdbcException {

    }

    @Override
    protected String getSelectQueryById() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getInsertQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }
}
