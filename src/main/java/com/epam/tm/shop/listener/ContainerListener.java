package com.epam.tm.shop.listener;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ContainerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private ConnectionPool pool;
    public static final Logger log = LoggerFactory.getLogger(ContainerListener.class);
    // Public constructor is required by servlet spec
    public ContainerListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        try {
            pool = new ConnectionPool();
        } catch (PoolException e) {
            log.error("creating connection pool was failed",e);
        }
        JdbcDaoFactory.setPool(pool);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            pool.close();
        } catch (PoolException e) {
            log.error("closing of the connection was failed");
        }

    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
