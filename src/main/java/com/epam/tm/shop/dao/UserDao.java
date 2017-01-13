package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {

    User findByLogin(String login) throws DaoException, DaoNoDataException;

    List<User> findAllUsersByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;
}
