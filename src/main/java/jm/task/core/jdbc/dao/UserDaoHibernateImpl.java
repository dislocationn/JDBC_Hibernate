package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tsa = null;
        try(Session ss = Util.getSessionFactory().openSession();)
        {tsa = ss.beginTransaction();
            ss.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age SMALLINT)").executeUpdate();
            tsa.commit();
        } catch (Exception e) {
            if (tsa != null) tsa.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session ss = Util.getSessionFactory().openSession();) {
            Transaction tsa = ss.beginTransaction();
            ss.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tsa.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session ss = Util.getSessionFactory().openSession();) {
            Transaction tsa = ss.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            ss.save(user);
            tsa.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tsa = null;
        try (Session ss = Util.getSessionFactory().openSession();) {
            tsa = ss.beginTransaction();
            User user = ss.get(User.class, id);
            if (user != null) {
                ss.delete(user);
                tsa.commit();
            }
        }
        catch (Exception e) {
            if (tsa != null) tsa.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session ss = Util.getSessionFactory().openSession();)
        {
            return ss.createQuery("from User", User.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void cleanUsersTable() {
        Transaction tsa = null;
        try (Session ss = Util.getSessionFactory().openSession();) {
            tsa = ss.beginTransaction();
            ss.createQuery("DELETE from User").executeUpdate();
            tsa.commit();
        } catch (Exception e) {
            if (tsa != null) tsa.rollback();
            e.printStackTrace();
        }
    }
}