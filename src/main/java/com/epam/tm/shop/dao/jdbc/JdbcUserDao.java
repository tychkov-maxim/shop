package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, role = ?, account = ?, account_unit = ?, address = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_QUERY_BY_LOGIN = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.login = ?";
    private static final String SELECT_QUERY_BY_ORDER_STATUS = "SELECT * FROM users JOIN roles ON users.role = roles.id JOIN orders on users.id = orders.user_id WHERE orders.order_status = ?";

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, User entity) throws JdbcException {

        try {
            ps.setString(1,entity.getLogin());
            ps.setString(2,entity.getPassword());
            ps.setString(3,entity.getFirstName());
            ps.setString(4,entity.getLastName());
            ps.setInt(5,entity.getRole().getId());
            ps.setBigDecimal(6,entity.getAccount().getAmount());
            ps.setString(7,entity.getAccount().getCurrencyUnit().toString());
            ps.setString(8,entity.getAddress());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(9,entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set user entity to ps was failed",e);
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
    protected List<User> createEntityFromResultSet(ResultSet rs) throws JdbcException, JdbcNoDataException {
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(new Role(rs.getString("roles.name"),rs.getInt("role")));
                user.setAccount(Money.of(CurrencyUnit.getInstance(rs.getString("account_unit")),rs.getBigDecimal("account")));
                user.setAddress(rs.getString("address"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating user entity from result set was failed",e);
        }

        if (users.size() == 0)
            throw new JdbcNoDataException("no one user was found");

        return users;
    }


    @Override
    public User findByLogin(String login) throws JdbcException, JdbcNoDataException {
        List<User> users;

        try {
            users = findByString(login, SELECT_QUERY_BY_LOGIN);
        } catch (JdbcException e) {
            throw new JdbcException(MessageFormat.format("finding user by login = {0} was failed",login),e);
        }

        return users.get(0);
    }

    @Override
    public List<User> findAllUsersByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return findAllById(orderStatus.getId(),SELECT_QUERY_BY_ORDER_STATUS);
    }


}
