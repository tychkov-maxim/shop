package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.service.ProductCategoryService;
import com.epam.tm.shop.service.ProductService;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ProductsByCategoryAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(ProductsByCategoryAction.class);

    private static final String NO_ONE_PRODUCT_IN_CATEGORY_MESSAGE = "no.one.product.in.category";
    private static final String FORM_NAME = "show-products";
    private static final String PAGE_PAGINATION_PARAMETER = "page";
    private static final String CATEGORY_PARAMETER = "category";
    private static final String CATEGORIES_ATTRIBUTE = "categories";
    private static final String PRODUCT_MESSAGE_ATTRIBUTE = "productsMessage";
    private static final String PRODUCTS_ATTRIBUTE = "products";
    private static final String NEXT_PAGE_ATTRIBUTE = "nextPage";
    private static final String PREVIOUS_PAGE_ATTRIBUTE = "previousPage";
    private static final int DEFAULT_LIMIT_PAGINATION = 15;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {


        ProductCategoryService productCategoryService = new ProductCategoryService();
        ProductService productService = new ProductService();
        String pageParam;
        String categoryParam;
        List<Product> products;
        int pageNumber = 0;

        try {
            List<ProductCategory> allProductCategory = productCategoryService.getAllProductCategory();
            req.setAttribute(CATEGORIES_ATTRIBUTE,allProductCategory);
            log.trace("got {} categories",allProductCategory.size());



            pageParam  = req.getParameter(PAGE_PAGINATION_PARAMETER);
            if ((pageParam != null) && (!pageParam.equals(""))){
                pageNumber = Integer.parseInt(pageParam);
            }
            if (pageNumber < 0) pageNumber = 0;

            categoryParam = req.getParameter(CATEGORY_PARAMETER);
            if ((categoryParam != null) && (!categoryParam.equals(""))){
                products = productService.getProductsByCategoryWithPagination(categoryParam, pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION);
            } else {
                products = productService.getProductsWithPagination(pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION);
            }
            req.setAttribute(PRODUCTS_ATTRIBUTE,products);

            if (DEFAULT_LIMIT_PAGINATION == products.size()) {
                List<Product> productsWithPagination = productService.getProductsWithPagination(pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION + 1);
                if (productsWithPagination.size() > DEFAULT_LIMIT_PAGINATION) {
                    req.setAttribute(PREVIOUS_PAGE_ATTRIBUTE,++pageNumber);
                }
            }
            if (pageNumber > 0) {
                req.setAttribute(NEXT_PAGE_ATTRIBUTE,--pageNumber);
            }

        } catch (ServiceException e) {
            throw new ActionException(e);
        } catch (ServiceNoDataException e){
            List<String> message = new ArrayList<>();
            message.add(NO_ONE_PRODUCT_IN_CATEGORY_MESSAGE);
            req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE,message);
            log.debug("no one product{}",message);
        }

        return FORM_NAME;
    }
}
