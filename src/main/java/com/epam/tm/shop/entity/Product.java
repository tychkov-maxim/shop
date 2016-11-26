package com.epam.tm.shop.entity;

import org.joda.money.Money;

public class Product extends BaseEntity{
    private String name;
    private String description;
    private Money price;
    private Category category;
    private String imagePath;

    public Product() {
    }

    public Product(String name, String description, Money price, Category category, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
