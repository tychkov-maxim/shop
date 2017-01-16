package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.util.ConstantHolder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, role = ?, account = ?, account_unit = ?, address = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_QUERY_BY_LOGIN = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.login = ?";
    private static final String SELECT_QUERY_BY_ORDER_STATUS = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id IN (SELECT orders.user_id FROM orders where order_status = ?)";

    private static final String ID_COLUMN_NAME = "id";
    private static final String LOGIN_COLUMN_NAME = "login";
    private static final String PASSWORD_COLUMN_NAME = "password";
    private static final String FIRST_NAME_COLUMN_NAME = "first_name";
    private static final String LAST_NAME_COLUMN_NAME = "last_name";
    private static final String ROLES_NAME_COLUMN_NAME = "roles.name";
    private static final String ROLE_COLUMN_NAME = "role";
    private static final String ACCOUNT_UNIT_COLUMN_NAME = "account_unit";
    private static final String ACCOUNT_COLUMN_NAME = "account";
    private static final String ADDRESS_COLUMN_NAME = "address";

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, User entity) throws JdbcException {

        try {
            ps.setString(ConstantHolder.FIRST_INDEX, entity.getLogin());
            ps.setString(ConstantHolder.SECOND_INDEX, entity.getPassword());
            ps.setString(ConstantHolder.THIRD_INDEX, entity.getFirstName());
            ps.setString(ConstantHolder.FOURTH_INDEX, entity.getLastName());
            ps.setInt(ConstantHolder.FIFTH_INDEX, entity.getRole().getId());
            ps.setBigDecimal(ConstantHolder.SIXTH_INDEX, entity.getAccount().getAmount());
            ps.setString(ConstantHolder.SEVENTH_INDEX, entity.getAccount().getCurrencyUnit().toString());
            ps.setString(ConstantHolder.EIGHTH_INDEX, entity.getAddress());

            Integer id = entity.getId();
            if (id != null)
                ps.setInt(ConstantHolder.NINTH_INDEX, entity.getId());

        } catch (SQLException e) {
            throw new JdbcException("set user entity to ps was failed", e);
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
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(ID_COLUMN_NAME));
                user.setLogin(rs.getString(LOGIN_COLUMN_NAME));
                user.setPassword(rs.getString(PASSWORD_COLUMN_NAME));
                user.setFirstName(rs.getString(FIRST_NAME_COLUMN_NAME));
                user.setLastName(rs.getString(LAST_NAME_COLUMN_NAME));
                user.setRole(new Role(rs.getString(ROLES_NAME_COLUMN_NAME), rs.getInt(ROLE_COLUMN_NAME)));
                user.setAccount(Money.of(CurrencyUnit.getInstance(rs.getString(ACCOUNT_UNIT_COLUMN_NAME)), rs.getBigDecimal(ACCOUNT_COLUMN_NAME)));
                user.setAddress(rs.getString(ADDRESS_COLUMN_NAME));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new JdbcException("creating user entity from result set was failed", e);
        }

        if (users.size() == ConstantHolder.EMPTY_LIST_SIZE)
            throw new JdbcNoDataException("no one user was found");

        return users;
    }


    @Override
    public User findByLogin(String login) throws JdbcException, JdbcNoDataException {
        List<User> users;
        try {
            users = findByString(login, SELECT_QUERY_BY_LOGIN);
        } catch (JdbcException e) {
            throw new JdbcException(MessageFormat.format("finding user by login = {0} was failed", login), e);
        }

        return users.get(0);
    }

    @Override
    public List<User> findAllUsersByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return findAllById(orderStatus.getId(), SELECT_QUERY_BY_ORDER_STATUS);
    }


}
