package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.Product;

import java.sql.Connection;

public interface ProductDao extends Dao<Product> {
    void setCon(Connection con);// FIXME: 22.11.2016
}