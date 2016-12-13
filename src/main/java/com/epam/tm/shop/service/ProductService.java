package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.DaoException;
import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.entity.Product;

import java.util.List;

public class ProductService {

    public Product getProductById(int id) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Product> getProductsByCategoryWithPagination(String category,int offset,int limit) throws ServiceException {

        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findProductsByCategoryWithPagination(category,offset,limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Product> getProductsWithPagination(int offset,int limit) throws ServiceException {

        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findAllProductsWithPagination(offset,limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


}
