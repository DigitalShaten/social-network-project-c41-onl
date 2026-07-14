package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.PostReaction;
import by.tms.socialnetworkc41onl.model.ReactionType;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.*;
import java.util.Optional;

public class PostReactionDao {

    private final String SAVE_REACTION_SQL_QUERY = "INSERT INTO post_reactions(reaction_type, user_id,post_id) VALUES(?,?,?)";
    private final String UPDATE_REACTION_TYPE_SQL_QUERY = "UPDATE post_reactions SET reaction_type=? WHERE id=?";
    private final String DELETE_REACTION_SQL_QUERY = "DELETE FROM post_reactions WHERE post_id=? AND user_id=?";
    private final String FIND_REACTION_TYPE_BY_USER_AND_POST_SQL_QUERY = "SELECT reaction_type FROM post_reactions WHERE post_id=? AND user_id=?";
    private final String COUNT_REACTION_BY_TYPE_SQL_QUERY = "SELECT COUNT(*) AS amount FROM post_reactions WHERE post_id=? AND reaction_type=?";

    public PostReaction save(PostReaction postReaction) {
        try (Connection connection = ConnectionManager.getConnection();

             // Просим JDBC вернуть автоматически сгенерированный id после INSERT
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_REACTION_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, postReaction.getReactionType().name());
            preparedStatement.setLong(2, postReaction.getUserId());
            preparedStatement.setLong(3, postReaction.getPostId());
            preparedStatement.executeUpdate();

            // Получаем id записи и сохраняем его в объект postReaction
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    postReaction.setId(keys.getLong(1));
                }
            }
            return postReaction;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранении реакции на пост", e);
        }
    }

    public void update(PostReaction postReaction) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REACTION_TYPE_SQL_QUERY)) {

            preparedStatement.setString(1, postReaction.getReactionType().name());
            preparedStatement.setLong(2, postReaction.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления реакции на пост.", e);
        }
    }

    // Удаляем реакцию конкретного пользователя на конкретный пост
    public void delete(long userId, long postId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REACTION_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления реакции", e);
        }
    }

    public Optional<ReactionType> findByUserAndPost(long userId, long postId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_REACTION_TYPE_BY_USER_AND_POST_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String reactionType = resultSet.getString("reaction_type");

                    //Приводим строку к верхнему регистру, чтобы избежать ошибок при valueOf(),
                    // например если вручную вписали в БД "Like" вместо "LIKE"
                    return Optional.of(ReactionType.valueOf(reactionType.toUpperCase()));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска реакции по id пользователя и id поста", e);
        }
    }

    public int countByPostAndType(long postId, String type) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_REACTION_BY_TYPE_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);
            preparedStatement.setString(2, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("amount") : 0;
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка подсчета реакций по типу: LIKE или DISLIKE", ex);
        }
    }
}
