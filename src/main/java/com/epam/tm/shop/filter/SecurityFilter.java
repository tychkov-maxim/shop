package com.epam.tm.shop.filter;

import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.PropertyManagerException;
import com.epam.tm.shop.util.PropertyManager;
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

import static com.epam.tm.shop.util.ConstantHolder.*;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "*.do")
public class SecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String ACTION_SECURITY_PROPERTIES_FILE_NAME = "security.properties";
    private static final String ADMIN_PREFIX = "admin";
    private static final String USER_PREFIX = "user";
    private static final String ANONYMOUS_PREFIX = "anon";


    private List<String> userActions;
    private List<String> anonActions;
    private List<String> adminActions;

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

        try {
            PropertyManager propertyManager = new PropertyManager(ACTION_SECURITY_PROPERTIES_FILE_NAME);
            anonActions = propertyManager.getListWithPrefix(ANONYMOUS_PREFIX);
            userActions = propertyManager.getListWithPrefix(USER_PREFIX);
            adminActions = propertyManager.getListWithPrefix(ADMIN_PREFIX);
        } catch (PropertyManagerException e) {
            log.error("reading file with security actions was failed",e);
            throw new ServletException(e);
        }

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
        String actionName = requestURI.substring(startIndex + 1, endIndex);

        Role role;
        if (user == null) {
            role = Role.getAnonymousRole();
        } else
            role = user.getRole();


        if (!hasRights(role, actionName)) {
            log.trace("user permission error, role = {} want to get {}", role, actionName);
            response.sendRedirect(request.getContextPath() + "/permission.do");
            return;
        }

        chain.doFilter(req, resp);
    }


    private boolean hasRights(Role role, String actionName) {
        List<String> availableActions = new ArrayList<>();

        if (role.equals(Role.getAdministratorRole())) {
            availableActions = adminActions;
        } else if (role.equals(Role.getUserRole())) {
            availableActions = userActions;
        } else if (role.equals(Role.getAnonymousRole())) {
            availableActions = anonActions;
        }

        return availableActions.contains(actionName);
    }

}
