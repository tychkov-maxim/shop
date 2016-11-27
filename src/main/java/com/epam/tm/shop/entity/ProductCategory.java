package com.epam.tm.shop.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends BaseEntity{

    private List<String> categories;
    private int i;

    public ProductCategory() {
        categories = new ArrayList<>();
        i = 1;
    }

    public String getCategory(){
        return categories.get(0);
    }

    public String getSubCategory(){
        if (categories.size() >= i) i = 1;
        if (categories.size() == 1) i = 0;
        return categories.get(i++);
    }

    public int getAmountSubCategory(){
        return categories.size()-1;
    }
}
