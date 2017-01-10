package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.entity.User;

public class UserService {


    public User getUserByLogin(String login) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e){
            throw new ServiceNoDataException(e);
        }

    }


    public User saveUser(User user) throws ServiceException, ServiceNonUniqueFieldException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e){
            throw new ServiceNonUniqueFieldException(e);
        }
    }

    public User getUserById(int id) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e){
            throw new ServiceNoDataException(e);
        }

    }



}
