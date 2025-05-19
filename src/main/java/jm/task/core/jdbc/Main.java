package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.DAOInjector;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = injectUserService();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 28);
        userService.saveUser("Danil", "Chernov", (byte) 26);
        userService.saveUser("Matvey", "Perestoronin", (byte) 21);
        userService.saveUser("Leonid", "Malinov", (byte) 19);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    private static UserService injectUserService() {
        return new UserServiceImpl(DAOInjector.getUserDao());
    }
}