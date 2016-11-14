package com.epam.tm.shop.entity;

import org.joda.money.Money;

public class Product extends BaseEntity{
    private String name;
    private String description;
    private Money price;
    private Category category;

}
