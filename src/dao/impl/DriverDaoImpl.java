package dao.impl;

import dao.DaoFactory;
import dao.interfaces.DriverDao;
import main.HibernateUtil;
import models.Area;
import models.Driver;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class DriverDaoImpl implements DriverDao {
    @Override
    public void addDriver(Driver driver) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(driver);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteDriver(Driver driver) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(driver);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Driver getDriver(int id) throws SQLException {
        Session session = null;
        Driver driver = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            driver = session.load(Driver.class, id);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return driver;
    }

    @Override
    public Driver getDriver(String login) throws SQLException {
        Session session = null;
        List<Driver> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            list = session.createCriteria(Driver.class)
                    .add(Restrictions.in("login", login))
                    .list();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                if (list != null && list.size() > 0) return list.get(0);
                else return null;
            } else return null;
        }
    }

    @Override
    public int countDriversInArea(int idArea) throws SQLException {
        int count = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Area area = DaoFactory.getInstance().getAreaDAOImpl().getArea(idArea);
            count = session.createCriteria(Driver.class)
                    .add(Restrictions.in("area", area))
                    .list()
                    .size();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return count;
    }

    @Override
    public void setStatus(int id, int newStatus) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Driver driver = session.load(Driver.class, id);
            driver.setStatus(newStatus);
            session.update(driver);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void setArea(int id, Area area) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Driver driver = session.load(Driver.class, id);
            driver.setArea(area);
            session.update(driver);
            area.getDrivers().add(driver);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}