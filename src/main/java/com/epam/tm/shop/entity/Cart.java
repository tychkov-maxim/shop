package com.epam.tm.shop.entity;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseEntity {
    private Map<Product, Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public int getSize() {
        return cart.size();
    }

    //let it be here than in CartSerivce
    public Money getAllCost() {
        Money sum = Money.zero(CurrencyUnit.USD);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            sum = sum.plus(entry.getKey().getPrice().multipliedBy(entry.getValue()));
        }
        return sum;
    }

    public void addProduct(Product product, Integer quantity) {
        cart.put(product, quantity);
    }

    public void removeProduct(Product product) {
        cart.remove(product);
    }

    public void changeQuantity(Product product, Integer quantity) {
        cart.replace(product, quantity);
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }


}
