package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.User;

import java.sql.Connection;

public interface UserDao extends Dao<User>{

    void setCon(Connection con);// FIXME: 22.11.2016
    User findByLogin(String login) throws JdbcException;
}
