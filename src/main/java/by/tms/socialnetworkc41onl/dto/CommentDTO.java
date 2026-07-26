package by.tms.socialnetworkc41onl.dto;

import lombok.Data;

import java.time.LocalDateTime;

/** Комментарий, готовый к показу, с именем автора и аватром */
@Data
public class CommentDTO {
    private String authorName;
    private Long authorAvatarFileId;
    private String text;
    private LocalDateTime createdTime;
}
