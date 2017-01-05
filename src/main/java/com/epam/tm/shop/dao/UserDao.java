package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcNoDataException;
import com.epam.tm.shop.entity.User;

import java.sql.Connection;

public interface UserDao extends Dao<User>{

    User findByLogin(String login) throws DaoException, DaoNoDataException;
}
