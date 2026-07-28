/**
 * Classname    ProfileDTO
 * @version     0.01
 * @author      Aleksei Borzetsov
 * date         25.07.2026
 */

package by.tms.socialnetworkc41onl.dto;

import by.tms.socialnetworkc41onl.constant.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileDto {

    long userId;
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

    /*Посты пользователя для отображения на странице профиля*/
    List<PostDTO> posts = new ArrayList<>();

    /**
     * @author Ирина Мизгир
     * @date 26.07.2026 13:52
     */
    public static record UserProfileDto(
            long userId,
            String firstName,
            String lastName,
            Gender gender,
            LocalDate birthday,
            String about,
            Long currentFileId
    ) {
    }
}
