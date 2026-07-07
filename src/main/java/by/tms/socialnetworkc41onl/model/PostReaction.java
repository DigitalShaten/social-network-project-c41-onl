package by.tms.socialnetworkc41onl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostReaction {
    long id;
    ReactionType reactionType;
    long userId;
    long postId;
    LocalDateTime createdDate;
}
