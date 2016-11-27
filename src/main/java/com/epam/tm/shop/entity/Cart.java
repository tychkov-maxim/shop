package com.epam.tm.shop.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseEntity{
    private Map<Product,Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public boolean addProduct(Product product, Integer quantity) {
        if (!HaveEnoughProduct(product, quantity)) return false;
        cart.put(product,quantity);
        return true;
    }

    public void removeProduct(Product product){
        cart.remove(product);
    }

    public boolean changeQuantity(Product product, Integer quantity){

        if (!HaveEnoughProduct(product, quantity)) return false;

        if (cart.containsKey(product)) {
            cart.replace(product, quantity);
            return true;
        }else
            return false;
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    private boolean HaveEnoughProduct(Product product, Integer quantity){
        return product.getQuantity() >= quantity;
    }
}
