package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Util {

    private final static Properties jProp = loadJProp();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration conf = new Configuration();
            conf.addProperties(loadHProp());
            conf.addAnnotatedClass(User.class);
            sessionFactory = conf.buildSessionFactory();
        } catch (ExceptionInInitializerError e) {
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnectionThru() throws SQLException {
        return DriverManager.getConnection(
                jProp.getProperty("jdbc.url"),
                jProp.getProperty("jdbc.username"),
                jProp.getProperty("jdbc.password")
        );
    }

    private static Properties loadJProp() {
        Properties prop = new Properties();
        try (InputStream input = Util.class.getClassLoader()
                .getResourceAsStream("jdbc.properties")) {
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException("Oshibka pri zagruzke", e);
        }
    }

    private static Properties loadHProp() {
        Properties prop = new Properties();
        try (InputStream input = Util.class.getClassLoader()
                .getResourceAsStream("hibernate.properties")) {
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException("Oshibka pri zagruzke", e);
        }
    }
}

