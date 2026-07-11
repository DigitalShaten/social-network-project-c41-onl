package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostPhoto {
    private long id;
    private long fileId;
    private long postId;
    private LocalDateTime createdDate;
}
