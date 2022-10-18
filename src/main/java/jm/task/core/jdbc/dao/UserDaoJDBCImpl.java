package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        final String sql = "CREATE TABLE if not exists users (\n" +
                "  id int NOT NULL AUTO_INCREMENT,\n" +
                "  name varchar(45) NOT NULL,\n" +
                " lastName varchar(45) NOT NULL,\n" +
                " age int NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ") ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error CreateTable");
        }

    }

    public void dropUsersTable() {
        final String sql = "DROP TABLE if EXISTS users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute(sql);

        } catch (SQLException e) {
            System.out.println("Error DropTable");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saveUser");
        }
    }

    public void removeUserById(long id) {
        final String sql = "DELETE FROM users WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removeUser");
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        final String sql = "SELECT * FROM users;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllUsers");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        final String sql = "truncate users;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error cleanUsersTable");
        }
    }
}
