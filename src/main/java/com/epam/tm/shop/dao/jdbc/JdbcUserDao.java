package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?)";

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
        return null;
    }

    @Override
    String getSelectQueryById(int id) {
        return null;
    }

    @Override
    User createEntityFromResultSet(ResultSet rs) {
        return null;
    }

    // FIXME: 22.11.2016
    public void setCon(Connection con){
        this.connection = con;
    }
}
