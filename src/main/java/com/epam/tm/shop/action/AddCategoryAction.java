package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.service.ProductCategoryService;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNonUniqueFieldException;
import com.epam.tm.shop.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(AddCategoryAction.class);

    private static final String FORM_NAME = "add-products";
    private static final String FORM_NAME_ADD_CATEGORY = "category";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String NAME_PREVIOUS_PARAMETER = "oldName";
    private static final String DESCRIPTION_PREVIOUS_PARAMETER = "oldDescription";
    private static final String ADD_PRODUCT_CATEGORY_ERROR_ATTRIBUTE = "categoryErrors";
    private static final String PRODUCT_CATEGORY_EXIST_ERROR_MESSAGE = "product.category.exist";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
       log.trace("start to add product category action");
        String nameParam = req.getParameter(NAME_PARAMETER);
        String descriptionParam = req.getParameter(DESCRIPTION_PARAMETER);

        try {
            FormValidator categoryFormValidator = FormValidatorFactory.getFormValidatorByNameOfForm(FORM_NAME_ADD_CATEGORY);
            if (categoryFormValidator.validate(req)){
                req.setAttribute(NAME_PREVIOUS_PARAMETER,nameParam);
                req.setAttribute(DESCRIPTION_PREVIOUS_PARAMETER,descriptionParam);
                log.trace("product category form's parameters are not valid");
                return FORM_NAME;
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }


        ProductCategory productCategory = new ProductCategory(nameParam,descriptionParam);
        ProductCategoryService productCategoryService = new ProductCategoryService();
        try {
            ProductCategory savedProductCategory = productCategoryService.saveProductCategory(productCategory);
            log.trace("product category {} was added successfully",savedProductCategory.getName());
        } catch (ServiceException e) {
            throw new ActionException(e);
        } catch (ServiceNonUniqueFieldException e) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add(PRODUCT_CATEGORY_EXIST_ERROR_MESSAGE);
            req.setAttribute(ADD_PRODUCT_CATEGORY_ERROR_ATTRIBUTE,errorMessage);
            req.setAttribute(NAME_PREVIOUS_PARAMETER,nameParam);
            req.setAttribute(DESCRIPTION_PREVIOUS_PARAMETER,descriptionParam);
        }


        return FORM_NAME;
    }
}
