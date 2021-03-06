package com.epam.tm.shop.listener;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.exception.PoolException;
import com.epam.tm.shop.exception.PropertyManagerException;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class ContainerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private static final Logger log = LoggerFactory.getLogger(ContainerListener.class);
    private static final String URL_PROPERTY_KEY = "db.url";
    private static final String USER_NAME_PROPERTY_KEY = "db.username";
    private static final String PASSWORD_PROPERTY_KEY = "db.password";
    private static final String MAX_CON_PROPERTY_KEY = "max.connections";
    private static final String DRIVER_CLASS_NAME_PROPERTY_KEY = "db.driverClassName";

    private ConnectionPool pool;

    // Public constructor is required by servlet spec
    public ContainerListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        String url, username, password, driverName;
        int maxCon;
        log.info("start to initialize container listener");
        try {
            PropertyManager manager = new PropertyManager("connection-pool.properties");
            url = manager.getPropertyKey(URL_PROPERTY_KEY);
            username = manager.getPropertyKey(USER_NAME_PROPERTY_KEY);
            password = manager.getPropertyKey(PASSWORD_PROPERTY_KEY);
            maxCon = manager.getIntPropertyKey(MAX_CON_PROPERTY_KEY);
            driverName = manager.getPropertyKey(DRIVER_CLASS_NAME_PROPERTY_KEY);
            log.debug("url : {}", url);
            log.debug("username : {}", username);
            log.debug("password : {}", password);
            log.debug("maxCon : {}", maxCon);
            log.debug("driverName : {}", driverName);
            ConnectionPool pool = new ConnectionPool(url, username, password, maxCon, driverName);
            JdbcDaoFactory.setPool(pool);
            log.info("connection pool was created successfully");
        } catch (PropertyManagerException e) {
            log.error("getting properties was failed and connection pool was not created", e);
        } catch (PoolException e) {
            log.error("creating connection pool was failed", e);
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            JdbcDaoFactory.getPool().close();
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
