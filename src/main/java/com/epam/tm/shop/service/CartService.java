package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {
    public static final Logger log = LoggerFactory.getLogger(CartService.class);

    public Cart getCartById(int id) throws ServiceException {

        Map<Product, Integer> productIntegerMap = new HashMap<>();
        List<Product> allProductsByCartId;
        Cart cart;

            try(DaoFactory factory = DaoFactory.createFactory()) {
                CartDao cartDao = factory.getCartDao();
                ProductDao productDao = factory.getProductDao();
                cart = cartDao.findById(id);
                allProductsByCartId = productDao.findAllProductsByCartId(id);
            } catch (DaoException | DaoNoDataException e) {
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
