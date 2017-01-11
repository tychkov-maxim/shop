package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JdbcCartDao extends JdbcDao<Cart> implements CartDao {


    private static final String INSERT_CART_QUERY = "INSERT INTO carts VALUES(DEFAULT)";
    private static final String INSERT_QUERY = "INSERT INTO cart_to_products VALUES(?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM cart_to_products WHERE cart_id = ?";
    private static final String SELECT_QUERY_BY_ORDER_STATUS = "SELECT * FROM cart_to_products JOIN orders on cart_to_products.cart_id = orders.cart_id WHERE orders.order_status = ?";
    private static final String DELETE_QUERY = "DELETE FROM cart_to_products WHERE cart_id = ?";


    public JdbcCartDao(Connection connection) {
        super(connection);
    }

    @Override
    public Cart save(Cart entity) throws JdbcException {
        if (entity.getId() == null){
            PreparedStatement ps;
            AtomicInteger id = new AtomicInteger();
            try {
                ps = connection.prepareStatement(INSERT_CART_QUERY);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                     id.set(generatedKeys.getInt(1));
                }
                ps.close();
                return insert(entity, id.get());
            } catch (SQLException e){
                throw new JdbcException(e);
            }
        }else
            return update(entity);
    }


    private Cart insert(Cart cart, int id) throws JdbcException {
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
            throw new JdbcException("inserting cart was failed",e);
        }

        cart.setId(id);
        return cart;
    }

    private Cart update(Cart cart) throws JdbcException {
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
    protected List<Cart> createEntityFromResultSet(ResultSet rs) throws JdbcException, JdbcNoDataException {
        List<Cart> carts = new ArrayList<>();
        Cart cart = new Cart();
        Map<Product,Integer> map = new HashMap<>();
        Integer id = null, oldId = null;
        try {
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                map.put(product,rs.getInt("quantity"));
                id = rs.getInt("cart_id");
                if ( oldId != null ) {
                    if (!oldId.equals(id)) {
                        cart.setId(id);
                        cart.setCart(map);
                        carts.add(cart);
                        map = new HashMap<>();
                        cart = new Cart();
                    }
                }
                oldId = id;
            }
        } catch (SQLException e) {
            throw new JdbcException("creating cart entity from result set was failed",e);
        }

        cart.setId(id);
        cart.setCart(map);

        if (cart.getCart().size() == 0)
            throw new JdbcNoDataException("no one cart was found");

        carts.add(cart);
        return carts;
    }

    @Override
    public List<Cart> findAllCartsByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
       return findAllById(orderStatus.getId(),SELECT_QUERY_BY_ORDER_STATUS);
    }


}
