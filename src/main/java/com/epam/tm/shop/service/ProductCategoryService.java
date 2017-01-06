package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.entity.ProductCategory;

import java.util.List;

public class ProductCategoryService {

    public List<ProductCategory> getAllProductCategory() throws ServiceException {

        try(DaoFactory factory = DaoFactory.createFactory()) {
            ProductCategoryDao productCategoryDao = factory.getProductCategoryDao();
            return productCategoryDao.getAllProductCategory();
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        }

    }
}
