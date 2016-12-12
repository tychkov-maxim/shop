package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.OrderDao;
import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final String INSERT_QUERY = "INSERT INTO orders VALUES(DEFAULT,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE orders SET user_id = ?, time = ?, total = ?, total_price = ?, order_status = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM orders JOIN order_status ON orders.order_status = order_status.id WHERE orders.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM orders WHERE id = ?";


    public JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, Order entity) throws JdbcException {

        try {
            ps.setInt(1,entity.getUser().getId());
            ps.setTimestamp(2,new Timestamp(entity.getTime().getMillis()));
            ps.setBigDecimal(3,entity.getTotal().getAmount());
            ps.setString(4,entity.getTotal().getCurrencyUnit().toString());
            ps.setInt(5,entity.getStatus().getId());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(6,entity.getId());

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
    protected List<Order> createEntityFromResultSet(ResultSet rs) throws JdbcException {
        List<Order> orders = new ArrayList<>();
        try {
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setStatus(new OrderStatus(rs.getInt("order_status.id"),rs.getString("order_status.name")));
                order.setTime(new DateTime(rs.getTime("time")));
                order.setTotal(Money.of(CurrencyUnit.of(rs.getString("total_unit")), rs.getBigDecimal("total")));
                User user = new User();
                user.setId(rs.getInt("user_id"));
                order.setUser(user);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating order entity from result set was failed",e);
        }

        if (orders.size() == 0)
            throw new JdbcException("no one order was found");

        return orders;
    }

}
