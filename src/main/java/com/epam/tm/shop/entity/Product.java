package com.epam.tm.shop.entity;

import org.joda.money.Money;

public class Product extends BaseEntity{
    private String name;
    private String description;
    private Money price;
    private ProductCategory productCategory;
    private String imagePath;
    private int quantity;

    public Product() {
    }

    public Product(String name, String description, Money price, ProductCategory productCategory, String imagePath, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.imagePath = imagePath;
        this.quantity = quantity;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productCategory=" + productCategory +
                ", imagePath='" + imagePath + '\'' +
                ", quantity=" + quantity +
                "} " + super.toString();
    }
}
