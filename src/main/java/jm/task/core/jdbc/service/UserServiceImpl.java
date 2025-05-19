package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final static Properties propOfCon = new Properties();

    static {
        try (InputStream input = UserServiceImpl.class.getClassLoader()
                .getResourceAsStream("controller.properties")) {
            propOfCon.load(input);
        } catch (Exception e) {
            logger.warn("Failed to load controller.properties during static initialization", e);
        }
    }

    public static UserDao getUserDao() {
        String type = propOfCon.getProperty("dao.impl");
        if (type.equals("jdbc")) {
            return new UserDaoJDBCImpl();
        } else {
            return new UserDaoHibernateImpl();
        }
    }

    public void createUsersTable() {
        getUserDao().createUsersTable();
    }

    public void dropUsersTable() {
        getUserDao().dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        getUserDao().saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        getUserDao().removeUserById(id);
    }

    public List<User> getAllUsers() {
        return getUserDao().getAllUsers();
    }

    public void cleanUsersTable() {
        getUserDao().cleanUsersTable();
    }
}
