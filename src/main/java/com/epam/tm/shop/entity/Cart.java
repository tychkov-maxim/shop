package com.epam.tm.shop.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseEntity{
    private Map<Product,Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public boolean addProduct(Product product, Integer quantity){
        return cart.put(product, quantity) != null;
    }

    public boolean removeProduct(Product product){
        return cart.remove(product) != null;
    }

    public boolean changeQuantity(Product product, Integer quantity){
        return cart.replace(product, quantity) != null;
    }
}
