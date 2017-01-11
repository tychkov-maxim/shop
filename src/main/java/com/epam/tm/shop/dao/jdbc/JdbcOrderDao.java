package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.dao.OrderDao;
import com.epam.tm.shop.entity.*;
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


    public JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, Order entity) throws JdbcException {

        try {
            ps.setInt(1,entity.getCart().getId());
            ps.setInt(2,entity.getUser().getId());
            ps.setTimestamp(3,new Timestamp(entity.getTime().getMillis()));
            ps.setBigDecimal(4,entity.getTotal().getAmount());
            ps.setString(5,entity.getTotal().getCurrencyUnit().toString());
            ps.setInt(6,entity.getStatus().getId());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(7,entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set order entity to ps was failed",e);
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
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setStatus(new OrderStatus(rs.getInt("order_status.id"),rs.getString("order_status.name")));
                order.setTime(new DateTime(rs.getTimestamp("time")));
                order.setTotal(Money.of(CurrencyUnit.of(rs.getString("total_unit")), rs.getBigDecimal("total")));
                User user = new User();
                user.setId(rs.getInt("user_id"));
                order.setUser(user);
                Cart cart = new Cart();
                cart.setId(rs.getInt("cart_id"));
                order.setCart(cart);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating order entity from result set was failed",e);
        }

        if (orders.size() == 0)
            throw new JdbcNoDataException("no one order was found");

        return orders;
    }

    @Override
    public List<Order> findAllOrdersByStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return findAllById(orderStatus.getId(), SELECT_QUERY_BY_STATUS);
    }

    @Override
    public List<Order> findUserOrdersByStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        log.trace("start to find user {} orders by status {}",userId, orderStatus.getName());
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_QUERY_USER_BY_STATUS);
            ps.setInt(1,orderStatus.getId());
            ps.setInt(2,userId);
            ResultSet rs = ps.executeQuery();
            List<Order> orders = createEntityFromResultSet(rs);
            ps.close();
            log.trace("finding user {} orders by status {} was finished successfully",userId, orderStatus.getName());
            return orders;
        } catch (SQLException e) {
            throw new JdbcException(MessageFormat.format("finding user {0} orders by status {1} was failed", userId, orderStatus.getName()),e);
        }
    }
}
