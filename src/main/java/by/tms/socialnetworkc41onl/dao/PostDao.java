/**
 * Classname    PostDao
 *
 * @version 0.01
 * @author Aleksei Borzetsov
 * date         19.07.2026
 */

package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.File;
import by.tms.socialnetworkc41onl.model.Post;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с постами
 */
public class PostDao {

    private static final String INSERT_POSTS = "INSERT INTO POSTS (POST_TEXT, USER_ID) VALUES (?, ?)";
    private static final String INSERT_POST_PHOTOS = "INSERT INTO POST_PHOTOS (FILE_ID, POST_ID) VALUES (?, ?)";

    private static final String FIND_POST_BY_ID = "SELECT * FROM POSTS WHERE ID = ?";
    private static final String FIND_ALL_POSTS_BY_USER_ID = "SELECT * FROM POSTS WHERE USER_ID = ?";
    private static final String FIND_ALL_POSTS_BY_DATE =
            "SELECT * FROM POSTS ORDER BY CREATED_DATE DESC LIMIT ? OFFSET ?";
    private static final String FIND_ALL_POST_PHOTOS_BY_ID = "SELECT * FROM POST_PHOTOS WHERE POST_ID = ?";

    /**
     * Сохраняет пост
     *
     * @param post   пост
     * @param photos список фото поста
     * @return пост с ID
     */
    public Post save(Post post, List<File> photos) {
        try (Connection connection = ConnectionManager.getConnection()) {
            /*Необходимо сохранять все компоненты поста в составе транзакции*/
            connection.setAutoCommit(false);
            /*Загрузить пост в таблицу POSTS*/
            PreparedStatement postStatement = connection.prepareStatement(INSERT_POSTS,
                    Statement.RETURN_GENERATED_KEYS);
            postStatement.setString(1, post.getPostText());
            postStatement.setLong(2, post.getUserId());
            postStatement.executeUpdate();
            try (ResultSet generatedKeys = postStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    /*Получить сгенерированный ID поста*/
                    long postID = generatedKeys.getLong(1);
                    /*Все фото связаны с одним постом*/
                    PreparedStatement photoStatement = connection.prepareStatement(INSERT_POST_PHOTOS,
                            Statement.RETURN_GENERATED_KEYS);
                    photoStatement.setLong(2, postID);
                    /*Для всех фото*/
                    FileDao fileDao = new FileDao();
                    for (File currentPhoto : photos) {
                        /*Загрузить фото в таблицу FILES и получить ID*/
                        photoStatement.setLong(1, fileDao.save(connection, currentPhoto).getId());
                        /*Добавить запись поста и фото в таблицу POST_PHOTOS*/
                        photoStatement.executeUpdate();
                    }
                    /*Нижняя граница транзакции*/
                    connection.commit();
                    post.setId(postID);
                    return post;
                } else {
                    connection.rollback();
                    throw new SQLException("Не удалось получить сгенерированный ID поста");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Не удалось получить сгенерированный ID поста");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении поста", e);
        }
    }

    /**
     * Возвращает пост с указанным ID
     *
     * @param postID ID поста
     * @return пост
     */
    public Optional<Post> findByID(long postID) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_POST_BY_ID)) {

            preparedStatement.setLong(1, postID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractPost(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске поста", e);
        }
    }

    /**
     * Возвращает список ID фото, связанных с указанным постом
     *
     * @param postID ID поста
     * @return список ID фото указанного поста
     */
    public List<Long> findPhotoFileIDs(long postID) {
        List<Long> foundPhotos = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_POST_PHOTOS_BY_ID)) {

            preparedStatement.setLong(1, postID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    foundPhotos.add(resultSet.getLong("FILE_ID"));
                }
            }
            return foundPhotos;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске фото поста", e);
        }
    }

    /**
     * Возвращает список "свежих" постов
     *
     * @param limit  максимальное количество постов
     * @param offset смещение
     * @return список постов
     */
    public List<Post> findLatest(int limit, int offset) {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_POSTS_BY_DATE)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(extractPost(resultSet));
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске постов", e);
        }
    }

    /**
     * Возвращает список постов указанного пользователя
     *
     * @param userID ID пользователя
     * @return список постов
     */
    public List<Post> findByAuthor(long userID) {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_POSTS_BY_USER_ID)) {

            preparedStatement.setLong(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(extractPost(resultSet));
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске постов", e);
        }
    }

    /**
     * Возвращает список постов по указанным подпискам
     *
     * @param userIDs список ID пользователей, на которых есть подписки
     * @param limit   максимальное количество постов
     * @param offset  смещение
     * @return список постов
     */
    public List<Post> findBySubscriptions(List<Long> userIDs, int limit, int offset) {
        List<Post> posts = new ArrayList<>();
        /*Составить строку из '?' по количеству пользователей из userIDs*/
        String userList = String.join(", ", Collections.nCopies(userIDs.size(), "?"));
        try (Connection connection = ConnectionManager.getConnection();
                /*Включить в запрос перечень ID пользователей*/
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM POSTS WHERE USER_ID IN (" + userList + ") " +
                             "ORDER BY CREATED_DATE DESC LIMIT ? OFFSET ? ")) {
            /*Внести в запрос список userIDs*/
            int i = 1;
            for (long currentUserID : userIDs) {
                preparedStatement.setLong(i, currentUserID);
                i++;
            }
            /*Внести в запрос limit и offset*/
            preparedStatement.setInt(i, limit);
            i++;
            preparedStatement.setInt(i, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(extractPost(resultSet));
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске постов", e);
        }
    }

    /**
     * Возвращает пост из ответа СУБД
     *
     * @param resultSet ответ СУБД
     * @return пост
     * @throws SQLException ошибка извлечения данных
     */
    private Post extractPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("ID"));
        post.setPostText(resultSet.getString("POST_TEXT"));
        post.setUserId(resultSet.getLong("USER_ID"));
        post.setCreatedTime(resultSet.getObject("CREATED_DATE", LocalDateTime.class));
        return post;
    }
}
