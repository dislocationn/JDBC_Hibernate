package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Danil", "Chernov", (byte) 26);
        service.saveUser("Matvey", "Perestoronin", (byte) 21);
        service.saveUser("Leonid", "Malinov", (byte) 27);
        service.saveUser("Anfisa", "Suzdaltseva", (byte) 27);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}