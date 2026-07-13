package by.tms.socialnetworkc41onl.model;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class Subscription {
    private long id;
    private long userId;
    private long subscriptionUserId;
    private LocalDateTime createdDate;
}
