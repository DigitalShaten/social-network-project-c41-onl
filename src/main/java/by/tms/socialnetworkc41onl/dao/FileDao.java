package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.File;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

/** Доступ к FILES таблице БД. */
public class FileDao {

    private static final String INSERT = "INSERT INTO FILES (FILE_NAME, DATA) VALUES (?, ?)";

    private static final String SELECT_BY_ID = "SELECT ID, FILE_NAME, DATA, CREATED_DATE FROM FILES WHERE ID = ?";

    /** Сохраняет файл, используя собственное соединение. */
    public File save(File file) {
        try (Connection connection = ConnectionManager.getConnection()) {
            return save(connection, file);
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка сохранения файла", error);
        }
    }

    /**
     * Сохраняет файл в рамках существующего соединения, позволяя вызывающему коду включить эту операцию
     * в более крупную транзакцию (например, сохранение поста вместе с фотографиями в PostDao).
     */
    public File save(Connection connection, File file) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, file.getFileName());
            statement.setBytes(2, file.getData());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    file.setId(keys.getLong(1));
                }
            }
            return file;
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка сохранения файла", error);
        }
    }

    public Optional<File> findById(long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        } catch (SQLException error) {
            throw new RuntimeException("Неудалось найти файл", error);
        }
    }

    private File map(ResultSet result) throws SQLException {
        File file = new File();
        file.setId(result.getLong("ID"));
        file.setFileName(result.getString("FILE_NAME"));
        file.setData(result.getBytes("DATA"));
        Timestamp created = result.getTimestamp("CREATED_DATE");
        file.setCreatedDate(created == null ? null : created.toLocalDateTime());
        return file;
    }
}

