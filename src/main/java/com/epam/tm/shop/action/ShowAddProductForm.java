package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.exception.ServiceException;
import com.epam.tm.shop.exception.ServiceNoDataException;
import com.epam.tm.shop.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tm.shop.util.ConstantHolder.CATEGORIES_ATTRIBUTE;

public class ShowAddProductForm implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowAddProductForm.class);

    private static final String FORM_NAME = "add-products";
    private static final String NO_ONE_CATEGORY_MESSAGE = "no.one.category";
    private static final String ADD_PRODUCT_MESSAGE_ATTRIBUTE = "addProductsMessage";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to show add product form");
        ProductCategoryService productCategoryService = new ProductCategoryService();

        try {
            List<ProductCategory> allProductCategory = productCategoryService.getAllProductCategory();
            log.trace("got {} categories", allProductCategory.size());
            req.setAttribute(CATEGORIES_ATTRIBUTE, allProductCategory);
        } catch (ServiceException e) {
            throw new ActionException(e);
        } catch (ServiceNoDataException e) {
            log.trace("no one category was found");
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add(NO_ONE_CATEGORY_MESSAGE);
            req.setAttribute(ADD_PRODUCT_MESSAGE_ATTRIBUTE, errorMessage);
        }


        return FORM_NAME;
    }
}
