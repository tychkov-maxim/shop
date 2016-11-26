package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, role = ?, account = ?, account_unit = ?, address = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_QUERY_BY_LOGIN = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.login = ?";


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
            throw new JdbcException(e);
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
    protected User createEntityFromResultSet(ResultSet rs) throws JdbcException {
        try {
            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(new Role(rs.getString("roles.name"),rs.getInt("role")));
                user.setAccount(Money.of(CurrencyUnit.getInstance(rs.getString("account_unit")),rs.getBigDecimal("account")));
                user.setAddress(rs.getString("address"));
                return user;
            }else {
                throw new JdbcException("no one user was found");
            }
        } catch (SQLException e) {
            log.error("creating user entity from result set was failed");
            throw new JdbcException(e);
        }
    }

    private List<User> findByString(String key, String query) throws JdbcException {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,key);
            ResultSet rs = ps.executeQuery();
            while(!rs.isLast())
                users.add(createEntityFromResultSet(rs));
            ps.close();
        } catch (SQLException e) {
            throw new JdbcException(e);
        }

        return users;

    }

    @Override
    public User findByLogin(String login) throws JdbcException {
        List<User> users;

        try {
            users = findByString(login, SELECT_QUERY_BY_LOGIN);
        } catch (JdbcException e) {
            log.error("finding user by login = {} was failed",login);
            throw new JdbcException(e);
        }

        return users.get(0);
    }

    // FIXME: 22.11.2016
    public void setCon(Connection con){
        this.connection = con;
    }
}
