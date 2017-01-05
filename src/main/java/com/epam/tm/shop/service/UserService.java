package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.DaoNoDataException;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.User;

public class UserService {

    public User getUserByLogin(String login) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findByLogin(login);
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        }
    }


    public User saveUser(User user) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
