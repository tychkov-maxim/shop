package com.epam.tm.shop.filter;

import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "*.do")
public class SecurityFilter implements Filter {

    public static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";
    private static final String END_URL = ".do";
    private static final String START_URL = "/";

    private List<String> userActions;
    private List<String> anonActions;
    private List<String> adminActions;

    public void destroy() {
    }
//// FIXME: 11.01.2017 move it to property
    public void init(FilterConfig config) throws ServletException {

        userActions = new ArrayList<>();
        anonActions = new ArrayList<>();
        adminActions = new ArrayList<>();

        anonActions.add("login");
        anonActions.add("register");
        anonActions.add("show");
        anonActions.add("lang");
        anonActions.add("product");
        anonActions.add("cart");
        anonActions.add("showRegister");
        anonActions.add("showLogin");
        anonActions.add("permission");

        userActions.add("logout");
        userActions.add("show");
        userActions.add("lang");
        userActions.add("product");
        userActions.add("cart");
        userActions.add("permission");
        userActions.add("checkout");
        userActions.add("order");
        userActions.add("orders");
        userActions.add("profile");
        userActions.add("changeStatus");

        adminActions.add("logout");
        adminActions.add("show");
        adminActions.add("lang");
        adminActions.add("product");
        adminActions.add("cart");
        adminActions.add("permission");
        adminActions.add("checkout");
        adminActions.add("order");
        adminActions.add("orders");
        adminActions.add("profile");
        adminActions.add("changeStatus");
        adminActions.add("user");
        adminActions.add("find");


        log.debug("Security filter was initialized successfully");
        log.debug("Anonymous has {} actions", anonActions.size());
        log.debug("User has {} actions", userActions.size());
        log.debug("Administrator has {} actions", adminActions.size());
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(ATTRIBUTE_SESSION_USER_NAME);

        String requestURI = request.getRequestURI();
        int startIndex = requestURI.lastIndexOf(START_URL);
        int endIndex = requestURI.lastIndexOf(END_URL);
        String actionName = requestURI.substring(startIndex+1,endIndex);

        Role role;
        if (user == null) {
            role = Role.getAnonymousRole();
        } else
            role = user.getRole();


        if (!hasRights(role, actionName)) {
            log.trace("user permission error, role = {} want to get {}",role,actionName);
            response.sendRedirect(request.getContextPath() + "/permission.do");
            return;
        }

        chain.doFilter(req, resp);
    }



    private boolean hasRights(Role role, String actionName) {
        List<String> availableActions = new ArrayList<>();

       if (role.equals(Role.getAdministratorRole())) {
           availableActions = adminActions;
       } else if (role.equals(Role.getUserRole())){
           availableActions = userActions;
       } else if (role.equals(Role.getAnonymousRole())){
           availableActions = anonActions;
       }

        return availableActions.contains(actionName);
    }

}
