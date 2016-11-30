package com.epam.tm.shop.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseEntity{
    private Map<Product,Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public void addProduct(Product product, Integer quantity) {
        cart.put(product,quantity);
    }

    public void removeProduct(Product product){
        cart.remove(product);
    }

    public void changeQuantity(Product product, Integer quantity){
            cart.replace(product, quantity);
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

}
