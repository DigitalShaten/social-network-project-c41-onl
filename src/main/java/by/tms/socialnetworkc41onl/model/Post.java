package by.tms.socialnetworkc41onl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {

    private long id;
    private String postText;
    private long userId;
    private LocalDateTime createdTime;

}
