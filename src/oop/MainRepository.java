package oop;

import oop.entities.User;
import oop.repositories.UserRepository;
import oop.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "gjhfqr102";
    private static final String DB_URL =
            "jdbc:postgresql://localhost:5432/testdb_11_503";

    public static void main(String[] args) throws SQLException {

        try (Connection connection =
                     DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            UserRepository userRepository =
                    new UsersRepositoryJdbcImpl(connection);

            // 1. Шесть пользователей отправляются одним SQL-запросом.
            List<User> users = Arrays.asList(
                    new User(null, "Ivan", "Ivanov", 20),
                    new User(null, "Petr", "Petrov", 21),
                    new User(null, "Anna", "Sidorova", 20),
                    new User(null, "Maria", "Smirnova", 22),
                    new User(null, "Alex", "Kozlov", 21),
                    new User(null, "Olga", "Volkova", 23)
            );

            userRepository.saveAll(users);

            // 2. Вывод пользователей по условию: возраст = 20.
            List<User> usersByAge = userRepository.findAllByAge(20);

            System.out.println("Пользователи с возрастом 20:");
            usersByAge.forEach(user ->
                    System.out.println(
                            user.getId() + " | " +
                            user.getName() + " " +
                            user.getSurname() + " | age=" +
                            user.getAge()
                    )
            );
        }
    }
}
