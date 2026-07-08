package by.tms.socialnetworkc41onl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    long id;
    String postText;
    long userId;
    LocalDateTime createdTime;

}
