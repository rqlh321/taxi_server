package dao;

import dao.impl.AreaDaoImpl;
import dao.impl.DriverDaoImpl;

public class DaoFactory {
    private static DriverDaoImpl driverDaoImpl = null;
    private static AreaDaoImpl areaDaoImpl = null;
    private static DaoFactory instance = null;

    private DaoFactory() {
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public DriverDaoImpl getDriverDAOImpl() {
        if (driverDaoImpl == null) {
            driverDaoImpl = new DriverDaoImpl();
        }
        return driverDaoImpl;
    }

    public AreaDaoImpl getAreaDAOImpl() {
        if (areaDaoImpl == null) {
            areaDaoImpl = new AreaDaoImpl();
        }
        return areaDaoImpl;
    }
}
