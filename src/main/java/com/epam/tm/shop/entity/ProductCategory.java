package com.epam.tm.shop.entity;

public class ProductCategory extends BaseEntity {

    private String name;
    private String description;

    public ProductCategory() {
    }

    public ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProductCategory(int id, String name, String description) {
        this.setId(id);
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
