package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDaoJDBC = new UserDaoJDBCImpl();
    private final UserDao userDaoHibernate = new UserDaoHibernateImpl();

    private final boolean useHibernate = false;


    public void createUsersTable() {
        if (useHibernate) {
            userDaoHibernate.createUsersTable();
        } else {
            userDaoJDBC.createUsersTable();
        }
    }

    public void dropUsersTable() {
        if (useHibernate) {
            userDaoHibernate.dropUsersTable();
        } else {
            userDaoJDBC.dropUsersTable();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (useHibernate) {
            userDaoHibernate.saveUser(name, lastName, age);
        } else {
            userDaoJDBC.createUsersTable();
        }
    }

    public void removeUserById(long id) {
        if (useHibernate) {
            userDaoHibernate.removeUserById(id);
        } else {
            userDaoJDBC.removeUserById(id);
        }
    }

    public List<User> getAllUsers() {
        if (useHibernate) {
            return userDaoHibernate.getAllUsers();
        } else {
            return userDaoJDBC.getAllUsers();
        }
    }


    public void cleanUsersTable() {
        if (useHibernate) {
            userDaoHibernate.cleanUsersTable();
        } else {
            userDaoJDBC.cleanUsersTable();
        }
    }
}
