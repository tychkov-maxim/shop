package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.dao.OrderDao;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final String INSERT_QUERY = "INSERT INTO orders VALUES(DEFAULT,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE orders SET cart_id = ?, user_id = ?, time = ?, total = ?, total_unit = ?, order_status = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM orders JOIN order_status ON orders.order_status = order_status.id WHERE orders.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM orders WHERE id = ?";
    private static final String SELECT_QUERY_BY_STATUS = "SELECT * FROM orders JOIN order_status ON orders.order_status = order_status.id WHERE order_status.id = ?";
    private static final String SELECT_QUERY_USER_BY_STATUS = "SELECT * FROM orders JOIN order_status ON orders.order_status = order_status.id WHERE order_status.id = ? and user_id = ?";


    private static final int EMPTY_LIST_SIZE = 0;
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int FOURTH_INDEX = 4;
    private static final int FIFTH_INDEX = 5;
    private static final int SIXTH_INDEX = 6;
    private static final int SEVENTH_INDEX = 7;


    private static final String ID_COLUMN_NAME = "id";
    private static final String ORDER_STATUS_ID_COLUMN_NAME = "order_status.id";
    private static final String ORDER_STATUS_NAME_COLUMN_NAME = "order_status.name";
    private static final String TIME_COLUMN_NAME = "time";
    private static final String TOTAL_UNIT_COLUMN_NAME = "total_unit";
    private static final String TOTAL_COLUMN_NAME = "total";
    private static final String USER_ID_COLUMN_NAME = "user_id";
    private static final String CART_ID_COLUMN_NAME = "cart_id";


    public JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, Order entity) throws JdbcException {

        try {
            ps.setInt(FIRST_INDEX, entity.getCart().getId());
            ps.setInt(SECOND_INDEX, entity.getUser().getId());
            ps.setTimestamp(THIRD_INDEX, new Timestamp(entity.getTime().getMillis()));
            ps.setBigDecimal(FOURTH_INDEX, entity.getTotal().getAmount());
            ps.setString(FIFTH_INDEX, entity.getTotal().getCurrencyUnit().toString());
            ps.setInt(SIXTH_INDEX, entity.getStatus().getId());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(SEVENTH_INDEX, entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set order entity to ps was failed", e);
        }

    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
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
    protected List<Order> createEntityFromResultSet(ResultSet rs) throws JdbcException, JdbcNoDataException {
        List<Order> orders = new ArrayList<>();
        try {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(ID_COLUMN_NAME));
                order.setStatus(new OrderStatus(rs.getInt(ORDER_STATUS_ID_COLUMN_NAME), rs.getString(ORDER_STATUS_NAME_COLUMN_NAME)));
                order.setTime(new DateTime(rs.getTimestamp(TIME_COLUMN_NAME)));
                order.setTotal(Money.of(CurrencyUnit.of(rs.getString(TOTAL_UNIT_COLUMN_NAME)), rs.getBigDecimal(TOTAL_COLUMN_NAME)));
                User user = new User();
                user.setId(rs.getInt(USER_ID_COLUMN_NAME));
                order.setUser(user);
                Cart cart = new Cart();
                cart.setId(rs.getInt(CART_ID_COLUMN_NAME));
                order.setCart(cart);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating order entity from result set was failed", e);
        }

        if (orders.size() == EMPTY_LIST_SIZE)
            throw new JdbcNoDataException("no one order was found");

        return orders;
    }

    @Override
    public List<Order> findAllOrdersByStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return findAllById(orderStatus.getId(), SELECT_QUERY_BY_STATUS);
    }

    @Override
    public List<Order> findUserOrdersByStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        log.trace("start to find user {} orders by status {}", userId, orderStatus.getName());
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_QUERY_USER_BY_STATUS);
            ps.setInt(FIRST_INDEX, orderStatus.getId());
            ps.setInt(SECOND_INDEX, userId);
            ResultSet rs = ps.executeQuery();
            List<Order> orders = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding user {} orders by status {} was finished successfully", userId, orderStatus.getName());
            return orders;
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("finding user {0} orders by status {1} was failed", userId, orderStatus.getName()), e);
        }
    }
}
