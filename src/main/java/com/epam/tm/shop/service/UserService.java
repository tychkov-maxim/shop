package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.*;
import com.epam.tm.shop.util.HashGenerator;
import com.epam.tm.shop.util.MD5Generator;

public class UserService {
    private static final String USER_EXIST_MESSAGE = "user.exist";


    public User getUserByLogin(String login) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }

    }

    public User register(User user) throws ServiceExceptionError, ServiceException {
        try {
            String password = user.getPassword();
            HashGenerator hashGenerator = new MD5Generator();
            String hashedPassword = hashGenerator.generateHashByString(password);
            user.setPassword(hashedPassword);
            return saveUser(user);
        } catch (ServiceNonUniqueFieldException e) {
            throw new ServiceExceptionError(USER_EXIST_MESSAGE);
        } catch (HashGeneratorException e) {
            throw new ServiceException(e);
        }
    }

    public User saveUser(User user) throws ServiceException, ServiceNonUniqueFieldException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e) {
            throw new ServiceNonUniqueFieldException(e);
        }
    }

    public User getUserById(int id) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }

    }


}
