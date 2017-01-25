package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.entity.ProductCategory;
import com.epam.tm.shop.service.*;
import com.epam.tm.shop.validator.FormValidator;
import com.epam.tm.shop.validator.FormValidatorFactory;
import com.epam.tm.shop.validator.ValidatorException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddProductAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddProductAction.class);
    private static final String FORM_NAME = "add-products";
    private static final String FORM_NAME_ADD_PRODUCT = "product";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String CATEGORY_PARAMETER = "category";
    private static final String PRICE_PARAMETER = "price";
    private static final String IMAGE_PARAMETER = "image";
    private static final String NAME_PREVIOUS_PARAMETER = "oldName";
    private static final String DESCRIPTION_PREVIOUS_PARAMETER = "oldDescription";
    private static final String CATEGORY_PREVIOUS_PARAMETER = "oldCategory";
    private static final String PRICE_PREVIOUS_PARAMETER = "oldPrice";
    private static final String ALL_CATEGORY_ATTRIBUTE = "categories";
    private static final String ADD_PRODUCT_ERROR_ATTRIBUTE = "productsErrors";
    private static final String ADD_PRODUCT_MESSAGE_ATTRIBUTE = "productsMessages";
    private static final String NO_ONE_PRODUCT_CATEGORY_ERROR_MESSAGE = "no.one.product.category";
    private static final String PRODUCT_EXIST_ERROR_MESSAGE = "product.exist";
    private static final String NOT_CORRECT_IMAGE_MESSAGE = "not.correct.image";
    private static final String NOT_CORRECT_IMAGE_ATTRIBUTE = "imageErrors";
    private static final String PRODUCT_ADDED_SUCCESSFULLY = "product.added";
    private static final int MAX_SIZE_IMAGE_MB = 5;
    private static final int ONE_MEGABYTE_BY_BYTES = 1024 * 1024;
    private static final int BUFFER_SIZE = 1024;
    private static final String CHECK_IMAGE_EXTENSION_REGEX = ".+[.]jpg$|.+[.]png$";
    private static final String DOT = ".";
    private static final String FOLDER_SEPARATOR = "/";
    private static final String IMAGE_FOLDER = "image";
    private static final int NO_MORE_DATA_IN_STREAM = -1;
    private static final int START_OFFSET_IN_THE_DATA = 0;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        List<String> errorMessages = new ArrayList<>();
        List<ProductCategory> allProductCategory;
        ProductCategory productCategory = null;
        log.trace("start to add product action");

        ProductCategoryService productCategoryService = new ProductCategoryService();
        try {
            log.trace("getting all categories");
            allProductCategory = productCategoryService.getAllProductCategory();
            log.trace("got category {}", allProductCategory.size());
            req.setAttribute(ALL_CATEGORY_ATTRIBUTE, allProductCategory);
        } catch (ServiceException e) {
            throw new ActionException(e);
        } catch (ServiceNoDataException e) {
            log.trace("no one category");
            errorMessages.add(NO_ONE_PRODUCT_CATEGORY_ERROR_MESSAGE);
            req.setAttribute(ADD_PRODUCT_ERROR_ATTRIBUTE, errorMessages);
            return FORM_NAME;
        }


        String nameParam = req.getParameter(NAME_PARAMETER);
        String descriptionParam = req.getParameter(DESCRIPTION_PARAMETER);
        String categoryParam = req.getParameter(CATEGORY_PARAMETER);
        String priceParam = req.getParameter(PRICE_PARAMETER);

        try {
            FormValidator categoryFormValidator = FormValidatorFactory.getFormValidatorByNameOfForm(FORM_NAME_ADD_PRODUCT);
            if (categoryFormValidator.validate(req)) {
                req.setAttribute(NAME_PREVIOUS_PARAMETER, nameParam);
                req.setAttribute(DESCRIPTION_PREVIOUS_PARAMETER, descriptionParam);
                req.setAttribute(CATEGORY_PREVIOUS_PARAMETER, categoryParam);
                req.setAttribute(PRICE_PREVIOUS_PARAMETER, priceParam);
                log.trace("product form's parameters are not valid");
                return FORM_NAME;
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        for (ProductCategory category : allProductCategory) {
            if (category.getName().equals(categoryParam)) {
                productCategory = category;
                break;
            }
        }

        if (productCategory == null) {
            log.trace("not right category");
            return FORM_NAME;
        }


        log.trace("start to receive file");
        try {
            Part filePart = req.getPart(IMAGE_PARAMETER);
            if ((filePart != null) && (filePart.getSize() < MAX_SIZE_IMAGE_MB * ONE_MEGABYTE_BY_BYTES)) {

                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                File file;
                String uniqueFileName;
                log.trace("fileName = {}, fileSize = {}", fileName, filePart.getSize());

                if (fileName.matches(CHECK_IMAGE_EXTENSION_REGEX)) {

                    String extension = fileName.substring(fileName.lastIndexOf(DOT));
                    uniqueFileName = getUniqueFileName(extension);
                    String realPath = req.getServletContext().getRealPath(FOLDER_SEPARATOR);
                    uniqueFileName = FOLDER_SEPARATOR + uniqueFileName;
                    file = new File(realPath + IMAGE_FOLDER + uniqueFileName);
                } else {
                    log.trace("file doesn't have jpg or png extension");
                    errorMessages.add(NOT_CORRECT_IMAGE_MESSAGE);
                    req.setAttribute(NOT_CORRECT_IMAGE_ATTRIBUTE, errorMessages);
                    return FORM_NAME;
                }

                log.trace("start to save new product");
                Product product = new Product(nameParam, descriptionParam, Money.of(CurrencyUnit.USD, Double.parseDouble(priceParam)), productCategory, uniqueFileName);
                ProductService productService = new ProductService();

                try {
                    Product savedProduct = productService.saveProduct(product);
                    log.trace("product {} was save successfully", savedProduct.getId());
                } catch (ServiceException e) {
                    throw new ActionException(e);
                } catch (ServiceNonUniqueFieldException e) {
                    log.trace("product {} already exist", product.getName());
                    errorMessages.add(PRODUCT_EXIST_ERROR_MESSAGE);
                    req.setAttribute(ADD_PRODUCT_ERROR_ATTRIBUTE, errorMessages);
                    return FORM_NAME;
                }

                try (InputStream fileContent = filePart.getInputStream();
                     FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    int read;
                    final byte[] bytes = new byte[BUFFER_SIZE];
                    while ((read = fileContent.read(bytes)) != NO_MORE_DATA_IN_STREAM) {
                        fileOutputStream.write(bytes, START_OFFSET_IN_THE_DATA, read);
                    }
                    log.trace("file was uploaded");
                }

                errorMessages.add(PRODUCT_ADDED_SUCCESSFULLY);
                req.setAttribute(ADD_PRODUCT_MESSAGE_ATTRIBUTE, errorMessages);
                return FORM_NAME;

            } else {
                log.trace("don't have multipart of form or file too size");
                errorMessages.add(NOT_CORRECT_IMAGE_MESSAGE);
                req.setAttribute(NOT_CORRECT_IMAGE_ATTRIBUTE, errorMessages);
                return FORM_NAME;
            }
        } catch (IllegalStateException | IOException | ServletException e) {
            log.trace("some exception", e);
            errorMessages.add(NOT_CORRECT_IMAGE_MESSAGE);
            req.setAttribute(NOT_CORRECT_IMAGE_ATTRIBUTE, errorMessages);
            return FORM_NAME;
        }

    }

    private String getUniqueFileName(String extension) {
        UUID id = UUID.randomUUID();
        return id.toString().replaceAll("-", "") + extension;
    }

}
