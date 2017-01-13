package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao extends Dao<ProductCategory> {
    ProductCategory findProductCategoryByName(String name) throws DaoException, DaoNoDataException;

    List<ProductCategory> getAllProductCategory() throws DaoException, DaoNoDataException;
}
