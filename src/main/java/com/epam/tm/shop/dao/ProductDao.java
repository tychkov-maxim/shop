package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> findAllProductsByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;

    List<Product> findUserProductsByOrderStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException;

    List<Product> findAllProductsByCartId(int cartId) throws DaoException, DaoNoDataException;

    List<Product> findProductsByCategoryWithPagination(String category, int offset, int limit) throws DaoException, DaoNoDataException;

    List<Product> findAllProductsWithPagination(int offset, int limit) throws DaoException, DaoNoDataException;
}