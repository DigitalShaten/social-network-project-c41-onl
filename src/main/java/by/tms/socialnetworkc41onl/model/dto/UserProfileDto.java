package by.tms.socialnetworkc41onl.model.dto;

import by.tms.socialnetworkc41onl.constant.Gender;

import java.time.LocalDate;

/**
 * @author Ирина Мизгир
 * @date 26.07.2026 13:52
 */
public record UserProfileDto(
        long userId,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate birthday,
        String about,
        Long currentFileId
) {
}
