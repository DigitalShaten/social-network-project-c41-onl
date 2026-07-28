package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.CommentDao;
import by.tms.socialnetworkc41onl.dao.PostPhotoDao;
import by.tms.socialnetworkc41onl.dao.PostReactionDao;
import by.tms.socialnetworkc41onl.dao.SubscriptionDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.Comment;
import by.tms.socialnetworkc41onl.model.Post;
import by.tms.socialnetworkc41onl.model.PostPhoto;
import by.tms.socialnetworkc41onl.model.PostReaction;
import by.tms.socialnetworkc41onl.model.ReactionType;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.dto.CommentDTO;
import by.tms.socialnetworkc41onl.dto.PostDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Класс собирает готовые к показу PostDTO из постов */
public class PostDTOAssembler {
    private final UserDao userDao = new UserDao();

    /** TODO поправить, как будет UserPhotoDao */
//    private final UserPhotoDao userPhotoDao = new UserPhotoDao();
    private final PostPhotoDao postPhotoDao = new PostPhotoDao();
    private final PostReactionDao postReactionDao = new PostReactionDao();
    private final CommentDao commentDao = new CommentDao();
    private final SubscriptionDao subscriptionDao = new SubscriptionDao();

    public List<PostDTO> assemble(List<Post> posts, long dtoId) {
        Map<Long, User> userCache = new HashMap<>();
        Map<Long, Long> avatarCache = new HashMap<>();
        List<PostDTO> views = new ArrayList<>();
        for (Post post : posts) {
            views.add(build(post, dtoId, userCache, avatarCache));
        }
        return views;
    }

    private PostDTO build(Post post, long dtoId, Map<Long, User> userCache, Map<Long, Long> avatarCache) {
        PostDTO view = new PostDTO();
        view.setId(post.getId());
        view.setText(post.getPostText());
        view.setCreatedTime(post.getCreatedTime());
        view.setAuthorId(post.getUserId());
        view.setOwnPost(post.getUserId() == dtoId);

        User author = user(post.getUserId(), userCache);
        if (author != null) {
            view.setAuthorName(author.getUserName());
        }

        view.setAuthorAvatarField(avatar(post.getUserId(), avatarCache));

        List<Long> photoIds = new ArrayList<>();
        for (PostPhoto photo : postPhotoDao.findByPostId(post.getId())) {
            photoIds.add(photo.getFileId());
        }

        view.setPhotoFileIds(photoIds);

        view.setLikes(postReactionDao.countByPostAndType(post.getId(), ReactionType.LIKE));
        view.setDislikes(postReactionDao.countByPostAndType(post.getId(), ReactionType.DISLIKE));

        Optional<PostReaction> mine = postReactionDao.findByUserAndPost(dtoId, post.getId());
        mine.ifPresent(r -> view.setMyReaction(r.getReactionType().name()));

        view.setSubscribedToAuthor(post.getUserId() != dtoId
                && subscriptionDao.exists(dtoId, post.getUserId()));

        List<CommentDTO> comments = new ArrayList<>();
        for (Comment comment : commentDao.findByPost(post.getId())) {
            CommentDTO cv = new CommentDTO();
            User commentAuthor = user(comment.getUserId(), userCache);
            cv.setAuthorName(commentAuthor == null ? "—" : commentAuthor.getUserName());
            cv.setAuthorAvatarFileId(avatar(comment.getUserId(), avatarCache));
            cv.setText(comment.getCommentText());
            cv.setCreatedTime(comment.getCreatedTime());
            comments.add(cv);
        }
        view.setComments(comments);
        return view;
    }

    private User user(long id, Map<Long, User> cache) {
        return cache.computeIfAbsent(id, key -> userDao.findById(key).orElse(null));
    }

    private Long avatar(long userId, Map<Long, Long> cache) {
        //TODO временно null
        return null;
        //return cache.computeIfAbsent(userId, key -> userPhotoDao.findCurrentByUserId(key).map(p -> p.getFileId()).orElse(null));
    }

}
