package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao extends JdbcDao<Product> implements ProductDao{

    public JdbcProductDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Product> createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException {
        List<Product> products = new ArrayList<>();
        try {
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            log.error("creating user entity from result set was failed");
            throw new JdbcException(e);
        }

        if (products.size() == 0)
            throw new JdbcException("no one user was found");

        return products;
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
