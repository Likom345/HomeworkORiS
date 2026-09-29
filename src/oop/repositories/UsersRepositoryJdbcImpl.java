package oop.repositories;

import oop.entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    private static final String SQL_SELECT_FROM_DRIVERS =
            "SELECT id, first_name, last_name, age FROM drivers";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVERS)) {

            while (resultSet.next()) {
                result.add(mapUser(resultSet));
            }
        }

        return result;
    }

    @Override
    public void saveAll(List<User> users) throws SQLException {
        if (users == null || users.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder(
                "INSERT INTO drivers (first_name, last_name, age) VALUES ");

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            sql.append("(")
                    .append(toSqlString(user.getName()))
                    .append(", ")
                    .append(toSqlString(user.getSurname()))
                    .append(", ")
                    .append(user.getAge())
                    .append(")");

            if (i < users.size() - 1) {
                sql.append(", ");
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        if (age == null) {
            throw new IllegalArgumentException("Возраст не может быть null");
        }

        String sql = SQL_SELECT_FROM_DRIVERS + " WHERE age = " + age;
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                result.add(mapUser(resultSet));
            }
        }

        return result;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age")
        );
    }

    private String toSqlString(String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replace("'", "''") + "'";
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User entity) {
        throw new UnsupportedOperationException("Используйте saveAll()");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Метод пока не реализован");
    }

    @Override
    public void remove(User entity) {
        throw new UnsupportedOperationException("Метод пока не реализован");
    }

    @Override
    public void removeById(Long id) {
        throw new UnsupportedOperationException("Метод пока не реализован");
    }
}
