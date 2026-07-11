package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserPhoto {
    private long id;
    private Boolean current;
    private Long fileId;
    private Long userId;
    private LocalDateTime createdDate;
}
