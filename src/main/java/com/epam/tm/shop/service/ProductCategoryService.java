package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.ProductCategory;

import java.util.List;

public class ProductCategoryService {

    public List<ProductCategory> getAllProductCategory() throws ServiceException {

        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductCategoryDao productCategoryDao = factory.getProductCategoryDao();
            return productCategoryDao.getAllProductCategory();
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        }

    }

    public ProductCategory saveProductCategory(ProductCategory productCategory) throws ServiceException, ServiceNonUniqueFieldException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductCategoryDao productCategoryDao = factory.getProductCategoryDao();
            return productCategoryDao.save(productCategory);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e) {
            throw new ServiceNonUniqueFieldException(e);
        }
    }
}
