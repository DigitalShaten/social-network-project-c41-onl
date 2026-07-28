package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.PostDao;
import by.tms.socialnetworkc41onl.dao.SubscriptionDao;
import by.tms.socialnetworkc41onl.dto.PostDTO;
import by.tms.socialnetworkc41onl.model.Post;

import java.util.ArrayList;
import java.util.List;

/** Лента: посты с автором, фото, реакциями и комментариями. */
public class FeedService {

    private static final int FEED_LIMIT = 100;

    private final PostDao postDao = new PostDao();
    private final SubscriptionDao subscriptionDao = new SubscriptionDao();
    private final PostDTOAssembler assembler = new PostDTOAssembler();

    /** Все последние посты (главная лента). */
    public List<PostDTO> feed(long viewerId) {
        return feed(viewerId, false);
    }

    public List<PostDTO> feed(long viewerId, boolean subscriptionsOnly) {
        List<Post> posts;
        if (subscriptionsOnly) {
            List<Long> followed = subscriptionDao.findSubscriptionUserIds(viewerId);
            if (followed.isEmpty()) {
                return new ArrayList<>(); // нет подписок — пустая лента (и без IN () в SQL)
            }
            posts = postDao.findBySubscriptions(followed, FEED_LIMIT, 0);
        } else {
            posts = postDao.findFeed();
        }
        return assembler.assemble(posts, viewerId);
    }
}
