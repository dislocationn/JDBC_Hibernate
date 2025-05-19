package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.jboss.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static jm.task.core.jdbc.dao.SQLDetails.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class);

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        templFor(CREATE_TABLE, "Table didn't create");
    }

    public void dropUsersTable() {
        templFor(DROP_TABLE, "Table didn't drop");
    }

    public void saveUser(String name, String lastName, byte age) {
        templForTwo(
                SAVE_USER,
                new Consumer<PreparedStatement>() {
                    @Override
                    public void accept(PreparedStatement ps) {
                        try {
                            ps.setString(1, name);
                            ps.setString(2, lastName);
                            ps.setByte(3, age);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                "User didn't save"
        );
        logger.info("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        templForTwo(
                REMOVE_USER_BY_ID,
                new Consumer<PreparedStatement>() {
                    @Override
                    public void accept(PreparedStatement ps) {
                        try {
                            ps.setLong(1, id);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                "User didn't remove");
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection cn = Util.getConnectionThru();) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL);
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.warn("Users didn't get");
        }
        return userList;
    }

    public void cleanUsersTable() {
        templFor(CLEAR_TABLE, "Table didn't clean");
    }

    private void templFor(String sql, String error) {
        try (Connection cn = Util.getConnectionThru();
             Statement st = cn.createStatement();) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(error);
        }
    }

    private void templForTwo(String sql, Consumer<PreparedStatement> p, String error) {
        try (Connection cn = Util.getConnectionThru();
             PreparedStatement st = cn.prepareStatement(sql);) {
            p.accept(st);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.warn(error);
        }
    }
}