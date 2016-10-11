package dao.interfaces;

import models.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sic on 12.10.2016.
 */
public interface OrderDao {

    public void addOrder(Order order) throws SQLException;

    public Order getOrder(int id) throws SQLException;

    public List<Order> getOrders() throws SQLException;
}
