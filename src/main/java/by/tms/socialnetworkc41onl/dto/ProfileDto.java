/**
 * Classname    ProfileDTO
 * @version     0.01
 * @author      Aleksei Borzetsov
 * date         25.07.2026
 */

package by.tms.socialnetworkc41onl.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

    /*Данные из USERS*/
    String userName;
    String firstName;
    String lastName;
    LocalDate birthDay;
    String gender;
    String about;

    /*Данные из USER_PHOTOS*/
    long avatarFileId;

    /*Данные из POSTS*/
    int postsCounter;

    /*Данные из SUBSCRIPTIONS*/
    int subscriptionsCounter;
    int followersCounter;
    boolean subscribed;

}
