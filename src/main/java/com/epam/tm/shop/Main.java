package com.epam.tm.shop;

import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.dao.jdbc.JdbcDao;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcUserDao;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws PoolException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();


        DaoFactory factory = DaoFactory.createFactory();
        UserDao userDao = factory.getUserDao();
        userDao.setCon(connection);

        User user = new User("login","pass","Max","fam", Role.getAdministratorRole(), Money.of(CurrencyUnit.USD, 6666666.66));


        try {
            System.out.println(userDao.save(user));

        } catch (JdbcException e) {
            //e.printStackTrace();
        }
    }


    class MyThread extends Thread {
        public MyThread() {

            start();
        }

        @Override
        public void run() {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                Connection connection = connectionPool.getConnection();
                System.out.println(connection);
                this.sleep(10000);
                connection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}