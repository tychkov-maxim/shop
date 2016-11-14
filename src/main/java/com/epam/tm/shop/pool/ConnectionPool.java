package com.epam.tm.shop.pool;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private List<Connection> freeConn;


    private ConnectionPool() {
        freeConn = new ArrayList<>();
    }

    public Connection getConnection(){

    }

    private static class InstanceHolder{
        public static final ConnectionPool instance = new ConnectionPool();
    }
}
