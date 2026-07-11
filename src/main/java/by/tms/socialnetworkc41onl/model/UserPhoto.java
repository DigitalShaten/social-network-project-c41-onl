package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserPhoto {
    private long id;
    private boolean current;
    private long fileId;
    private long userId;
    private LocalDateTime createdDate;
}
