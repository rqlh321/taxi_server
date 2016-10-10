package dao.impl;

import dao.interfaces.AreaDao;
import main.HibernateUtil;
import models.Area;
import models.Driver;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class AreaDaoImpl implements AreaDao {
    @Override
    public void addArea(Area area) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(area);
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
    public void deleteArea(Area area) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(area);
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
    public Area getArea(int id) throws SQLException {
        Session session = null;
        Area area = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            area = session.load(Area.class, id);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return area;
    }

    @Override
    public List<Area> getAreas() throws SQLException {
        List<Area> areas = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            areas = session.createCriteria(Area.class).list();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return areas;
    }
}
