package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDao {

    public User save(User user) {
        String sqlQuery = "INSERT INTO users (user_name, email, password_hash, status, first_name, last_name, birthday, gender, about) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.setBoolean(4, user.isStatus());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setObject(7, user.getBirthday());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setString(9, user.getAbout());
            preparedStatement.executeUpdate();
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    user.setId(generatedKeys.getLong(1));
                } else{
                    throw new SQLException("Не удалось получить сгенерированный id.");
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения нового пользователя.", e);
        }
    }

    public Optional<User> findById(long id) {
        String sqlQuery = "SELECT * FROM users WHERE id=?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setStatus(resultSet.getBoolean("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setBirthday(resultSet.getObject("birthday", LocalDate.class));
                    user.setGender(resultSet.getString("gender"));
                    user.setAbout(resultSet.getString("about"));
                    user.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по id.", e);
        }
    }

    public Optional<User> findByUsername(String username) {
        String sqlQuery = "SELECT * FROM users WHERE user_name=?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setStatus(resultSet.getBoolean("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setBirthday(resultSet.getObject("birthday", LocalDate.class));
                    user.setGender(resultSet.getString("gender"));
                    user.setAbout(resultSet.getString("about"));
                    user.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по username.", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        String sqlQuery = "SELECT * FROM users WHERE email=?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setStatus(resultSet.getBoolean("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setBirthday(resultSet.getObject("birthday", LocalDate.class));
                    user.setGender(resultSet.getString("gender"));
                    user.setAbout(resultSet.getString("about"));
                    user.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по email.", e);
        }
    }

    public void update(User user) {
        String sqlQuery = "UPDATE users SET status=?, first_name=?, last_name=?, birthday=?, gender=?, about=?, password_hash=? WHERE id=?";
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setBoolean(1, user.isStatus());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setObject(4, user.getBirthday());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getAbout());
            preparedStatement.setString(7, user.getPasswordHash());
            preparedStatement.setLong(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления пользователя.", e);
        }
    }

}

