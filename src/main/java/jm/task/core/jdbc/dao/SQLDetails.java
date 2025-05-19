package jm.task.core.jdbc.dao;

public class SQLDetails {

        public SQLDetails() {
        }

        public static final String CREATE_TABLE = "CREATE TABLE if not exists users (id bigserial primary key, name varchar(50), lastname varchar(100), age smallint)";

        public static final String DROP_TABLE = "DROP TABLE if exists users";

        public static final String SAVE_USER = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";

        public static final String REMOVE_USER_BY_ID = "DELETE users WHERE id";

        public static final String GET_ALL = "SELECT id, name, lastname, age FROM users";

        public static final String CLEAR_TABLE = "TRUNCATE TABLE users";
    }