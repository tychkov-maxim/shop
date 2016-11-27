package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.ProductCategory;

import java.sql.Connection;

public interface ProductCategoryDao extends Dao<ProductCategory>{
    void setCon(Connection con);// FIXME: 22.11.2016
}
