package dao.impl;

import dao.interfaces.OrderDao;
import main.HibernateUtil;
import models.Order;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sic on 12.10.2016.
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order);
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
    public Order getOrder(int id) throws SQLException {
        Session session = null;
        Order order = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            order = session.load(Order.class, id);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return order;
    }

    @Override
    public List<Order> getOrders() throws SQLException {
        List<Order> orders = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            orders = session.createCriteria(Order.class).list();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return orders;
    }

}
