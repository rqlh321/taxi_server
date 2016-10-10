package dao.interfaces;

import models.Area;
import models.Driver;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sic on 05.10.2016.
 */
public interface AreaDao {

    public void addArea(Area area) throws SQLException;

    public void deleteArea(Area area) throws SQLException;

    public Area getArea(int id) throws SQLException;

    public List<Area> getAreas() throws SQLException;
}
