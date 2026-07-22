package by.tms.socialnetworkc41onl.dao;
import by.tms.socialnetworkc41onl.model.UserPhoto;
import by.tms.socialnetworkc41onl.util.ConnectionManager;


import java.sql.*;
import java.util.Optional;


public class UserPhotoDao {

 public void save(Connection connection, UserPhoto photo) throws SQLException {
  String sql = "INSERT INTO user_photos(file_id, current,user_id) VALUES (?, ?, ?)";
  try (Connection con = ConnectionManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
       PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
   preparedStatement.setLong(1, photo.getFileId());
   preparedStatement.setLong(2, photo.getUserId());
   preparedStatement.setBoolean(3, photo.isCurrent());
   int rows = preparedStatement.executeUpdate();
   if (rows != 1) {
    throw new SQLException("Ожидалась 1 строка, а затронуто: " + rows);
   }
  } catch (SQLException e) {
   throw new RuntimeException(e);
  }
 }

 public void unsetCurrentForUser(Connection con, long USER_ID) throws SQLException {


  try (Connection connection = ConnectionManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
       PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USER_PHOTOS SET CURRENT = FALSE WHERE USER_ID = ? AND CURRENT = TRUE")) {
   preparedStatement.setLong(1, USER_ID);
   int rowsUpdated = preparedStatement.executeUpdate();
   if (rowsUpdated > 0) {
    System.out.println("Снято current с " + rowsUpdated + " фото пользователя " + USER_ID);
   }
  } catch (SQLException e) {
   throw new RuntimeException(e);
  }
 }

 public Optional<Long> finduserid(Connection connectionlong, long USER_ID) throws SQLException, RuntimeException {

  try (Connection connection = ConnectionManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
       PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, current, file_id, user_id, created_date, * FROM user_photos WHERE user_id= ? AND current = TRUE")) {
   preparedStatement.setLong(1, USER_ID);
   ResultSet resultSet = preparedStatement.executeQuery();
   if (resultSet.next()) {
    UserPhoto userPhoto = new UserPhoto();
    userPhoto.setUserId(resultSet.getLong("USER_ID"));
    userPhoto.setId(resultSet.getLong("ID"));
    userPhoto.setCurrent(resultSet.getBoolean("CURRENT"));
    return Optional.of(resultSet.getLong("FILE_ID"));
   }
  } catch (RuntimeException e) {
   throw new RuntimeException(e);
  }
     return Optional.empty();
 }
}






