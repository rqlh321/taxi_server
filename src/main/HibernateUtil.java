package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory ourInstance;

    static {
        ourInstance = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return ourInstance;
    }

    private HibernateUtil() {
    }
}