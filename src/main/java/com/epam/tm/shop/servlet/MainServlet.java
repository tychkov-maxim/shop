package com.epam.tm.shop.servlet;

import com.epam.tm.shop.action.Action;
import com.epam.tm.shop.action.ActionFactory;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.exception.ActionFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.tm.shop.util.ConstantHolder.END_URL;
import static com.epam.tm.shop.util.ConstantHolder.START_URL;

@WebServlet(value = "*.do")
@MultipartConfig
public class MainServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MainServlet.class);
    private static final String VIEW_PATH = "/WEB-INF/jsp/";
    private static final String VIEW_EXTENSION = ".jsp";
    private static final String REDIRECT_PREFIX = "redirect:";

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        try {
            actionFactory = new ActionFactory();
        } catch (ActionFactoryException e) {
            log.error("Action factory error", e);
            throw new ServletException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        int startIndex = requestURI.lastIndexOf(START_URL);
        int endIndex = requestURI.lastIndexOf(END_URL);
        String actionName = requestURI.substring(startIndex + 1, endIndex);

        try {
            Action action = actionFactory.getAction(actionName);
            String result = action.execute(req, resp);

            if (result.startsWith(REDIRECT_PREFIX)) {
                resp.sendRedirect(req.getContextPath() + result.substring(REDIRECT_PREFIX.length()));
            } else {
                req.getRequestDispatcher(VIEW_PATH + result + VIEW_EXTENSION).forward(req, resp);
            }

        } catch (ActionFactoryException | ActionException e) {
            log.error("Error was occurred", e);
            throw new ServletException(e);
        } catch (Throwable e){
            log.error("Throwable exception!!!",e);
            throw new ServletException(e);
        }


    }

}
