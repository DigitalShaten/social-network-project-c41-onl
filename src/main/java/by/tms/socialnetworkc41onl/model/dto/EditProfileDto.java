package by.tms.socialnetworkc41onl.model.dto;

import by.tms.socialnetworkc41onl.constant.Gender;
import by.tms.socialnetworkc41onl.model.FileData;

import java.time.LocalDate;

/**
 * @author Ирина Мизгир
 * @date 25.07.2026 22:10
 */

public record EditProfileDto (
        long userId,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate birthday,
        String about,
        FileData photoData
) {
}
