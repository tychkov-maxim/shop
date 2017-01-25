package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.service.ProductCategoryService;
import com.epam.tm.shop.service.ProductService;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.validator.OnlyNumberValidator;
import com.epam.tm.shop.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ProductsByCategoryAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ProductsByCategoryAction.class);

    private static final String NO_ONE_PRODUCT_IN_CATEGORY_MESSAGE = "no.one.product.in.category";
    private static final String NO_ONE_CATEGORY_MESSAGE = "no.one.category";
    private static final String CHOSEN_CATEGORY_PARAMETER = "chosenCategory";
    private static final String FORM_NAME = "show-products";
    private static final String PAGE_PAGINATION_PARAMETER = "page";
    private static final String CATEGORY_PARAMETER = "category";
    private static final String CATEGORIES_ATTRIBUTE = "categories";
    private static final String PRODUCT_MESSAGE_ATTRIBUTE = "productsMessage";
    private static final String PRODUCTS_ATTRIBUTE = "products";
    private static final String NEXT_PAGE_ATTRIBUTE = "nextPage";
    private static final String PREVIOUS_PAGE_ATTRIBUTE = "previousPage";
    private static final int DEFAULT_LIMIT_PAGINATION = 15;
    private static final int THE_FIRST_PAGE_NUMBER = 0;
    private static final int STEP_OF_MOVING_PAGES = 1;
    private static final int THE_FIRST_ELEMENT_IN_LIST = 0;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start to execute products by category action");

        ProductCategoryService productCategoryService = new ProductCategoryService();
        ProductService productService = new ProductService();
        String pageParam;
        String categoryParam;
        List<Product> products;
        int pageNumber = THE_FIRST_PAGE_NUMBER;
        Validator validator = new OnlyNumberValidator();
        try {
            List<ProductCategory> allProductCategory = productCategoryService.getAllProductCategory();
            req.setAttribute(CATEGORIES_ATTRIBUTE, allProductCategory);
            log.trace("got {} categories", allProductCategory.size());


            if (allProductCategory.size() == 0) {
                List<String> message = new ArrayList<>();
                message.add(NO_ONE_CATEGORY_MESSAGE);
                req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                log.trace("no one category was found");
                return FORM_NAME;
            }


            pageParam = req.getParameter(PAGE_PAGINATION_PARAMETER);
            if (validator.isValid(pageParam)) {
                pageNumber = Integer.parseInt(pageParam);
                log.trace("pageParam is valid - {}", pageNumber);
            }
            if (pageNumber < THE_FIRST_PAGE_NUMBER) pageNumber = THE_FIRST_PAGE_NUMBER;

            categoryParam = req.getParameter(CATEGORY_PARAMETER);
            if ((categoryParam != null) && (!categoryParam.equals(""))) {
                products = productService.getProductsByCategoryWithPagination(categoryParam, pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION);
                log.trace("category parameter is valid - {}, got {} products", categoryParam, products.size());
            } else {
                categoryParam = allProductCategory.get(THE_FIRST_ELEMENT_IN_LIST).getName();
                products = productService.getProductsByCategoryWithPagination(categoryParam, pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION);
                log.trace("category parameter is not valid, chose the first category {}, got {} products", categoryParam, products.size());
            }
            req.setAttribute(CHOSEN_CATEGORY_PARAMETER, categoryParam);
            req.setAttribute(PRODUCTS_ATTRIBUTE, products);

            if (DEFAULT_LIMIT_PAGINATION == products.size()) {
                List<Product> productsWithPagination = productService.getProductsWithPagination(pageNumber * DEFAULT_LIMIT_PAGINATION, DEFAULT_LIMIT_PAGINATION + 1);
                if (productsWithPagination.size() > DEFAULT_LIMIT_PAGINATION) {
                    req.setAttribute(NEXT_PAGE_ATTRIBUTE, pageNumber + STEP_OF_MOVING_PAGES);
                    log.trace("next page:{} exists", pageNumber + STEP_OF_MOVING_PAGES);
                }
            }
            if (pageNumber > 0) {
                req.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, pageNumber - STEP_OF_MOVING_PAGES);
                log.trace("previous page:{} exists", pageNumber - STEP_OF_MOVING_PAGES);
            }

        } catch (ServiceException e) {
            throw new ActionException(e);
        } catch (ServiceNoDataException e) {
            List<String> message = new ArrayList<>();
            message.add(NO_ONE_PRODUCT_IN_CATEGORY_MESSAGE);
            req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
            log.trace("no one product was found{}", message);
        }

        log.trace("products by category action was finished successfully");

        return FORM_NAME;
    }
}
