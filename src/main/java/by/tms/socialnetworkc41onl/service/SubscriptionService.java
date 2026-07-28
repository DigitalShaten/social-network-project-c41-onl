package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.SubscriptionDao;
import by.tms.socialnetworkc41onl.model.Subscription;

/**
 * Подписка/отписка.
 */
public class SubscriptionService {

    private final SubscriptionDao subscriptionDao = new SubscriptionDao();

    public void subscribe(long userId, long targetUserId) {
        if (userId == targetUserId) {
            return; // на себя подписаться нельзя (в БД это ещё и CHECK)
        }
        if (subscriptionDao.exists(userId, targetUserId)) {
            return; // уже подписан — тихо выходим, дубля не создаём
        }
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setSubscriptionUserId(targetUserId);
        subscriptionDao.save(subscription);
    }

    public void unsubscribe(long userId, long targetUserId) {
        subscriptionDao.delete(userId, targetUserId);
    }
}
