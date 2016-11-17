package com.epam.tm.shop;

import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new MyThread();
        }
    }
}


class MyThread extends Thread{
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
