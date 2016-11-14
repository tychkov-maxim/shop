package com.epam.tm.shop.dao.jdbc;


import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    void setPsFields(PreparedStatement ps, User entity) {
    }

    @Override
    String getInsertQuery() {
        return null;
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
}
