package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.exception.*;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final String NOT_ENOUGH_MONEY_ERROR = "not.enough.money.error.message";

    public Order getOrderById(int id) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            OrderDao orderDao = factory.getOrderDao();
            Order order = orderDao.findById(id);

            CartService cartService = new CartService();
            Cart cart = cartService.getCartById(order.getCart().getId());
            order.setCart(cart);

            UserService userService = new UserService();
            User user = userService.getUserById(order.getUser().getId());
            order.setUser(user);

            return order;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }
    }

    public Order makeOrder(Order order) throws ServiceException, ServiceNonUniqueFieldException, ServiceExceptionError {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            factory.beginTx();

            OrderDao orderDao = factory.getOrderDao();
            UserDao userDao = factory.getUserDao();
            CartDao cartDao = factory.getCartDao();

            User user = userDao.findById(order.getUser().getId());
            Money userAccount = user.getAccount();
            if (userAccount.isLessThan(order.getTotal())) {
                throw new ServiceExceptionError(NOT_ENOUGH_MONEY_ERROR);
            } else {
                Money balance = userAccount.minus(order.getTotal());
                user.setAccount(balance);
            }

            Cart savedCart = cartDao.save(order.getCart());
            order.setCart(savedCart);
            userDao.save(user);
            Order saveOrder = orderDao.save(order);
            factory.commit();

            return saveOrder;
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e) {
            throw new ServiceNonUniqueFieldException(e);
        }
    }

    public List<Order> getUserOrdersByOrderStatus(int userId, OrderStatus orderStatus) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {

            OrderDao orderDao = factory.getOrderDao();
            UserDao userDao = factory.getUserDao();
            CartDao cartDao = factory.getCartDao();
            ProductDao productDao = factory.getProductDao();


            List<Order> allOrdersByStatus = orderDao.findUserOrdersByStatus(userId, orderStatus);
            User user = userDao.findById(userId);
            List<Cart> userCartsByOrderStatus = cartDao.findUserCartsByOrderStatus(userId, orderStatus);
            List<Product> userProductsByOrderStatus = productDao.findUserProductsByOrderStatus(userId, orderStatus);

            userCartsByOrderStatus = setProductsToCarts(userCartsByOrderStatus, userProductsByOrderStatus);

            for (Order order : allOrdersByStatus) {
                order.setUser(user);
                for (Cart cart : userCartsByOrderStatus) {
                    if (order.getCart().getId().equals(cart.getId())) {
                        order.setCart(cart);
                        break;
                    }
                }
            }
            return allOrdersByStatus;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }

    }

    public List<Order> getAllOrdersByOrderStatus(OrderStatus orderStatus) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {

            OrderDao orderDao = factory.getOrderDao();
            UserDao userDao = factory.getUserDao();
            CartDao cartDao = factory.getCartDao();
            ProductDao productDao = factory.getProductDao();

            List<Order> allOrdersByStatus = orderDao.findAllOrdersByStatus(orderStatus);
            List<User> allUsersByOrderStatus = userDao.findAllUsersByOrderStatus(orderStatus);
            List<Cart> allCartsByOrderStatus = cartDao.findAllCartsByOrderStatus(orderStatus);
            List<Product> allProductsByOrderStatus = productDao.findAllProductsByOrderStatus(orderStatus);

            allCartsByOrderStatus = setProductsToCarts(allCartsByOrderStatus, allProductsByOrderStatus);

            for (Order order : allOrdersByStatus) {

                for (User user : allUsersByOrderStatus) {
                    if (order.getUser().getId().equals(user.getId())) {
                        order.setUser(user);
                        break;
                    }
                }

                for (Cart cart : allCartsByOrderStatus) {
                    if (order.getCart().getId().equals(cart.getId())) {
                        order.setCart(cart);
                        break;
                    }
                }

            }
            return allOrdersByStatus;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }

    }

    public void changeOrderStatusById(int orderId, OrderStatus orderStatus) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {

            OrderDao orderDao = factory.getOrderDao();

            Order order = orderDao.findById(orderId);
            order.setStatus(orderStatus);
            orderDao.save(order);
        } catch (DaoException | DaoNonUniqueFieldException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }

    }

    private List<Cart> setProductsToCarts(List<Cart> carts, List<Product> products) {
        for (Cart cart : carts) {
            Map<Product, Integer> cartMap = new HashMap<>();
            for (Map.Entry<Product, Integer> productsQuantity : cart.getCart().entrySet()) {
                for (Product product : products) {
                    if (productsQuantity.getKey().getId().equals(product.getId())) {
                        cartMap.put(product, productsQuantity.getValue());
                        break;
                    }
                }
            }
            cart.setCart(cartMap);
        }
        return carts;
    }
}