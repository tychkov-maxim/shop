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


    private static final String INSERT_QUERY = "INSERT INTO products VALUES(DEFAULT,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE products SET name = ?, description = ?, price = ?, price_unit = ?, category_id = ?, image_path = ?, quantity = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM products JOIN categories ON products.category_id = categories.id WHERE products.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String SELECT_QUERY_BY_CART_ID = "SELECT * FROM carts " +
            "JOIN products ON carts.product_id = products.id " +
            "JOIN categories ON products.category_id = categories.id where cart_id = ?";
    public static final String SELECT_QUERY_BY_CATEGORY = "SELECT * FROM products JOIN categories ON products.category_id = categories.id WHERE categories.name = ?";

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
            throw new JdbcException("creating product entity from result set was failed",e);
        }

        if (products.size() == 0)
            throw new JdbcException("no one product was found");

        return products;
    }


    @Override
    protected void setPsFields(PreparedStatement ps, Product entity) throws JdbcException {
        try {
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getDescription());
            ps.setBigDecimal(3,entity.getPrice().getAmount());
            ps.setString(4,entity.getPrice().getCurrencyUnit().toString());
            ps.setInt(5,entity.getProductCategory().getId());
            ps.setString(6,entity.getImagePath());
            ps.setInt(7,entity.getQuantity());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(8,entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set product entity to ps was failed",e);
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
    public List<Product> getAllProductsByCartId(int cartId) throws JdbcException {
        try {
            return findAllById(cartId,SELECT_QUERY_BY_CART_ID);
        } catch (JdbcException e) {
            throw new JdbcException(e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws JdbcException {
        try {
            return findByString(category,SELECT_QUERY_BY_CATEGORY);
        } catch (JdbcException e) {
            throw new JdbcException(e);
        }
    }
}
