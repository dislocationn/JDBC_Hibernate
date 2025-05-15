package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.NullServiceException;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

public class Util {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "fifi";
    private static final String password = "Platina300nba";
    private static final String driver = "org.postgresql.Driver";

    public static Connection getConnectionThru() throws SQLException {
        return DriverManager.getConnection(url, username, password);

    }

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "fifi";
    private static final String JDBC_PASSWORD = "Platina300nba";


    private static SessionFactory sessionFactory;

    public static Connection getJdbcConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к базе данных через JDBC", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Указываем настройки Hibernate (аналог hibernate.cfg.xml)
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", JDBC_URL);
                configuration.setProperty("hibernate.connection.username", JDBC_USERNAME);
                configuration.setProperty("hibernate.connection.password", JDBC_PASSWORD);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // или validate / none / create-drop

                // Добавление аннотированного класса
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка создания Hibernate SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}

