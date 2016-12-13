package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> getAllProductsByCartId(int cartId) throws JdbcException;
    List<Product> getProductsByCategory(String category,int offset,int limit) throws JdbcException;
}