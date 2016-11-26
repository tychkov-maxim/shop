package com.epam.tm.shop.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Order extends BaseEntity {
    private Cart cart;
    private User user;
    private DateTime time;
    private Money total;

}
