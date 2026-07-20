package by.tms.socialnetworkc41onl.dao;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.model.UserPhoto;
import lombok.Data;

import java.sql.*;
import java.util.Optional;

@Data
public class UserPhotoDao {


 public void save(Connection connection, UserPhoto photo) throws SQLException {
  Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
  try {
   PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_photos(file_id, current) VALUES (?, ?)");
   connection.setAutoCommit(false);
   preparedStatement.setLong(1, photo.getFileId());
   int rows = preparedStatement.executeUpdate();

   if (rows != 1) {
    throw new SQLException("Ожидалась 1 строка, а затронуто: " + rows);
   }
  } catch (SQLException e) {
   throw new RuntimeException(e);
  }
 }
public void unsetCurrentForUser(Connection con, long userId)throws SQLException{
 Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

 try {
  PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_photos(file_id, current) VALUES (?, ?)");
  preparedStatement.setLong(1, userId);
  int rowsUpdated = preparedStatement.executeUpdate();
  if (rowsUpdated > 0) {
   System.out.println("Снято current с " + rowsUpdated + " фото пользователя " + userId);
  }
 }  catch (SQLException e) {
  throw new RuntimeException(e);
 }
}
public Optional<UserPhoto>finduserid(Connection connectionlong, long userId)throws SQLException{
 Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
 try {
  PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, user_id, * FROM user_photos WHERE file_id= ?");
  preparedStatement.setLong(1, userId);
  ResultSet resultSet = preparedStatement.executeQuery();
  if (resultSet.next()) {
   UserPhoto userPhoto = new UserPhoto();
   userPhoto.setUserId(resultSet.getLong("id"));
   userPhoto.setId(resultSet.getLong("id"));

   return Optional.of(new UserPhoto());
  } else {
   return Optional.empty();
  }
 }catch (SQLException e) {
   throw new RuntimeException(e);
  }
}
}





