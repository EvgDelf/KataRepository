package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String create = "CREATE TABLE users (Id INT PRIMARY KEY AUTO_INCREMENT,Name VARCHAR(30) NOT NULL ,LastName VARCHAR (30) NOT NULL," +
                "Age INT NOT NULL)";
        try(PreparedStatement statement = Util.connection().prepareStatement(create)){
           statement.execute();
        } catch (SQLException e) {
        }

    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";
        try(PreparedStatement statement = Util.connection().prepareStatement(drop)){
            statement.executeUpdate();
        } catch (SQLException e) {}
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "INSERT INTO users (Name, LastName, Age) VALUES (?, ?, ?)";
        try(PreparedStatement statement = Util.connection().prepareStatement(save)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM users WHERE Id = ?";
        try(PreparedStatement statement = Util.connection().prepareStatement(remove)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String all = "SELECT * FROM users";
        List<User> userList = new LinkedList<>();
        try(PreparedStatement statement = Util.connection().prepareStatement(all)){
            ResultSet res = statement.executeQuery();
            while(res.next()){

                String name = res.getString("Name");
                String lastName = res.getString("LastName");
                int age = res.getInt("Age");
                User user = new User(name, lastName, (byte)age);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String remove = "DELETE FROM users";
        try(Statement statement = Util.connection().createStatement()){
            statement.executeUpdate(remove);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
