package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.UserAggregate;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ирина Мизгир
 * @date 26.07.2026 19:21
 */
public class UserSearchAggregateDao {

    private static final String ALL_USERS_SEARCH_QUERY =
            """
                    SELECT USERS.ID, USERS.FIRST_NAME, USERS.LAST_NAME, USER_PHOTOS.FILE_ID,\s
                    COALESCE(SUBSCRIPTIONS.USER_ID IS NOT NULL, FALSE) AS SUBSCRIBE FROM USERS\s
                    LEFT JOIN USER_PHOTOS ON USERS.ID = USER_PHOTOS.USER_ID AND USER_PHOTOS.CURRENT = TRUE
                    LEFT JOIN SUBSCRIPTIONS ON SUBSCRIPTIONS.USER_ID = ? AND SUBSCRIPTIONS.SUBSCRIPTION_USER_ID = USERS.ID
                    WHERE USERS.ID <> ?;""";

    private static final String USERS_SEARCH_QUERY =
            """
                    SELECT USERS.ID, USERS.FIRST_NAME, USERS.LAST_NAME, USER_PHOTOS.FILE_ID, \s
                    COALESCE(SUBSCRIPTIONS.USER_ID IS NOT NULL, FALSE) AS SUBSCRIBE FROM USERS
                    LEFT JOIN USER_PHOTOS ON USERS.ID = USER_PHOTOS.USER_ID AND USER_PHOTOS.CURRENT = TRUE
                    LEFT JOIN SUBSCRIPTIONS ON SUBSCRIPTIONS.USER_ID = ? AND SUBSCRIPTIONS.SUBSCRIPTION_USER_ID = USERS.ID\s
                    WHERE (USERS.USER_NAME ILIKE ? OR USERS.FIRST_NAME ILIKE ? OR USERS.LAST_NAME ILIKE ?) AND USERS.ID <> ?""";


    public List<UserAggregate> getAllUsersWithExclude(long excludeUserId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ALL_USERS_SEARCH_QUERY)) {

            preparedStatement.setLong(1, excludeUserId);
            preparedStatement.setLong(2, excludeUserId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserAggregate> userAggregates = new ArrayList<>();
                while (resultSet.next()) {
                    userAggregates.add(map(resultSet));
                }
                return userAggregates;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователей.", e);
        }
    }

    public List<UserAggregate> getUsersWithExclude(String query, long excludeUserId) {
        String searchPattern = "%" + (query == null ? "" : query.trim()) + "%";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USERS_SEARCH_QUERY)) {

            preparedStatement.setLong(1, excludeUserId);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            preparedStatement.setString(4, searchPattern);
            preparedStatement.setLong(5, excludeUserId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserAggregate> userAggregates = new ArrayList<>();
                while (resultSet.next()) {
                    userAggregates.add(map(resultSet));
                }
                return userAggregates;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователей.", e);
        }
    }

    private UserAggregate map(ResultSet resultSet) throws SQLException {
        return new UserAggregate(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getObject(4, Long.class),
                resultSet.getBoolean(5)
        );
    }
}
