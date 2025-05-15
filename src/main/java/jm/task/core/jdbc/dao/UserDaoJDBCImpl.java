package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection cn = Util.getConnectionThru();) {
            PreparedStatement st = cn.prepareStatement("CREATE TABLE if not exists users (id bigserial primary key, name varchar(50), lastname varchar(100), age smallint)");
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        try (Connection cn = Util.getConnectionThru();) {
            PreparedStatement st = cn.prepareStatement("DROP TABLE if exists users");
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnectionThru();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
        }


    }

    public void removeUserById(long id) {
        try (Connection cn = Util.getConnectionThru();) {
            PreparedStatement st = cn.prepareStatement("DELETE users WHERE id");
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection cn = Util.getConnectionThru();) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, lastname, age FROM users");
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователей", e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection cn = Util.getConnectionThru();) {
            Statement st = cn.createStatement();
            st.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
        }
    }
}