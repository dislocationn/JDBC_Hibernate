package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import java.util.List;
import java.util.function.Consumer;
import static jm.task.core.jdbc.dao.SQLDetails.*;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class);
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        templFor(new Consumer<Session>() {
            @Override
            public void accept(Session session) {
                session.createNativeQuery(CREATE_TABLE).executeUpdate();
            }
        });
    }

    @Override
    public void dropUsersTable() {
        templFor(new Consumer<Session>() {
            @Override
            public void accept(Session session) {
                session.createNativeQuery(DROP_TABLE).executeUpdate();
            }
        });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        templFor(new Consumer<Session>() {
            @Override
            public void accept(Session session) {
                User user = new User();
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                session.save(user);
                logger.info("User с именем – " + name + " добавлен в базу данных");
            }
        });
    }

    @Override
    public void removeUserById(long id) {
        templFor(new Consumer<Session>() {
            @Override
            public void accept(Session session) {
                User user = session.get(User.class, id);
                if(user != null) {
                    session.delete(user);
                }
            }
        });
        }

    @Override
    public List<User> getAllUsers() {
        try (Session ss = Util.getSessionFactory().openSession();)
        {
            return ss.createQuery("from User", User.class).list();
        }
        catch (Exception e) {
            logger.warn("Users didn't get");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
     templFor(new Consumer<Session>() {
         @Override
         public void accept(Session session) {
             session.createNativeQuery(CLEAR_TABLE).executeUpdate();
         }
     });
    }

    private void templFor(Consumer<Session> ss) {
        Transaction trs = null;
        try (Session se = Util.getSessionFactory().openSession();) {
            trs = se.beginTransaction();
            ss.accept(se);
            trs.commit();
        } catch (Exception e) {
            if (trs != null) trs.rollback();
            logger.warn("Transaction didn't open");
        }
    }
}