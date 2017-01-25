package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.util.ConstantHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    private static final Logger log = LoggerFactory.getLogger(JdbcCartDao.class);
    private static final String INSERT_CART_QUERY = "INSERT INTO carts VALUES(DEFAULT)";
    private static final String INSERT_QUERY = "INSERT INTO cart_to_products VALUES(?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM cart_to_products WHERE cart_id = ?";
    private static final String SELECT_QUERY_BY_ORDER_STATUS = "SELECT * FROM cart_to_products WHERE cart_to_products.cart_id IN (SELECT cart_id FROM orders WHERE orders.order_status = ?)";
    private static final String SELECT_USER_CART_BY_ORDER_STATUS = "SELECT * FROM cart_to_products WHERE cart_to_products.cart_id IN (SELECT cart_id FROM orders WHERE orders.order_status = ? AND orders.user_id = ?)";
    private static final String DELETE_QUERY = "DELETE FROM cart_to_products WHERE cart_id = ?";


    private static final String PRODUCT_ID_COLUMN_NAME = "product_id";
    private static final String QUANTITY_COLUMN_NAME = "quantity";
    private static final String CART_ID_COLUMN_NAME = "cart_id";

    public JdbcCartDao(Connection connection) {
        super(connection);
    }

    @Override
    public Cart save(Cart entity) throws JdbcException {
        if (entity.getId() == null) {
            PreparedStatement ps;
            AtomicInteger id = new AtomicInteger();
            try {
                ps = connection.prepareStatement(INSERT_CART_QUERY);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id.set(generatedKeys.getInt(ConstantHolder.FIRST_INDEX));
                }
                ps.close();
                return insert(entity, id.get());
            } catch (SQLException e) {
                throw new JdbcException(e);
            }
        } else
            return update(entity);
    }


    private Cart insert(Cart cart, int id) throws JdbcException {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(INSERT_QUERY);

            for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
                ps.setInt(ConstantHolder.FIRST_INDEX, id);
                ps.setInt(ConstantHolder.SECOND_INDEX, entry.getKey().getId());
                ps.setInt(ConstantHolder.THIRD_INDEX, entry.getValue());
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            throw new JdbcException("inserting cart was failed", e);
        }

        cart.setId(id);
        return cart;
    }

    private Cart update(Cart cart) throws JdbcException {
        deleteById(cart.getId());
        return insert(cart, cart.getId());
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
        Map<Product, Integer> map = new HashMap<>();
        Integer id = null;
        Integer oldId;
        try {
            while (rs.next()) {

                oldId = id;

                Product product = new Product();
                product.setId(rs.getInt(PRODUCT_ID_COLUMN_NAME));
                id = rs.getInt(CART_ID_COLUMN_NAME);

                if (oldId != null) {
                    if (!oldId.equals(id)) {
                        cart.setId(oldId);
                        cart.setCart(map);
                        carts.add(cart);
                        map = new HashMap<>();
                        cart = new Cart();
                    }
                }


                map.put(product, rs.getInt(QUANTITY_COLUMN_NAME));



            }
        } catch (SQLException e) {
            throw new JdbcException("creating cart entity from result set was failed", e);
        }

        if (map.size() == ConstantHolder.EMPTY_LIST_SIZE)
            throw new JdbcNoDataException("no one cart was found");

        cart.setId(id);
        cart.setCart(map);
        carts.add(cart);



        return carts;
    }

    @Override
    public List<Cart> findAllCartsByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return findAllById(orderStatus.getId(), SELECT_QUERY_BY_ORDER_STATUS);
    }

    @Override
    public List<Cart> findUserCartsByOrderStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        log.trace("start to find user {} carts by order status {}", userId, orderStatus.getName());
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_CART_BY_ORDER_STATUS);
            ps.setInt(ConstantHolder.FIRST_INDEX, orderStatus.getId());
            ps.setInt(ConstantHolder.SECOND_INDEX, userId);
            ResultSet rs = ps.executeQuery();
            List<Cart> carts = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding user {} carts by order status {} was finished successfully, find {} carts", userId, orderStatus.getName(),carts.size());
            return carts;
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("finding user {0} carts by order status {1} was failed", userId, orderStatus.getName()), e);
        }
    }


}
