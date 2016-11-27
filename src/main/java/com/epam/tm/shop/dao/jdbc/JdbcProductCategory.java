package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductCategoryDao;
import com.epam.tm.shop.entity.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcProductCategory extends JdbcDao<ProductCategory> implements ProductCategoryDao{

    public JdbcProductCategory(Connection connection) {
        super(connection);
    }

    @Override
    protected List<ProductCategory> createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException {
        return null;
    }

    @Override
    protected void setPsFields(PreparedStatement ps, ProductCategory entity) throws JdbcException {

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
