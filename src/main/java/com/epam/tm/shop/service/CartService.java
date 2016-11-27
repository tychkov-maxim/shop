package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.pool.ConnectionPool;

import java.sql.Connection;

public class CartService {

    public Cart getCart(int id){
        return new Cart();
    }
}
