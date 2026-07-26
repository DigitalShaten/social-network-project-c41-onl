package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.*;

/**
 * @author Ирина Мизгир
 * @date 25.07.2026 23:05
 */
public class UserPhotoDao {

    private static final String UNMARK_CURRENT_USER_PHOTO = "UPDATE USER_PHOTOS SET CURRENT=false WHERE USER_ID=?";

    private static final String INSERT_CURRENT_USER_PHOTO = "INSERT INTO USER_PHOTOS (CURRENT,FILE_ID,USER_ID) VALUES (true,?,?)";

    private static final String GET_CURRENT_USER_PHOTO_FILE_ID = "SELECT FILE_ID FROM USER_PHOTOS WHERE CURRENT=true AND USER_ID=? LIMIT 1";

    public void updateCurrentUserPhoto(long fileId, long userId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UNMARK_CURRENT_USER_PHOTO)) {
            updateStatement.setLong(1, userId);
            updateStatement.executeUpdate();

            try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_CURRENT_USER_PHOTO)) {
                insertStatement.setLong(1, fileId);
                insertStatement.setLong(2, userId);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Исключение при обновлении данных фотографии", e);
        }
    }

    public Long getCurrentPhotoFileIdOrNull(long userId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(GET_CURRENT_USER_PHOTO_FILE_ID)) {
            updateStatement.setLong(1, userId);
            try (ResultSet resultSet = updateStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong("FILE_ID") : null;
            }


        } catch (SQLException e) {
            throw new RuntimeException("Исключения при поиске текущей фотографии пользователя", e);
        }
    }
}
