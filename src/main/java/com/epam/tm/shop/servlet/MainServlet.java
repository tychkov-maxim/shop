package com.epam.tm.shop.servlet;

import com.epam.tm.shop.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "*.do")
public class MainServlet extends HttpServlet {

    public static final Logger log = LoggerFactory.getLogger(MainServlet.class);
    private static final String VIEW_PATH = "/WEB-INF/jsp/";
    private static final String VIEW_EXTENSION = ".jsp";
    private static final String REDIRECT = "redirect:";
    private static final String SERVLET_PATH_PATTERN = "/*.do";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        String requestURI = req.getRequestURI();
        String actionName = requestURI.replaceFirst(SERVLET_PATH_PATTERN, "").substring(1);

        ActionFactory actionFactory = new ActionFactory();
        try {
            Action action = actionFactory.getAction(actionName);
            String result = action.execute(req, resp);

            if (result.startsWith(REDIRECT)){
                resp.sendRedirect(result.substring(REDIRECT.length()));
            }else{
                req.getRequestDispatcher(VIEW_PATH + result + VIEW_EXTENSION).forward(req,resp);
            }

        } catch (ActionFactoryException | ActionException e) {
            throw new ServletException(e);
        }


//        Config.set(req.getSession(),Config.FMT_LOCALE,new java.util.Locale("en"));
    }

}
