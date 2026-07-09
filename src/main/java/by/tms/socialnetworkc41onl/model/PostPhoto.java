package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostPhoto {
    private Long id;
    private Long fileId;
    private Long postId;
    private LocalDateTime createdDate;
}
