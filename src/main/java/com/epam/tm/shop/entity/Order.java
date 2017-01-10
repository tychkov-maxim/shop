package com.epam.tm.shop.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Order extends BaseEntity {
    private Cart cart;
    private User user;
    private DateTime time;
    private Money total;
    private OrderStatus status;

    public Order() {
    }

    public Order(Cart cart, User user, DateTime time, Money total, OrderStatus status) {
        this.cart = cart;
        this.user = user;
        this.time = time;
        this.total = total;
        this.status = status;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "cart=" + cart +
                ", user=" + user +
                ", time=" + time +
                ", total=" + total +
                ", status=" + status +
                "} " + super.toString();
    }
}
