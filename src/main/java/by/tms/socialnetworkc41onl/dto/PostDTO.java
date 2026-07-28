package by.tms.socialnetworkc41onl.dto;

import by.tms.socialnetworkc41onl.model.ReactionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Пост со всем, что нужно для отрисовки в ленте и профиле */
@Data
public class PostDTO {
    private long id;
    private String text;
    private LocalDateTime createdTime;
    private long authorId;
    private String authorName;
    private Long authorAvatarField;
    private List<Long> photoFileIds = new ArrayList<>();
    private long likes;
    private long dislikes;
    private String myReaction;
    private boolean subscribedToAuthor;
    private boolean ownPost;
    private List<CommentDTO> comments = new ArrayList<>();
}
