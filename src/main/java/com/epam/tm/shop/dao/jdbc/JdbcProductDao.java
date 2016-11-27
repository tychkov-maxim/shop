package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.entity.ProductCategory;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao extends JdbcDao<Product> implements ProductDao{


    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, role = ?, account = ?, account_unit = ?, address = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM products JOIN categories ON products.category_id = categories.id WHERE products.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_QUERY_BY_LOGIN = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.login = ?";

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
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(Money.of(CurrencyUnit.getInstance(rs.getString("price_unit")),rs.getBigDecimal("price")));
                product.setImagePath(rs.getString("image_path"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductCategory(new ProductCategory(rs.getInt("categories.id"),rs.getString("categories.name"),rs.getString("categories.description")));
                products.add(product);
            }
        } catch (SQLException e) {
            log.error("creating product entity from result set was failed");
            throw new JdbcException(e);
        }

        if (products.size() == 0)
            throw new JdbcException("no one product was found");

        return products;
    }


    @Override
    protected void setPsFields(PreparedStatement ps, Product entity) throws JdbcException {

    }

    @Override
    protected String getSelectQueryById() {
        return SELECT_QUERY;
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

    // FIXME: 22.11.2016
    public void setCon(Connection con){
        this.connection = con;
    }
}
