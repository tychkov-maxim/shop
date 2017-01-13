package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Product;
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

public class ProductAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(ProductAction.class);
    private static final String FORM_NAME = "product";
    private static final String PRODUCT_ATTRIBUTE = "product";
    private static final String ID_PARAMETER = "id";
    private static final String PRODUCT_MESSAGE = "productMessage";
    private static final String NO_ONE_PRODUCT_WITH_ID_MESSAGE = "no.one.product.with.id";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to product action");
        ProductService productService = new ProductService();
        List<String> message = new ArrayList<>();
        int id;
        String idParam = req.getParameter(ID_PARAMETER);
        Validator validator = new OnlyNumberValidator();

        if (validator.isValid(idParam)) {
            id = Integer.parseInt(idParam);
            log.trace("id parameter is valid - {}", id);
            try {
                Product product = productService.getProductById(id);
                req.setAttribute(PRODUCT_ATTRIBUTE, product);
                log.trace("got product {}", product);
            } catch (ServiceNoDataException e) {
                message.add(NO_ONE_PRODUCT_WITH_ID_MESSAGE);
                req.setAttribute(PRODUCT_MESSAGE, message);
                log.trace("couldn't get product");
            } catch (ServiceException e) {
                throw new ActionException(e);
            }

        } else {
            message.add(NO_ONE_PRODUCT_WITH_ID_MESSAGE);
            req.setAttribute(PRODUCT_MESSAGE, message);
            log.trace("id parameter is not valid");
        }

        log.trace("product action was finished successfully");
        return FORM_NAME;
    }
}
