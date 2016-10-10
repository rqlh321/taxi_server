package dao.interfaces;

import models.Area;
import models.Driver;

import java.sql.SQLException;

/**
 * Created by sic on 05.10.2016.
 */
public interface DriverDao {

    public void addDriver(Driver driver) throws SQLException;

    public void deleteDriver(Driver driver) throws SQLException;

    public Driver getDriver(int id) throws SQLException;

    public Driver getDriver(String login) throws SQLException;

    public int countDriversInArea(int idArea) throws SQLException;

    public void setStatus(int id, int newStatus) throws SQLException;

    public void setArea(int id, Area area) throws SQLException;
}
