package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductCategoryDao;
import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;
import com.epam.tm.shop.exception.JdbcException;
import com.epam.tm.shop.exception.JdbcNoDataException;
import com.epam.tm.shop.util.ConstantHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductCategory extends JdbcDao<ProductCategory> implements ProductCategoryDao {
    private static final Logger log = LoggerFactory.getLogger(JdbcProductCategory.class);
    private static final String INSERT_QUERY = "INSERT INTO categories VALUES(DEFAULT,?,?)";
    private static final String UPDATE_QUERY = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM categories WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM categories";
    private static final String DELETE_QUERY = "DELETE FROM categories WHERE id = ?";
    private static final String SELECT_QUERY_BY_NAME = "SELECT * FROM categories WHERE name = ?";

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String DESCRIPTION_COLUMN_NAME = "description";

    public JdbcProductCategory(Connection connection) {
        super(connection);
    }

    @Override
    protected List<ProductCategory> createEntityFromResultSet(ResultSet rs) throws SQLException, JdbcException, JdbcNoDataException {
        List<ProductCategory> productCategories = new ArrayList<>();
        try {
            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(rs.getInt(ID_COLUMN_NAME));
                productCategory.setName(rs.getString(NAME_COLUMN_NAME));
                productCategory.setDescription(rs.getString(DESCRIPTION_COLUMN_NAME));
                productCategories.add(productCategory);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating product category entity from result set was failed", e);
        }

        if (productCategories.size() == ConstantHolder.EMPTY_LIST_SIZE)
            throw new JdbcNoDataException("no one product category was found");

        return productCategories;
    }


    @Override
    protected void setPsFields(PreparedStatement ps, ProductCategory entity) throws JdbcException {
        try {
            ps.setString(ConstantHolder.FIRST_INDEX, entity.getName());
            ps.setString(ConstantHolder.SECOND_INDEX, entity.getDescription());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(ConstantHolder.THIRD_INDEX, entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set product category entity to ps was failed", e);
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
    public ProductCategory findProductCategoryByName(String name) throws JdbcException, JdbcNoDataException {
        List<ProductCategory> productCategories;

        try {
            productCategories = findByString(name, SELECT_QUERY_BY_NAME);
        } catch (JdbcException e) {
            log.debug("finding product category by name = {} was failed", name);
            throw new JdbcException(e);
        }

        return productCategories.get(ConstantHolder.FIRST_ELEMENT_IN_LIST);
    }

    @Override
    public List<ProductCategory> getAllProductCategory() throws DaoException, DaoNoDataException {
        return findAll(SELECT_ALL_QUERY);
    }
}
