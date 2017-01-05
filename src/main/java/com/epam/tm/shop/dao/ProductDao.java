package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcNoDataException;
import com.epam.tm.shop.entity.Product;

import java.sql.Connection;
import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> findAllProductsByCartId(int cartId) throws DaoException, DaoNoDataException;
    List<Product> findProductsByCategoryWithPagination(String category, int offset, int limit) throws DaoException, DaoNoDataException;
    List<Product> findAllProductsWithPagination(int offset, int limit) throws DaoException, DaoNoDataException;
}