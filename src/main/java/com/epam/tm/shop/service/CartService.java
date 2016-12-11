package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
//// FIXME: 27.11.2016 strange service, FIX N+!
public class CartService {
    public static final Logger log = LoggerFactory.getLogger(CartService.class);
    private DaoFactory factory;

    public CartService() {
        factory = DaoFactory.createFactory();
    }

    public Cart getCartById(int id) throws ServiceException {
        CartDao cartDao = factory.getCartDao();
        Cart cart;
        try {
            cart = cartDao.findById(id);
        } catch (JdbcException e) {
            log.error("finding cart by id = {} was failed", id);
            throw new ServiceException(e);
        }

        ProductDao productDao = factory.getProductDao();
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
            try {
                productIntegerMap.put(productDao.findById(entry.getKey().getId()), entry.getValue());
            } catch (JdbcException e) {
                log.error("finding product by id = {} was failed", entry.getKey().getId());
                throw new ServiceException(e);
            }
        }
        cart.setCart(productIntegerMap);
        return cart;
    }
}
