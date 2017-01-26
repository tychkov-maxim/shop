package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;

import java.util.List;

public interface ProductCategoryDao extends Dao<ProductCategory> {
    ProductCategory findProductCategoryByName(String name) throws DaoException, DaoNoDataException;

    List<ProductCategory> getAllProductCategory() throws DaoException, DaoNoDataException;
}
