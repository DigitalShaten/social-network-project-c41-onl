package by.tms.socialnetworkc41onl.dao;
import by.tms.socialnetworkc41onl.model.UserPhoto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        UserPhotoDao dao = new UserPhotoDao();

        String file_id = "scr/resources/db"; // путь к файлу на диске
        UserPhoto photo = new UserPhoto();
        photo.setUserId(1L);
        photo.setFileId(Long.parseLong(file_id));
        photo.setCurrent(true);
        long targetUserId = 1L;

            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
                con.setAutoCommit(false);
                UserPhotoDao userPhotoDao = new UserPhotoDao();
                dao.save(connection, photo);
                dao.unsetCurrentForUser(con, targetUserId);
                Optional<UserPhoto> opt = dao.finduserid(con, 1L);
                UserPhoto userPhoto = opt.orElse(null);
                opt.ifPresentOrElse(
                        p -> System.out.println("Нашли фото: " + p.getId()),
                        () -> System.out.println("Фото не найдено — делаем дефолтное поведение")
                );
                        con.commit();

                System.out.println("Фото успешно сохранено в БД!");
                System.out.println("Ссылка (file_path) в БД: " + file_id);


            } catch (SQLException e) {
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException rEx) {
                        rEx.printStackTrace();
                    }
                }
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException cEx) {
                        cEx.printStackTrace();
                    }
                }
            }

    }
}




