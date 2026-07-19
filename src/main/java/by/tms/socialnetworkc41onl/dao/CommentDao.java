package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.Comment;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    private static final String SAVE_COMMENT_SQL_QUERY = """
            INSERT INTO COMMENTS(COMMENT_TEXT, USER_ID, POST_ID)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_BY_POST_SQL_QUERY = """
            SELECT ID, COMMENT_TEXT, USER_ID, POST_ID, CREATED_DATE
            FROM COMMENTS
            WHERE POST_ID = ?
            ORDER BY CREATED_DATE ASC, ID ASC
            """;

    public Comment save(Comment comment) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SAVE_COMMENT_SQL_QUERY,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            preparedStatement.setString(1, comment.getCommentText());
            preparedStatement.setLong(2, comment.getUserId());
            preparedStatement.setLong(3, comment.getPostId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Не удалось получить сгенерированный id комментария.");
                }
            }

            return comment;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения комментария.", e);
        }
    }

    public List<Comment> findByPost(long postId) {
        List<Comment> comments = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_POST_SQL_QUERY)) {

            preparedStatement.setLong(1, postId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    comments.add(mapComment(resultSet));
                }
            }

            return comments;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска комментариев по id поста.", e);
        }
    }

    private Comment mapComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("ID"));
        comment.setCommentText(resultSet.getString("COMMENT_TEXT"));
        comment.setUserId(resultSet.getLong("USER_ID"));
        comment.setPostId(resultSet.getLong("POST_ID"));

        Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");
        comment.setCreatedTime(createdDate == null ? null : createdDate.toLocalDateTime());

        return comment;
    }
}
