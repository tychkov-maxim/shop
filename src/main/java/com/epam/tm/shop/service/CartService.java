package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;
import com.epam.tm.shop.exception.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {

    public Cart getCartById(int id) throws ServiceException {

        Map<Product, Integer> newCartMap = new HashMap<>();
        List<Product> allProductsByCartId;
        Cart cart;

        try (DaoFactory factory = DaoFactory.createFactory()) {
            CartDao cartDao = factory.getCartDao();
            ProductDao productDao = factory.getProductDao();
            cart = cartDao.findById(id);
            allProductsByCartId = productDao.findAllProductsByCartId(id);
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        }

        for (Map.Entry<Product, Integer> cartMap : cart.getCart().entrySet()) {
            for (Product product : allProductsByCartId) {
                if (product.equals(cartMap.getKey())) {
                    newCartMap.put(product, cartMap.getValue());
                    break;
                }
            }
        }

        cart.setCart(newCartMap);
        return cart;
    }


}
