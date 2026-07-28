package by.tms.socialnetworkc41onl.dto;

import by.tms.socialnetworkc41onl.constant.Gender;
import by.tms.socialnetworkc41onl.model.FileData;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Пост со всем, что нужно для отрисовки в ленте и профиле */
@Data
public class PostDTO {
    private long id;
    private String text;
    private LocalDateTime createdTime;
    private String createdTimeText;
    private long authorId;
    private String authorName;
    private Long authorAvatarFileId;
    private List<Long> photoFileIds = new ArrayList<>();
    private long likes;
    private long dislikes;
    private String myReaction;
    private boolean subscribedToAuthor;
    private boolean ownPost;
    private List<CommentDTO> comments = new ArrayList<>();

    /**
     * @author Ирина Мизгир
     * @date 25.07.2026 22:10
     */

    public static record EditProfileDto (
            long userId,
            String firstName,
            String lastName,
            Gender gender,
            LocalDate birthday,
            String about,
            FileData photoData
    ) {
    }
}
