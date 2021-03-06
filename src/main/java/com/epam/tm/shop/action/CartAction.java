package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.exception.ServiceException;
import com.epam.tm.shop.exception.ServiceNoDataException;
import com.epam.tm.shop.service.ProductService;
import com.epam.tm.shop.validator.NotEmptyParameterValidator;
import com.epam.tm.shop.validator.OnlyNumberValidator;
import com.epam.tm.shop.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.tm.shop.util.ConstantHolder.ATTRIBUTE_SESSION_CART_NAME;

public class CartAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CartAction.class);

    private static final String ADD_PARAMETER = "add";
    private static final String DELETE_PARAMETER = "delete";
    private static final String CHANGE_QUANTITY_PARAMETER = "change";
    private static final String QUANTITY_PARAMETER = "quantity";
    private static final int DEFAULT_PRODUCT_QUANTITY = 1;
    private static final String REDIRECT_CHECKOUT = "redirect:/checkout.do";

    private Cart cart;
    private Validator onlyNumberValidator;
    private Validator notEmptyParameterValidator;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start to work with cart");

        HttpSession session;
        session = req.getSession(true);
        onlyNumberValidator = new OnlyNumberValidator();
        notEmptyParameterValidator = new NotEmptyParameterValidator();

        cart = (Cart) session.getAttribute(ATTRIBUTE_SESSION_CART_NAME);
        if (cart == null) {
            cart = new Cart();
            log.trace("cart was created");
        }

        String addParam = req.getParameter(ADD_PARAMETER);
        String delParam = req.getParameter(DELETE_PARAMETER);
        String changeParam = req.getParameter(CHANGE_QUANTITY_PARAMETER);

        if (notEmptyParameterValidator.isValid(addParam)) {
            addProduct(req);
        } else if (notEmptyParameterValidator.isValid(delParam)) {
            deleteProduct(req);
        } else if (notEmptyParameterValidator.isValid(changeParam)) {
            changeQuantity(req);
        }

        session.setAttribute(ATTRIBUTE_SESSION_CART_NAME, cart);
        log.trace("cart was saved in session");

        return REDIRECT_CHECKOUT;
    }

    private Product getProductById(int id) throws ActionException, ServiceNoDataException {
        ProductService productService = new ProductService();
        try {
            return productService.getProductById(id);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private void addProduct(HttpServletRequest req) throws ActionException {

        log.trace("start to add product to cart");
        String productIdParam = req.getParameter(ADD_PARAMETER);
        String quantityParam = req.getParameter(QUANTITY_PARAMETER);
        int quantity;

        if (onlyNumberValidator.isValid(productIdParam)) {
            try {
                Product product = getProductById(Integer.parseInt(productIdParam));

                if (notEmptyParameterValidator.isValid(quantityParam) && onlyNumberValidator.isValid(quantityParam)) {
                    quantity = Integer.parseInt(quantityParam);
                } else {
                    quantity = DEFAULT_PRODUCT_QUANTITY;
                }

                if (quantity > 0) {
                    cart.addProduct(product, quantity);
                    log.trace("product {} was added in the amount of {} pieces to cart successfully", product.getName(), quantity);
                }
            } catch (ServiceNoDataException e) {
                log.trace("It's impossible to add not found product to cart");
            }
        }
    }

    private void deleteProduct(HttpServletRequest req) throws ActionException {
        log.trace("start to delete product from cart");
        String productIdParam = req.getParameter(DELETE_PARAMETER);
        if (onlyNumberValidator.isValid(productIdParam)) {
            try {
                Product product = getProductById(Integer.parseInt(productIdParam));
                cart.removeProduct(product);
            } catch (ServiceNoDataException e) {
                log.trace("It's impossible to delete not found product from cart");
            }
        }
    }


    private void changeQuantity(HttpServletRequest req) throws ActionException {
        log.trace("start to change quantity of product from cart");
        String quantityParam = req.getParameter(QUANTITY_PARAMETER);
        int quantity;
        String productIdParam = req.getParameter(CHANGE_QUANTITY_PARAMETER);
        if (onlyNumberValidator.isValid(productIdParam)) {
            try {
                Product product = getProductById(Integer.parseInt(productIdParam));

                if (notEmptyParameterValidator.isValid(quantityParam) && onlyNumberValidator.isValid(quantityParam)) {
                    quantity = Integer.parseInt(quantityParam);
                } else {
                    quantity = DEFAULT_PRODUCT_QUANTITY;
                }

                cart.changeQuantity(product, quantity);
            } catch (ServiceNoDataException e) {
                log.trace("It's impossible to change quantity not found product from cart");
            }
        }
    }
}

