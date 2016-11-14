package com.epam.tm.shop.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart extends BaseEntity{
    private List<Map<Product,Integer>> cart;
}
