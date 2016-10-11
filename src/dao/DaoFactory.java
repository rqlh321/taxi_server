package dao;

import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;

public class DaoFactory {
    private static UserDaoImpl userDaoImpl = null;
    private static OrderDaoImpl orderDaoImpl = null;
    private static DaoFactory instance = null;

    private DaoFactory() {
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public UserDaoImpl getDriverDAOImpl() {
        if (userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl();
        }
        return userDaoImpl;
    }

    public OrderDaoImpl getOrderDAOImpl() {
        if (orderDaoImpl == null) {
            orderDaoImpl = new OrderDaoImpl();
        }
        return orderDaoImpl;
    }
}
