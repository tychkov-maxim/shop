package com.epam.tm.shop.action;


import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String,Action> matches;
//// FIXME: 07.01.2017 fix that to const min
    public ActionFactory() {
        matches = new HashMap<>();
        matches.put("login",new LoginAction());
        matches.put("logout",new LogoutAction());
        matches.put("register",new UserRegisterAction());
        matches.put("show",new ProductsByCategoryAction());
        matches.put("lang",new ChangeLanguageAction());
        matches.put("product",new ProductAction());
        matches.put("cart",new CartAction());
        matches.put("showRegister",new ShowPageAction("register"));
        matches.put("showLogin",new ShowPageAction("login"));
        matches.put("checkout",new CheckoutAction());
        matches.put("order",new OrderAction());
        matches.put("permission",new ShowPageAction("permission-error"));
        matches.put("orders",new GetOrdersAction());
        matches.put("profile",new ShowPageAction("profile"));
        matches.put("changeStatus",new ChangeOrderStatusAction());
        matches.put("user",new UserAction());
        matches.put("find",new ShowPageAction("find-user"));
        matches.put("addCategory",new AddCategoryAction());
        matches.put("addProducts",new ShowPageAction("add-products"));
    }

    public Action getAction(String actionName) throws ActionFactoryException {
            return matches.get(actionName);
    }
}
