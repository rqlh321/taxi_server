package dao.interfaces;

import models.User;

import java.sql.SQLException;

/**
 * Created by sic on 05.10.2016.
 */
public interface UserDao {

    public void addUser(User user) throws SQLException;

    public void deleteUser(User user) throws SQLException;

    public User getUser(int id) throws SQLException;

    public User getUser(String login) throws SQLException;

}
