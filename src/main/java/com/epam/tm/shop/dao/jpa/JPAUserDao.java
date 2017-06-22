package com.epam.tm.shop.dao.jpa;

import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.*;


import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JPAUserDao extends JPADao<User> implements UserDao {


    @Override
    public User findByLogin(String login) throws DaoException, DaoNoDataException {
        return null;
    }

    @Override
    public List<User> findAllUsersByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException {
        return null;
    }

    @Override
    public User findById(int id) throws DaoException, DaoNoDataException {

        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public void delete(User entity) throws DaoException {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(int id) throws DaoException {
        User user = new User();
        user.setId(id);
        entityManager.remove(user);
    }
}
