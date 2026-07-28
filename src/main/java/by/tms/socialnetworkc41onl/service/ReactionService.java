package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.PostReactionDao;
import by.tms.socialnetworkc41onl.model.PostReaction;
import by.tms.socialnetworkc41onl.model.ReactionType;

import java.util.Optional;

/** Лайки и дизлайки*/
public class ReactionService {

    private final PostReactionDao reactionDao = new PostReactionDao();

    public void react(long userId, long postId, ReactionType type) {
        Optional<PostReaction> existing = reactionDao.findByUserAndPost(userId, postId);
        if (existing.isEmpty()) {
            PostReaction reaction = new PostReaction();
            reaction.setUserId(userId);
            reaction.setPostId(postId);
            reaction.setReactionType(type);
            reactionDao.save(reaction);
            return;
        }
        PostReaction reaction = existing.get();
        if (reaction.getReactionType() == type) {
            reactionDao.delete(reaction.getId());     // повторное нажатие — снимаем
        } else {
            reactionDao.update(reaction.getId(), type); // меняем тип
        }
    }
}
