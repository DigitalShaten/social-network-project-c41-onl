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

    public void update(long id, ReactionType type) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REACTION_TYPE_SQL_QUERY)) {
            statement.setString(1, type.name());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка обновления реакции", error);
        }
    }

    // Удаляем реакцию конкретного пользователя на конкретный пост
    public void delete(long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_REACTION_SQL_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка удаления реакции", error);
        }
    }

    public Optional<PostReaction> findByUserAndPost(long userId, long postId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_REACTION_TYPE_BY_USER_AND_POST_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);

            try (ResultSet result = preparedStatement.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        } catch (SQLException error) {
            throw new RuntimeException("Ошибка поиска реакции", error);
        }
    }

    public int countByPostAndType(long postId, ReactionType type) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_REACTION_BY_TYPE_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);
            preparedStatement.setString(2, type.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("amount") : 0;
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка подсчета реакций по типу: LIKE или DISLIKE", ex);
        }
    }

    private PostReaction map(ResultSet result) throws SQLException {
        PostReaction reaction = new PostReaction();
        reaction.setId(result.getLong("ID"));
        reaction.setReactionType(ReactionType.valueOf(result.getString("REACTION_TYPE")));
        reaction.setUserId(result.getLong("USER_ID"));
        reaction.setPostId(result.getLong("POST_ID"));
        Timestamp created = result.getTimestamp("CREATED_DATE");
        reaction.setCreatedDate(created == null ? null : created.toLocalDateTime());
        return reaction;
    }
}
