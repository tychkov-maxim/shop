package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.DaoFactoryException;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {
    public static final Logger log = LoggerFactory.getLogger(CartService.class);
    private DaoFactory factory;

    public CartService() throws ServiceException {
        try {
            factory = DaoFactory.createFactory();
        } catch (DaoFactoryException e) {
            throw new ServiceException(e);
        }
    }

    public Cart getCartById(int id) throws ServiceException {

        CartDao cartDao = factory.getCartDao();
        ProductDao productDao = factory.getProductDao();
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        List<Product> allProductsByCartId;
        Cart cart;

        try {
            cart = cartDao.findById(id);
            allProductsByCartId = productDao.getAllProductsByCartId(id);
        } catch (JdbcException e) {
            throw new ServiceException(e);
        }

        int i = 0;
        for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
                productIntegerMap.put(allProductsByCartId.get(i++),entry.getValue());
        }

        cart.setCart(productIntegerMap);
        return cart;
    }
}
