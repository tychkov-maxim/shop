package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcCartDao extends JdbcDao<Cart> implements CartDao {

    private static final String INSERT_QUERY = "INSERT INTO carts VALUES(?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM carts WHERE cart_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM carts WHERE cart_id = ?";


    public JdbcCartDao(Connection connection) {
        super(connection);
    }

    @Override
    public Cart save(Cart entity) throws JdbcException {
        throw new UnsupportedOperationException("not supported, we have insert and update");
    }

    //// FIXME: 27.11.2016
    @Override
    public Cart insert(Cart cart, int id) throws JdbcException {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(INSERT_QUERY);

            for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
                ps.setInt(1,id);
                ps.setInt(2,entry.getKey().getId());
                ps.setInt(3,entry.getValue());
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            log.error("inserting cart:{} was failed",cart,e);
            throw new JdbcException(e);
        }

        cart.setId(id);
        return cart;
    }

    @Override
    public Cart update(Cart cart) throws JdbcException {
        deleteById(cart.getId());
        return insert(cart,cart.getId());
    }


    @Override
    protected void setPsFields(PreparedStatement ps, Cart entity) throws JdbcException {
    }


    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    @Override
    protected String getSelectQueryById() {
        return SELECT_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }
    @Override
    protected List<Cart> createEntityFromResultSet(ResultSet rs) throws JdbcException {
        List<Cart> carts = new ArrayList<>();
        Cart cart = new Cart();
        Map<Product,Integer> map = new HashMap<>();
        Integer id = null;
        try {
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                map.put(product,rs.getInt("quantity"));
                id = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            log.error("creating cart entity from result set was failed");
            throw new JdbcException(e);
        }

        cart.setId(id);
        cart.setCart(map);

        if (cart.getCart().size() == 0)
            throw new JdbcException("no one cart was found");

        carts.add(cart);
        return carts;
    }


}
