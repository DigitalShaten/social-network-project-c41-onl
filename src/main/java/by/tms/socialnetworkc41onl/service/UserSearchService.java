package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.UserSearchAggregateDao;
import by.tms.socialnetworkc41onl.model.UserAggregate;
import by.tms.socialnetworkc41onl.model.UserCardView;

import java.util.List;

/**
 * @author Ирина Мизгир
 * @date 26.07.2026 18:10
 */
public class UserSearchService {

    private final UserSearchAggregateDao userSearchAggregateDao = new UserSearchAggregateDao();

    public List<UserCardView> getAllUsersWithExclude(long excludeUserId) {
        return userSearchAggregateDao.getAllUsersWithExclude(excludeUserId).stream()
                .map(this::map)
                .toList();
    }

    private UserCardView map(UserAggregate userAggregate) {
        return new UserCardView(
                userAggregate.userId(),
                userAggregate.firstName(),
                userAggregate.lastName(),
                userAggregate.fileId(),
                userAggregate.subscribed()
        );
    }

    public List<UserCardView> getUsersWithExclude(String query, long userId) {
        return userSearchAggregateDao.getUsersWithExclude(query,userId).stream()
                .map(this::map)
                .toList();
    }
}
