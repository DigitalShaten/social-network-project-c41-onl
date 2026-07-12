package by.tms.socialnetworkc41onl.model;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class Subscription {
    long id;
    long userId;
    long subscriptionUserId;
    LocalDateTime createdDate;
}
