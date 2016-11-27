package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.ProductCategory;

import java.sql.Connection;
import java.util.List;

public interface ProductCategoryDao extends Dao<ProductCategory>{
    ProductCategory findProductCategoryByName(String name) throws JdbcException;
}
