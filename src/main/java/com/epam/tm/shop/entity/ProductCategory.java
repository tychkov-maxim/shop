package com.epam.tm.shop.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends BaseEntity{

    private String name;
    private String description;

    public ProductCategory() {
    }

    public ProductCategory(int id,String name, String description) {
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
}
