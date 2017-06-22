package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDao extends Dao<User> {

    User findByLogin(String login) throws DaoException, DaoNoDataException;

    List<User> findAllUsersByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;
}
