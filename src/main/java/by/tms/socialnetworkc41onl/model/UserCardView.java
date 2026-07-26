package by.tms.socialnetworkc41onl.model;

import lombok.Value;


/**
 * @author Ирина Мизгир
 * @date 26.07.2026 18:14
 */
@Value
public class UserCardView {

    long userId;
    String firstName;
    String lastName;
    Long currentFileId;
    boolean subscribed;

}
