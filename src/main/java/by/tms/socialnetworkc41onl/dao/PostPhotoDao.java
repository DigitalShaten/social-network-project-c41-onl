package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.PostPhoto;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** Доступ к таблице POST_PHOTOS (связь поста с файлами-картинками). */
public class PostPhotoDao {

    private static final String INSERT =
            "INSERT INTO POST_PHOTOS (FILE_ID, POST_ID) VALUES (?, ?)";

    private static final String SELECT_BY_POST =
            "SELECT ID, FILE_ID, POST_ID, CREATED_DATE FROM POST_PHOTOS WHERE POST_ID = ? ORDER BY ID";

    public PostPhoto save(PostPhoto photo) {
        try (Connection connection = ConnectionManager.getConnection()) {
            return save(connection, photo);
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка сохранения фото поста", error);
        }
    }

    /** Сохраняет запись в рамках переданного соединения (транзакция «пост + фото»). */
    public PostPhoto save(Connection connection, PostPhoto photo) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, photo.getFileId());
            statement.setLong(2, photo.getPostId());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    photo.setId(keys.getLong(1));
                }
            }
            return photo;
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка сохранения фото поста", error);
        }
    }

    /** Фото поста в порядке добавления (по id) — так и рисуется карусель. */
    public List<PostPhoto> findByPostId(long postId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_POST)) {
            statement.setLong(1, postId);
            try (ResultSet result = statement.executeQuery()) {
                List<PostPhoto> photos = new ArrayList<>();
                while (result.next()) {
                    photos.add(map(result));
                }
                return photos;
            }
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка загрузки фото поста", error);
        }
    }

    private PostPhoto map(ResultSet result) throws SQLException {
        PostPhoto photo = new PostPhoto();
        photo.setId(result.getLong("ID"));
        photo.setFileId(result.getLong("FILE_ID"));
        photo.setPostId(result.getLong("POST_ID"));
        Timestamp created = result.getTimestamp("CREATED_DATE");
        photo.setCreatedDate(created == null ? null : created.toLocalDateTime());
        return photo;
    }
}
