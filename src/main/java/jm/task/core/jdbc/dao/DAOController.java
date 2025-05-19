package jm.task.core.jdbc.dao;

import org.jboss.logging.Logger;
import java.io.InputStream;
import java.util.Properties;

public class DAOController {
    private static final Logger logger = Logger.getLogger(DAOController.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DAOController.class.getClassLoader().getResourceAsStream("controller.properties")) {
            props.load(input);
        } catch (Exception e) {
            logger.warn("Could not load controller.properties", e);
        }
    }

    public static UserDao getUserDao() {
        String type = props.getProperty("dao.impl", "jdbc").toLowerCase();
        return switch (type) {
            case "hibernate" -> new UserDaoHibernateImpl();
            case "jdbc" -> new UserDaoJDBCImpl();
            default -> throw new IllegalArgumentException("Unsupported dao.impl: " + type);
        };
    }
}

