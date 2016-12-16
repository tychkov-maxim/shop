package com.epam.tm.shop.servlet;

import com.epam.tm.shop.action.LoginAction;
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

@WebServlet(value = "/controller")
public class MainServlet extends HttpServlet {

    public static final Logger log = LoggerFactory.getLogger(MainServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Process the servlet");
        LoginAction loginAction = new LoginAction();
        PrintWriter writer = resp.getWriter();
        writer.print(loginAction.execute(req, resp));
        writer.close();
        Config.set(req.getSession(),Config.FMT_LOCALE,new java.util.Locale("en"));
    }

}
