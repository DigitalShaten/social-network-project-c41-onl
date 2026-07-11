package by.tms.socialnetworkc41onl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostReaction {

    private long id;
    private ReactionType reactionType;
    private long userId;
    private long postId;
    private LocalDateTime createdDate;

}
