package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.entity.Product;

import java.util.List;

public class ProductService {

    public Product getProductById(int id) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findById(id);
        } catch (DaoException  e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e){
            throw new ServiceNoDataException(e);
        }
    }

    public List<Product> getProductsByCategoryWithPagination(String category,int offset,int limit) throws ServiceException, ServiceNoDataException {

        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findProductsByCategoryWithPagination(category,offset,limit);
        } catch (DaoException  e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e){
            throw new ServiceNoDataException(e);
        }
    }

    public List<Product> getProductsWithPagination(int offset,int limit) throws ServiceException, ServiceNoDataException {

        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.findAllProductsWithPagination(offset,limit);
        } catch (DaoException  e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e){
            throw new ServiceNoDataException(e);
        }
    }

    public Product saveProduct(Product product) throws ServiceException, ServiceNonUniqueFieldException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            ProductDao productDao = factory.getProductDao();
            return productDao.save(product);
        } catch (DaoException  e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e){
            throw new ServiceNonUniqueFieldException(e);
        }
    }


}
