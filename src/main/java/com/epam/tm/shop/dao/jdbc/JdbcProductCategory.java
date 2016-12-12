package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductCategoryDao;
import com.epam.tm.shop.entity.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductCategory extends JdbcDao<ProductCategory> implements ProductCategoryDao {

    private static final String INSERT_QUERY = "INSERT INTO categories VALUES(DEFAULT,?,?)";
    private static final String UPDATE_QUERY = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM categories WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM categories WHERE id = ?";
    private static final String SELECT_QUERY_BY_NAME = "SELECT * FROM categories WHERE name = ?";

    public JdbcProductCategory(Connection connection) {
        super(connection);
    }

    @Override
    protected List<ProductCategory> createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException {
        List<ProductCategory> productCategories = new ArrayList<>();
        try {
            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(rs.getInt("id"));
                productCategory.setName(rs.getString("name"));
                productCategory.setDescription(rs.getString("description"));
                productCategories.add(productCategory);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating product category entity from result set was failed",e);
        }

        if (productCategories.size() == 0)
            throw new JdbcException("no one product category was found");

        return productCategories;
    }


    @Override
    protected void setPsFields(PreparedStatement ps, ProductCategory entity) throws JdbcException {
        try {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(3, entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set product category entity to ps was failed",e);
        }
    }

    @Override
    protected String getSelectQueryById() {
        return SELECT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    public ProductCategory findProductCategoryByName(String name) throws JdbcException {
        List<ProductCategory> ProductCategories;

        try {
            ProductCategories = findByString(name, SELECT_QUERY_BY_NAME);
        } catch (JdbcException e) {
            log.debug("finding product category by name = {} was failed",name);
            throw new JdbcException(e);
        }

        return ProductCategories.get(0);
    }

}
