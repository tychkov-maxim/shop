package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.*;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users  SET WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id = ?";

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    void setPsFields(PreparedStatement ps, User entity) throws JdbcException {

        try {
            ps.setString(1,entity.getLogin());
            ps.setString(2,entity.getPassword());
            ps.setString(3,entity.getFirstName());
            ps.setString(4,entity.getLastName());
            ps.setInt(5,entity.getRole().getId());
            ps.setBigDecimal(6,entity.getAccount().getAmount());
            ps.setString(7,entity.getAccount().getCurrencyUnit().toString());

        } catch (SQLException e) {
            throw new JdbcException(e);
        }

    }

    @Override
    String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    String getSelectQueryById() {
        return SELECT_QUERY;
    }

    @Override
    User createEntityFromResultSet(ResultSet rs) throws JdbcException {
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

                return user;
            }else {
                throw new JdbcException("no one user was found");
            }
        } catch (SQLException e) {
            log.error("creating user entity from result set was failed");
            throw new JdbcException(e);
        }
    }

    // FIXME: 22.11.2016
    public void setCon(Connection con){
        this.connection = con;
    }
}
