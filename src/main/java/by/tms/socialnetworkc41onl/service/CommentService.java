package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.CommentDao;
import by.tms.socialnetworkc41onl.model.Comment;

/** Комментарии к постам*/
public class CommentService {

    private final CommentDao commentDao = new CommentDao();

    public Comment add(long userId, long postId, String text) {
        if (text == null || text.isBlank()) {
            return null; // пустой комментарий не сохраняем
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setCommentText(text.trim());
        return commentDao.save(comment);
    }
}
